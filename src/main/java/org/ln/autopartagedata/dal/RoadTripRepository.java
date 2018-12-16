package org.ln.autopartagedata.dal;

import org.ln.autopartagedata.domaine.RoadTrip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@RepositoryRestResource(path="roadtrips-list")
@Component("roadtrip_dao")
public interface RoadTripRepository extends CrudRepository<RoadTrip, Long> {
}
