package org.ln.autopartagedata.service;

import org.ln.autopartagedata.domaine.Step;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface StepService {
    void addStep(Step step);
    Double calculPrice(Double distance);
    java.sql.Date calculEndTime(String travelTime, String startTime) throws ParseException;

}

