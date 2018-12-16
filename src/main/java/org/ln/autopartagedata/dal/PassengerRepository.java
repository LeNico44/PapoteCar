package org.ln.autopartagedata.dal;

import org.ln.autopartagedata.domaine.Passenger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@RepositoryRestResource(path="passengers-list")
@Component("passenger_dao")
public interface PassengerRepository extends CrudRepository<Passenger, Long> {

}

