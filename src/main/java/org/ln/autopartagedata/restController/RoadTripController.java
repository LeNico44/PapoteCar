package org.ln.autopartagedata.restController;

import org.json.JSONException;
import org.json.JSONObject;
import org.ln.autopartagedata.domaine.RoadTrip;
import org.ln.autopartagedata.domaine.Step;
import org.ln.autopartagedata.service.RoadTripService;
import org.ln.autopartagedata.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@RestController
@RequestMapping("/api-rest/roadtrips")
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
        String travelTime = jsonObj.getString("traveltime");
        String capacity = jsonObj.getString("capacity");
        String distance = jsonObj.getString("distance");
        String onlyTwoBackSeatWarranty = jsonObj.getString("onlytwobackseatwarranty");
        String additionalInformation = jsonObj.getString("additionalinformation");

        String[] listValues = distance.split(" ");
        String distanceValue = listValues[0];

        //Création des objets utiles
        RoadTrip roadTrip = new RoadTrip(stringToInt(capacity), null);
        Step step = new Step(startPoint, endPoint, stringToDateSql(startTime), calculEndTime(travelTime, startTime), roadTrip, stepService.priceCalculation(stringToDouble(distanceValue)), stringToDouble(distanceValue));

        //Envoie des objets créés en base
        roadTripService.addRoadTrip(roadTrip);
        stepService.addStep(step);

        return "success !";
    }

    //Convertisseur de String en Date sql
    private java.sql.Date stringToDateSql(String string) throws ParseException {
        //déclaration du format de la date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
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

    //Convertisseur de String en Double
    private Double stringToDouble(String string){

        String stringPoint = string.replaceAll(",",".");
        //Conversion de la string en integer
        return Double.parseDouble(stringPoint);
    }

    //Parser la string travelTime pour récupérer les valeurs numériques en fonction du type. (day, hour, minute, etc.)
    private java.sql.Date calculEndTime(String travelTime, String startTime) throws ParseException {
        java.sql.Date endTime = null;

        String[] listValues = travelTime.split(" ");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = sdf.parse(startTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int aDay = 0;
        int aHour = 0;
        int aMinute = 0;

        for(int i=1, j=0, size=listValues.length; i < size; i+=2, j+=2){
            int valueTime = Integer.parseInt(listValues[j]);
            String typeTime = listValues[i];
            switch(typeTime.substring(0,3)){
                case "jou":
                    aDay = valueTime;
                    System.out.println("Le type trouvé correspond à (" + typeTime + ") " + aDay);
                    break;
                case "heu":
                    aHour = valueTime;
                    System.out.println("Le type trouvé correspond à (" + typeTime + ") " + aHour);
                    break;
                case "min":
                    aMinute = valueTime;
                    System.out.println("Le type trouvé correspond à (" + typeTime + ") " + aMinute);
                    break;
                default :
                    System.out.println("Aucune correspondance dans le use case pour " + typeTime.substring(0,3) + " !");
            }
        }

        cal.add(Calendar.DAY_OF_MONTH, aDay);
        cal.add(Calendar.HOUR, aHour);
        cal.add(Calendar.MINUTE, aMinute);

        return endTime;
    }

}
