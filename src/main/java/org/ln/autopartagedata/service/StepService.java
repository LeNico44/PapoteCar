package org.ln.autopartagedata.service;

import org.ln.autopartagedata.domaine.Step;
import org.springframework.stereotype.Service;

@Service
public interface StepService {
    void addStep(Step step);
}
