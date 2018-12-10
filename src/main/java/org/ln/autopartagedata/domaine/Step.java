package org.ln.autopartagedata.domaine;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
public class Step implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_step;
    private String startPoint;
    private String endPoint;
    private Date estimateStratTime;
    private Date estimateEndTime;
    private Boolean canceled;
    private Date realStratTime;
    private Date realEndTime;
    @OneToMany( mappedBy = "step", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    private Set<Passenger> passengers;
    @ManyToOne
    @JoinColumn( name = "id_roadtrip" )//préciser le nom de la colonne créée pour la clé étrangère sinon JPA crée des nom barbares
    private RoadTrip roadTrip;

    public Step() {
    }

    public Step(String startPoint, String endPoint, Date estimateStratTime, Date estimateEndTime, Boolean canceled, Date realStratTime, Date realEndTime, Set<Passenger> passengers, RoadTrip roadTrip) {
        this.startPoint=startPoint;
        this.endPoint=endPoint;
        this.estimateStratTime=estimateStratTime;
        this.estimateEndTime=estimateEndTime;
        this.canceled=canceled;
        this.realStratTime=realStratTime;
        this.realEndTime=realEndTime;
        this.passengers=passengers;
        this.roadTrip=roadTrip;
    }

    public Long getId_step() {
        return id_step;
    }

    public void setId_step(Long id_step) {
        this.id_step=id_step;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint=startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint=endPoint;
    }

    public Date getEstimateStratTime() {
        return estimateStratTime;
    }

    public void setEstimateStratTime(Date estimateStratTime) {
        this.estimateStratTime=estimateStratTime;
    }

    public Date getEstimateEndTime() {
        return estimateEndTime;
    }

    public void setEstimateEndTime(Date estimateEndTime) {
        this.estimateEndTime=estimateEndTime;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled=canceled;
    }

    public Date getRealStratTime() {
        return realStratTime;
    }

    public void setRealStratTime(Date realStratTime) {
        this.realStratTime=realStratTime;
    }

    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime=realEndTime;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers=passengers;
    }

    public RoadTrip getRoadTrip() {
        return roadTrip;
    }

    public void setRoadTrip(RoadTrip roadTrip) {
        this.roadTrip=roadTrip;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id_step=" + id_step +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", estimateStratTime=" + estimateStratTime +
                ", estimateEndTime=" + estimateEndTime +
                ", canceled=" + canceled +
                ", realStratTime=" + realStratTime +
                ", realEndTime=" + realEndTime +
                ", passengers=" + passengers +
                ", roadTrip=" + roadTrip +
                '}';
    }
}
