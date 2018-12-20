package org.ln.autopartagedata.service;

import org.ln.autopartagedata.domaine.RoadTrip;
import org.springframework.stereotype.Service;

@Service
public interface RoadTripService {
    void addRoadTrip(RoadTrip roadTrip);
}

