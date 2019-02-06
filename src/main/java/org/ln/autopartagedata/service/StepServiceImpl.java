package org.ln.autopartagedata.service;

import org.ln.autopartagedata.dal.StepRepository;
import org.ln.autopartagedata.domaine.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
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
        stepPrice = rounder(distance * PRICE_PER_KILOMETER);
        return stepPrice;
    }

    //Parser la string travelTime pour récupérer les valeurs numériques en fonction du type. (day, hour, minute, etc.)
    @Override
    public java.sql.Date calculEndTime(String travelTime, Date date) throws ParseException {
        java.sql.Date endTime = null;

        String[] listValues = travelTime.split(" ");

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
                    break;
                case "heu":
                    aHour = valueTime;
                    break;
                case "min":
                    aMinute = valueTime;
                    break;
            }
        }

        cal.add(Calendar.DAY_OF_MONTH, aDay);
        cal.add(Calendar.HOUR, aHour);
        cal.add(Calendar.MINUTE, aMinute);
        Date utilDate = cal.getTime();

        endTime = new java.sql.Date(utilDate.getTime());

        return endTime;
    }

    @Override
    public String calculTimeDuration(Date dateStart, Date dateEnd){
        Instant tStart = dateStart.toInstant();
        Instant tEnd = dateEnd.toInstant();

        Long durationLong = Duration.between(tStart, tEnd).toMinutes();

        String timeDuration = durationLong + " minutes";

        System.out.println(timeDuration);

        return timeDuration;
    }

    @Override
    public java.sql.Date calculEndTimeFromString(String travelTime, String startTime) throws ParseException {

        Date date = getTimeZone(startTime);

        return calculEndTime(travelTime, date);
    }

    @Override
    public Date getTimeZone(String aDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = sdf.parse(aDate);

        return date;
    }


}