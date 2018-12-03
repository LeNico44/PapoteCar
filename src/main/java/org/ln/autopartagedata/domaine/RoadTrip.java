package org.ln.autopartagedata.domaine;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class RoadTrip implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_roadtrip;
    private byte capacity;
    private boolean canceled;
    private byte remainingPlace;
    @ManyToOne
    @JoinColumn( name = "id_user" )
    private User driver;
    @OneToMany( mappedBy = "roadTrip", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    private Set<Step> steps;
    @OneToMany( mappedBy = "roadTrip", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    private Set<Comment> comments;

    public RoadTrip(){}

    public RoadTrip(byte capacity, boolean canceled, byte remainingPlace, User driver, Set<Step> steps, Set<Comment> comments) {
        this.capacity=capacity;
        this.canceled=canceled;
        this.remainingPlace=remainingPlace;
        this.driver=driver;
        this.steps=steps;
        this.comments=comments;
    }

    public Long getId_roadtrip() {
        return id_roadtrip;
    }

    public void setId_roadtrip(Long id_roadtrip) {
        this.id_roadtrip=id_roadtrip;
    }

    public byte getCapacity() {
        return capacity;
    }

    public void setCapacity(byte capacity) {
        this.capacity=capacity;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled=canceled;
    }

    public byte getRemainingPlace() {
        return remainingPlace;
    }

    public void setRemainingPlace(byte remainingPlace) {
        this.remainingPlace=remainingPlace;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver=driver;
    }

    public Set<Step> getSteps() {
        return steps;
    }

    public void setSteps(Set<Step> steps) {
        this.steps=steps;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments=comments;
    }

    @Override
    public String toString() {
        return "RoadTrip{" +
                "id_roadtrip=" + id_roadtrip +
                ", capacity=" + capacity +
                ", canceled=" + canceled +
                ", remainingPlace=" + remainingPlace +
                ", driver=" + driver +
                ", steps=" + steps +
                ", comments=" + comments +
                '}';
    }
}
