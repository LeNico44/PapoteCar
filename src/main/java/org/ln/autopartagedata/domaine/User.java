package org.ln.autopartagedata.domaine;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    private Genre userGenre;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int birthYear;
    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    private Set<Comment> comments;
    @OneToMany( mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    private Set<RoadTrip> roadTrips;
    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    private Set<Passenger> passengers;

    public User() {
    }
    public User(int birthYear, String email) {
        this.birthYear=birthYear;
        this.email=email;
    }

    public User(Genre userGenre, String firstName, String lastName, String email, String phoneNumber, int birthYear) {
        this.userGenre=userGenre;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.birthYear=birthYear;
    }

    public User(Genre userGenre, String firstName, String lastName, String email, String phoneNumber, int birthYear, Set<Comment> comments, Set<RoadTrip> roadTrips, Set<Passenger> passengers) {
        this.userGenre=userGenre;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.birthYear=birthYear;
        this.comments=comments;
        this.roadTrips=roadTrips;
        this.passengers=passengers;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user=id_user;
    }

    public Genre getUserGenre() {
        return userGenre;
    }

    public void setUserGenre(Genre userGenre) {
        this.userGenre=userGenre;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber=phoneNumber;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear=birthYear;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments=comments;
    }

    public Set<RoadTrip> getRoadTrips() {
        return roadTrips;
    }

    public void setRoadTrips(Set<RoadTrip> roadTrips) {
        this.roadTrips=roadTrips;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers=passengers;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id_user +
                ", userGenre=" + userGenre +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthYear=" + birthYear +
                ", comments" + comments +
                ", roadTrips" + roadTrips +
                ", passengers" + passengers +
                '}';
    }

    public enum Genre {
        Madame,
        Monsieur,
        Autre
    }
}
