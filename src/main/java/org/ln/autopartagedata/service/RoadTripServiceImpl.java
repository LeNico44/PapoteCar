package org.ln.autopartagedata.service;

import org.ln.autopartagedata.dal.RoadTripRepository;
import org.ln.autopartagedata.domaine.RoadTrip;
import org.ln.autopartagedata.domaine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;
import java.util.Set;

@Service
public class RoadTripServiceImpl extends GenericService <RoadTripRepository, RoadTrip> implements RoadTripService {

    private RoadTripRepository roadTripRepository;

    @Autowired
    public RoadTripServiceImpl(RoadTripRepository roadTripRepository){this.roadTripRepository = roadTripRepository;}

    @Override
    public void addRoadTrip(RoadTrip roadTrip) {
        roadTripRepository.save(roadTrip);
    }

    @Override
    public Optional<RoadTrip> getRoadTripById(Long id) {
        return this.roadTripRepository.findById(id);
    }

//    @Override
//    public RoadTrip getRoadTripByUser(Long user_id) {
//        return this.roadTripRepository.getRoadTripByDriver(user_id);
//    }

    @Override
    public Iterable<RoadTrip> getAllRoadTrips() {
        return this.roadTripRepository.findAll();
    }
}
