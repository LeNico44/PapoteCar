package org.ln.autopartagedata.dal;

import org.ln.autopartagedata.domaine.Step;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@RepositoryRestResource(path="steps-list")
@Component("step_dao")
public interface StepRepository extends CrudRepository<Step, Long> {
}
