package org.ln.autopartagedata.service;

import org.ln.autopartagedata.domaine.RoadTrip;
import org.ln.autopartagedata.domaine.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RoadTripService {
    void addRoadTrip(RoadTrip roadTrip);
    RoadTrip getRoadTripById(Long id);
    RoadTrip getRoadTripByUser(Long user_id);
}

