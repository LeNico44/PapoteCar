package org.ln.autopartagedata.restController;

import org.json.JSONException;
import org.json.JSONObject;
import org.ln.autopartagedata.domaine.RoadTrip;
import org.ln.autopartagedata.domaine.Step;
import org.ln.autopartagedata.service.GenericService;
import org.ln.autopartagedata.service.RoadTripService;
import org.ln.autopartagedata.service.StepService;
import org.ln.autopartagedata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;

@RestController
@RequestMapping("/api-rest/roadtrips")
public class RoadTripController {

    private RoadTripService roadTripService;
    private StepService stepService;
    private GenericService genericService;
    private UserService userService;

    @Autowired
    public RoadTripController(RoadTripService roadTripService, StepService stepService, GenericService genericService,
                              UserService userService){
        this.roadTripService = roadTripService;
        this.stepService = stepService;
        this.genericService = genericService;
        this.userService = userService;
    }

    @PostMapping(path="/test",consumes = "application/json")
    public String newRoadTrip(@RequestBody String json) throws JSONException, ParseException {

        //création d'un objet JSON à partir de la string globale du body (data)
        JSONObject jsonObj=new JSONObject(json);

        //Récuprération des valeurs de l'objet JSON dans des variables
        String startPoint = jsonObj.getString("startpoint");
        String endPoint = jsonObj.getString("endpoint");
        String startTime = jsonObj.getString("starttime");
        String travelTime = jsonObj.getString("traveltime");
        String capacity = jsonObj.getString("capacity");
        String distance = jsonObj.getString("distance");
        String onlyTwoBackSeatWarranty = jsonObj.getString("onlyTwoBackSeatWarranty");
        String additionalInformation = jsonObj.getString("additionalInformation");
        String driverId = "1";//jsonObj.getString("driverId");

        //Création des objets utiles
        RoadTrip roadTrip = new RoadTrip(Integer.parseInt(capacity), userService.getUserById(Long.parseLong(driverId)),
                genericService.stringToBoolean(onlyTwoBackSeatWarranty),
                additionalInformation);
        Step step = new Step(startPoint, endPoint, genericService.stringToDateSql(startTime),
                stepService.calculEndTime(travelTime, startTime), roadTrip,
                stepService.calculPrice(genericService.stringToDouble(genericService.catchValueString(distance))),
                genericService.stringToDouble(genericService.catchValueString(distance)));

        //Envoie des objets créés en base
        roadTripService.addRoadTrip(roadTrip);
        stepService.addStep(step);

        return "success !";
    }
}
