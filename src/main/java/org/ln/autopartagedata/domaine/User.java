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
    private byte birthYear;
    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    private Set<Comment> comments;
    @OneToMany( mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    private Set<RoadTrip> roadTrips;
    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    private Set<UserStep> UserStep_stepId;

    public User() {
    }

    public User(Genre userGenre, String firstName, String lastName, String email, String phoneNumber, byte birthYear, Set<Comment> comments, Set<RoadTrip> roadTrips) {
        this.userGenre=userGenre;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.birthYear=birthYear;
        this.comments=comments;
        this.roadTrips=roadTrips;
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

    public byte getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(byte birthYear) {
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
                '}';
    }

    private enum Genre {
        Madame,
        Monsieur,
        Autre
    }
}
