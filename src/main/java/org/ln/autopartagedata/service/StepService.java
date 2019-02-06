package org.ln.autopartagedata.service;

import org.ln.autopartagedata.domaine.Step;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public interface StepService {
    void addStep(Step step);
    Double calculPrice(Double distance);
    java.sql.Date calculEndTime(String travelTime, Date date) throws ParseException;
    String calculTimeDuration(Date dateStart, Date dateEnd) throws ParseException;
    java.sql.Date calculEndTimeFromString(String travelTime, String startTime) throws ParseException;
    Date getTimeZone(String aDate)throws ParseException;


}

