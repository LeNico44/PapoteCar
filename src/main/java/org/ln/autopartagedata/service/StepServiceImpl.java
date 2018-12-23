package org.ln.autopartagedata.service;

import org.ln.autopartagedata.dal.StepRepository;
import org.ln.autopartagedata.domaine.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Service
public class StepServiceImpl extends GenericService<StepRepository, Step> implements StepService {
    private static final Double PRICE_PER_KILOMETER = 0.13;
    private StepRepository stepRepository;

    @Autowired
    public StepServiceImpl(StepRepository stepRepository){this.stepRepository = stepRepository;}

    @Override
    public void addStep(Step step) {
        stepRepository.save(step);
    }

    @Override
    public Double calculPrice(Double distance) {
        Double stepPrice;
        stepPrice = (double) Math.round((distance * PRICE_PER_KILOMETER) * 100) / 100;
        return stepPrice;
    }

    //Parser la string travelTime pour récupérer les valeurs numériques en fonction du type. (day, hour, minute, etc.)
    @Override
    public java.sql.Date calculEndTime(String travelTime, String startTime) throws ParseException {
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
        Date utilDate = cal.getTime();

        endTime = new java.sql.Date(utilDate.getTime());

        return endTime;
    }
}