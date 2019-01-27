package org.ln.autopartagedata.service;

import org.ln.autopartagedata.domaine.RoadTrip;
import org.ln.autopartagedata.domaine.User;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.Set;

@Service
public interface RoadTripService {
    void addRoadTrip(RoadTrip roadTrip);
    Optional<RoadTrip> getRoadTripById(Long id);
//    RoadTrip getRoadTripByUser(Long user_id);
    Iterable<RoadTrip> getAllRoadTrips();
}

