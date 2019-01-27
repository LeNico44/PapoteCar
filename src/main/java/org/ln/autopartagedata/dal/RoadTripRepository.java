package org.ln.autopartagedata.dal;

import org.ln.autopartagedata.domaine.RoadTrip;
import org.ln.autopartagedata.domaine.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import java.util.Set;

@RepositoryRestResource(path="roadtrips-list")
@Component("roadtrip_dao")
public interface RoadTripRepository extends CrudRepository<RoadTrip, Long> {
    //RoadTrip getRoadTripById(@Param("id") Long id);
    //RoadTrip getRoadTripByDriver(@Param("driver") Long user_id);
}
