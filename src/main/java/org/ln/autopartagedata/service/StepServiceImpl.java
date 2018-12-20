package org.ln.autopartagedata.service;

import org.ln.autopartagedata.dal.StepRepository;
import org.ln.autopartagedata.domaine.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepServiceImpl extends GenericService<StepRepository, Step> implements StepService {
    private static final Double PRICE_PER_KILOMETER = 0.50;
    private StepRepository stepRepository;

    @Autowired
    public StepServiceImpl(StepRepository stepRepository){this.stepRepository = stepRepository;}

    @Override
    public void addStep(Step step) {
        stepRepository.save(step);
    }

    @Override
    public Double priceCalculation(Double distance) {
        Double stepPrice;
        stepPrice = (double) Math.round((distance * PRICE_PER_KILOMETER) * 100) / 100;
        return stepPrice;
    }
}