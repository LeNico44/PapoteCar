package org.ln.autopartagedata.service;

import org.ln.autopartagedata.dal.RoadTripRepository;
import org.ln.autopartagedata.domaine.RoadTrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoadTripServiceImpl extends GenericService <RoadTripRepository, RoadTrip> implements RoadTripService {

    private RoadTripRepository roadTripRepository;

    @Autowired
    public RoadTripServiceImpl(RoadTripRepository roadTripRepository){this.roadTripRepository = roadTripRepository;}

    @Override
    public void addRoadTrip(RoadTrip roadTrip) {
        roadTripRepository.save(roadTrip);
    }
}
