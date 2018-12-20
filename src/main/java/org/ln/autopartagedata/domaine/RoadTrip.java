package org.ln.autopartagedata.domaine;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Entity
public class RoadTrip implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int capacity;
    private boolean canceled;
    private int remainingPlace;
    //@NotNull
    @ManyToOne
    @JoinColumn( name = "user_id" )
    private User driver;
    @OneToMany( mappedBy = "roadTrip", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    private Set<Step> steps;
    @OneToMany( mappedBy = "roadTrip", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    private Set<Comment> comments;

    public RoadTrip(){}

    public RoadTrip(int capacity, User driver){
        this.canceled=false;
        this.capacity=capacity;
        this.remainingPlace=capacity;
        this.driver=driver;
        this.steps = this.getSteps();
    }

    public RoadTrip(boolean canceled, int capacity, int remainingPlace, User driver){
        this.canceled=canceled;
        this.capacity=capacity;
        this.remainingPlace=remainingPlace;
        this.driver=driver;
    }

    public RoadTrip(int capacity, boolean canceled, int remainingPlace, User driver, Set<Step> steps, Set<Comment> comments) {
        this.capacity=capacity;
        this.canceled=canceled;
        this.remainingPlace=remainingPlace;
        this.driver=driver;
        this.steps=steps;
        this.comments=comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity=capacity;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled=canceled;
    }

    public int getRemainingPlace() {
        return remainingPlace;
    }

    public void setRemainingPlace(int remainingPlace) {
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
                "id=" + id +
                ", capacity=" + capacity +
                ", canceled=" + canceled +
                ", remainingPlace=" + remainingPlace +
                ", driver=" + driver +
                ", steps=" + steps +
                ", comments=" + comments +
                '}';
    }
}
