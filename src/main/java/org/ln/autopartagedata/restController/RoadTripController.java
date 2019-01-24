package org.ln.autopartagedata.restController;

import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.json.JSONObject;
import org.ln.autopartagedata.domaine.RoadTrip;
import org.ln.autopartagedata.domaine.Step;
import org.ln.autopartagedata.domaine.User;
import org.ln.autopartagedata.service.GenericService;
import org.ln.autopartagedata.service.RoadTripService;
import org.ln.autopartagedata.service.StepService;
import org.ln.autopartagedata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    @ResponseStatus(HttpStatus.CREATED)
    public String newRoadTrip (@RequestBody String json) throws JSONException, ParseException {

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
        Long driverId = jsonObj.getLong("id");
        String stepPoint = jsonObj.getString("steppoint");

        Optional<User> optionalUser = userService.getUserById(driverId);

        //Création du roadTrip
        RoadTrip roadTrip = new RoadTrip(Integer.parseInt(capacity), optionalUser.get(),
                genericService.stringToBoolean(onlyTwoBackSeatWarranty),
                additionalInformation);

        //Création du step vide
        Step step = new Step();

        //Envoie du roadTrip créé en base
        roadTripService.addRoadTrip(roadTrip);

        if(stepPoint.equals("")){

            step=new Step(startPoint, endPoint, genericService.stringToDateSql(startTime),
                    stepService.calculEndTimeFromString(travelTime, startTime), roadTrip,
                    stepService.calculPrice(genericService.stringToDouble(genericService.catchValueString(distance))),
                    genericService.stringToDouble(genericService.catchValueString(distance)));
        }else{

            String stepDistance = jsonObj.getString("stepDistance");
            String stepTravelTime = jsonObj.getString("stepTime");
            String startStepTime = stepService.calculEndTimeFromString(travelTime, startTime).toString();


            Step step1=new Step(startPoint, stepPoint, genericService.stringToDateSql(startTime),
                    stepService.calculEndTimeFromString(travelTime, startTime), roadTrip,
                    stepService.calculPrice(genericService.stringToDouble(genericService.catchValueString(stepDistance))),
                    genericService.stringToDouble(genericService.catchValueString(stepDistance)));

            step=new Step(stepPoint, endPoint, stepService.calculEndTimeFromString(travelTime, startTime),
                    stepService.calculEndTime(stepTravelTime, stepService.calculEndTimeFromString(travelTime, startTime)), roadTrip,
                    stepService.calculPrice(genericService.stringToDouble(genericService.catchValueString(distance))),
                    genericService.stringToDouble(genericService.catchValueString(distance)));

            //Envoie du step1 créé en base
            stepService.addStep(step1);

        }


        //Envoie du step créé en base
        stepService.addStep(step);

        JSONObject jsonRetour = new JSONObject();
        jsonRetour.put("retour", "Success !");

        return jsonRetour.toString();
    }

    @GetMapping(value="/{id}",produces="application/json")
    @ApiOperation(value = "Get roadTrip by ID")
    RoadTrip getRoadTripById(@PathVariable final Long id){
        return this.roadTripService.getRoadTripById(id);
    }

    @GetMapping(value="/user/{user_id}",produces="application/json")
    @ApiOperation(value = "Get roadTrip by user_id")
    RoadTrip getRoadTripByUser(@PathVariable final Long user_id){
        return this.roadTripService.getRoadTripByUser(user_id);
    }

    @GetMapping(value="/ultimate_roadtrip/{id}",produces="application/json")
    @ApiOperation(value = "Get ultimate roadTrip")
    String getUltimateRoadTripById(@PathVariable final Long id) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONObject stepJsonObject;
        JSONObject roadTripJsonObject;

        RoadTrip roadTrip = this.roadTripService.getRoadTripById(id);
        Set<Step> steps = roadTrip.getSteps();

        int i = 1;
        if(steps.size() == 1){
            jsonObject.put("startRoadTrip", roadTrip.getSteps().iterator().next().getStartPoint());
            jsonObject.put("endRoadTrip", roadTrip.getSteps().iterator().next().getEndPoint());
        }else{
            roadTripJsonObject = new JSONObject();
            for(Step step : steps){
                stepJsonObject = new JSONObject();
                stepJsonObject.put("startPoint", step.getStartPoint());
                stepJsonObject.put("endPoint", step.getEndPoint());

                jsonObject.put("step-" + i++, stepJsonObject);
                if(i==2){
                    roadTripJsonObject.put("startPoint", step.getStartPoint());
                }else{
                    roadTripJsonObject.put("endPoint", step.getEndPoint());
                }
            }
            jsonObject.put("roadTrip",roadTripJsonObject);
        }

        return jsonObject.toString();
    }
}
