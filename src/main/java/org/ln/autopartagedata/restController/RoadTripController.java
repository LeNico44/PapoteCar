package org.ln.autopartagedata.restController;

import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
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
import java.util.*;

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

    @PostMapping(path="/go",consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String goRoadTrip (@RequestBody String json) throws JSONException, ParseException {

        JSONObject jsonObj=new JSONObject(json);
        Step modifiedStep = null;

        String stringId = jsonObj.getString("id");

        RoadTrip roadTrip = this.roadTripService.getRoadTripById(Long.parseLong(stringId)).get();
        Set<Step> steps = roadTrip.getSteps();
        List<Step> stepList = new ArrayList<>();

        Date realStartTimeUtil = new Date();
        Date realEndTimeSql = new Date();

        for (Step step : steps){
            stepList.add(step);
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR,1);

        stepList.get(0).setRealStartTime(cal.getTime());
        stepList.get(0).setRealEndTime(stepService.calculEndTime(stepService.calculTimeDuration(
                stepList.get(0).getEstimateStartTime(), stepList.get(0).getEstimateEndTime()),
                stepList.get(0).getRealStartTime()));

        System.out.println(stepList.get(0).getRealStartTime());
        System.out.println(stepList.get(0).getRealEndTime());

        modifiedStep = stepList.get(0);

        //Envoie du modifiedStep modifié en base
        stepService.addStep(modifiedStep);

        if (stepList.size() > 1){
            for(int i = 1; i < stepList.size(); i++) {
                stepList.get(i).setRealStartTime(stepList.get(i-1).getRealEndTime());
                stepList.get(i).setRealEndTime(stepService.calculEndTime(
                        stepService.calculTimeDuration(stepList.get(i).getEstimateStartTime(),
                                stepList.get(i).getEstimateEndTime()), stepList.get(i).getRealStartTime()));

                modifiedStep = stepList.get(i);

                //Envoie du modifiedStep modifié en base
                stepService.addStep(modifiedStep);

            }
        }

        JSONObject jsonRetour = new JSONObject();
        jsonRetour.put("retour", "Success !");

        return jsonRetour.toString();
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
        jsonRetour.put("roadTripId", roadTrip.getId().toString());

        return jsonRetour.toString();
    }

    @GetMapping(value="/{id}",produces="application/json")
    @ApiOperation(value = "Get roadTrip et steps by ID")
    String getRoadTripAndStepsById(@PathVariable final Long id) throws JSONException, ParseException {
        return returnRoadtrip(id).toString();
    }

    @GetMapping(value="/user/{id}",produces="application/json")
    @ApiOperation(value = "Get roadTrips et steps by user")
    String getRoadTripAndStepsByUserId(@PathVariable final Long id) throws JSONException, ParseException {
        User user = this.userService.getUserById(id).get();
        Set<RoadTrip> roadTrips = user.getRoadTrips();
        JSONArray jsonArray = getRoadTrips(roadTrips);

        return jsonArray.toString();
    }

    @GetMapping(value="",produces="application/json")
    @ApiOperation(value = "Get all roadTrips et steps")
    String getallRoadTripAndSteps() throws JSONException, ParseException {
        Iterable<RoadTrip> roadTrips = this.roadTripService.getAllRoadTrips();
        JSONArray jsonArray  = getRoadTrips(roadTrips);

        return jsonArray.toString();
    }

    JSONArray returnRoadtrip(Long id) throws JSONException, ParseException {
        JSONArray jsonArray = new JSONArray();
        JSONObject stepJsonObject;
        JSONObject roadTripJsonObject = new JSONObject();

        RoadTrip roadTrip = this.roadTripService.getRoadTripById(id).get();
        Set<Step> steps = roadTrip.getSteps();

        if(steps.size() == 1){
            roadTripJsonObject.put("startPoint", roadTrip.getSteps().iterator().next().getStartPoint());
            roadTripJsonObject.put("endPoint", roadTrip.getSteps().iterator().next().getEndPoint());
            roadTripJsonObject.put("passengers", roadTrip.getSteps().iterator().next().getPassengers());
            roadTripJsonObject.put("canceled", roadTrip.getSteps().iterator().next().getCanceled());
            roadTripJsonObject.put("distance", roadTrip.getSteps().iterator().next().getDistance());
            roadTripJsonObject.put("estimateEndTime", roadTrip.getSteps().iterator().next().getEstimateEndTime());
            roadTripJsonObject.put("estimateStartTime", roadTrip.getSteps().iterator().next().getEstimateStartTime());
            roadTripJsonObject.put("realEndTime", roadTrip.getSteps().iterator().next().getRealEndTime());
            roadTripJsonObject.put("realStartTime", roadTrip.getSteps().iterator().next().getRealStartTime());
            roadTripJsonObject.put("price", roadTrip.getSteps().iterator().next().getPrice());
            roadTripJsonObject.put("additionalInformation", roadTrip.getAdditionalInformation());
            roadTripJsonObject.put("capacity", roadTrip.getCapacity());
            roadTripJsonObject.put("remainingPlace", roadTrip.getRemainingPlace());
            roadTripJsonObject.put("onlyTwoBackSeatWarranty", roadTrip.isOnlyTwoBackSeatWarranty());
        }else{
            Double distanceRoadTrip = 0.0;
            Double priceRoadTrip = 0.0;
            for(Step step : steps){

                stepJsonObject = new JSONObject();
                stepJsonObject.put("id", step.getId());
                stepJsonObject.put("startPoint", step.getStartPoint());
                stepJsonObject.put("endPoint", step.getEndPoint());
                stepJsonObject.put("passengers", step.getPassengers());
                stepJsonObject.put("canceled", step.getCanceled());
                stepJsonObject.put("distance", step.getDistance());
                stepJsonObject.put("estimateEndTime", step.getEstimateEndTime());
                stepJsonObject.put("estimateStartTime", step.getEstimateStartTime());
                stepJsonObject.put("realEndTime", step.getRealEndTime());
                stepJsonObject.put("realStartTime", step.getRealStartTime());
                stepJsonObject.put("price", step.getPrice());

                distanceRoadTrip = genericService.rounder(distanceRoadTrip + step.getDistance());
                priceRoadTrip = genericService.rounder(priceRoadTrip + step.getPrice());

                jsonArray.put(stepJsonObject);

            }

            Long aId = jsonArray.getJSONObject(0).getLong("id");
            System.out.println("////////////////////////////////////////////////////////////////////////");
            System.out.println(aId);
            System.out.println("////////////////////////////////////////////////////////////////////////");
            roadTripJsonObject.put("startPoint", jsonArray.getJSONObject(0).getString("startPoint"));
            roadTripJsonObject.put("estimateStartTime", jsonArray.getJSONObject(0).getString("estimateStartTime"));
            roadTripJsonObject.put("endPoint", jsonArray.getJSONObject(0).getString("endPoint"));
            roadTripJsonObject.put("estimateEndTime", jsonArray.getJSONObject(0).getString("estimateEndTime"));

            for(int i = 0; i < jsonArray.length(); i++){
                if(jsonArray.getJSONObject(i).getLong("id") > aId){
                    roadTripJsonObject.put("endPoint", jsonArray.getJSONObject(i).getString("endPoint"));
                    roadTripJsonObject.put("estimateEndTime", jsonArray.getJSONObject(i).getString("estimateEndTime"));
                }else if (jsonArray.getJSONObject(i).getLong("id") < aId){
                    roadTripJsonObject.put("startPoint", jsonArray.getJSONObject(i).getString("startPoint"));
                    roadTripJsonObject.put("estimateStartTime", jsonArray.getJSONObject(i).getString("estimateStartTime"));
                }
            }

            roadTripJsonObject.put("additionalInformation", roadTrip.getAdditionalInformation());
            roadTripJsonObject.put("capacity", roadTrip.getCapacity());
            roadTripJsonObject.put("remainingPlace", roadTrip.getRemainingPlace());
            roadTripJsonObject.put("onlyTwoBackSeatWarranty", roadTrip.isOnlyTwoBackSeatWarranty());
            roadTripJsonObject.put("canceled", roadTrip.isCanceled());
            roadTripJsonObject.put("distance",distanceRoadTrip);
            roadTripJsonObject.put("price",priceRoadTrip);
        }

        jsonArray.put(roadTripJsonObject);

        return jsonArray;
    }

    /**
     * Boucle sur les roadtrips
     */
    private JSONArray getRoadTrips(Iterable<RoadTrip> roadTrips) throws JSONException, ParseException {
        JSONArray jsonArray = new JSONArray();

        for(RoadTrip roadTrip : roadTrips){
            jsonArray.put(returnRoadtrip(roadTrip.getId()));
        }

        return jsonArray;
    }
}
