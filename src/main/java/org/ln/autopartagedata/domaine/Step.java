package org.ln.autopartagedata.domaine;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class Step implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String startPoint;
    private String endPoint;
    private Date estimateStartTime;
    private Date estimateEndTime;
    private Boolean canceled;
    private Date realStartTime;
    private Date realEndTime;
    @OneToMany( mappedBy = "step", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    private Set<Passenger> passengers;
    @ManyToOne
    @JoinColumn( name = "roadTrip_id" )//préciser le nom de la colonne créée pour la clé étrangère sinon JPA crée des nom barbares
    private RoadTrip roadTrip;
    private Double price;

    public Step() {
    }

    public Step(String startPoint, String endPoint, Date estimateStartTime, Date estimateEndTime, RoadTrip roadTrip, Double price) {
        this.startPoint=startPoint;
        this.endPoint=endPoint;
        this.estimateStartTime=estimateStartTime;
        this.estimateEndTime=estimateEndTime;
        this.canceled=false;
        this.roadTrip=roadTrip;
        this.price=price;
        this.passengers=this.getPassengers();
    }

    public Step(String startPoint, String endPoint, Date estimateStartTime, Date estimateEndTime, Boolean canceled, Date realStartTime, Date realEndTime, RoadTrip roadTrip, Double price) {
        this.startPoint=startPoint;
        this.endPoint=endPoint;
        this.estimateStartTime=estimateStartTime;
        this.estimateEndTime=estimateEndTime;
        this.canceled=canceled;
        this.realStartTime=realStartTime;
        this.realEndTime=realEndTime;
        this.roadTrip=roadTrip;
        this.price=price;
    }

    public Step(String startPoint, String endPoint, Date estimateStartTime, Date estimateEndTime, Boolean canceled, Date realStartTime, Date realEndTime, Set<Passenger> passengers, RoadTrip roadTrip, Double price) {
        this.startPoint=startPoint;
        this.endPoint=endPoint;
        this.estimateStartTime=estimateStartTime;
        this.estimateEndTime=estimateEndTime;
        this.canceled=canceled;
        this.realStartTime=realStartTime;
        this.realEndTime=realEndTime;
        this.passengers=passengers;
        this.roadTrip=roadTrip;
        this.price=price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
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

    public Date getEstimateStartTime() {
        return estimateStartTime;
    }

    public void setEstimateStartTime(Date estimateStartTime) {
        this.estimateStartTime=estimateStartTime;
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

    public Date getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(Date realStartTime) {
        this.realStartTime=realStartTime;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price=price;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", estimateStartTime=" + estimateStartTime +
                ", estimateEndTime=" + estimateEndTime +
                ", canceled=" + canceled +
                ", realStartTime=" + realStartTime +
                ", realEndTime=" + realEndTime +
                ", passengers=" + passengers +
                ", roadTrip=" + roadTrip +
                ", price=" + price +
                '}';
    }
}
