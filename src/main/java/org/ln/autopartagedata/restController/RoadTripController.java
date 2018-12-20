package org.ln.autopartagedata.restController;

import org.json.JSONException;
import org.json.JSONObject;
import org.ln.autopartagedata.domaine.RoadTrip;
import org.ln.autopartagedata.domaine.Step;
import org.ln.autopartagedata.service.RoadTripService;
import org.ln.autopartagedata.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@RestController
@RequestMapping("/roadtrips")
public class RoadTripController {

    private RoadTripService roadTripService;
    private StepService stepService;

    @Autowired
    public RoadTripController(RoadTripService roadTripService, StepService stepService){
        this.roadTripService = roadTripService;
        this.stepService = stepService;
    }

    @PostMapping(path="/test",consumes = "application/json")
    public String saveObj(@RequestBody String json) throws JSONException, ParseException {

        //création d'un objet JSON à partir de la string globale du body (data)
        JSONObject jsonObj=new JSONObject(json);

        //Récuprération des valeurs de l'objet JSON dans des variables
        String startPoint = jsonObj.getString("startpoint");
        String endPoint = jsonObj.getString("endpoint");
        String startTime = jsonObj.getString("starttime");
        String endTime = jsonObj.getString("endtime");
        String capacity = jsonObj.getString("capacity");

        //Création des objets utiles
        RoadTrip roadTrip = new RoadTrip(stringToInt(capacity), null);
        Step step = new Step(startPoint, endPoint, stringToDateSql(startTime), stringToDateSql(endTime), roadTrip, 0.1);

        //Envoie des objets créés en base
        roadTripService.addRoadTrip(roadTrip);
        stepService.addStep(step);

        return "success !";
    }

    //Convertisseur de String en Date sql
    private java.sql.Date stringToDateSql(String string) throws ParseException {
        //déclaration du format de la date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        //Conversation de la string en simpleDateFormat
        Date date = sdf.parse(string);
        //Retour de la date en sql date
        return new java.sql.Date(date.getTime());
    }

    //Convertisseur de String en int
    private int stringToInt(String string){
        //Conversion de la string en integer
        return Integer.parseInt(string);
    }

}
