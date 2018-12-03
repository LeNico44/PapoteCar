package org.ln.autopartagedata.domaine;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comment;
    private String content;
    private String title;
    private Date date;
    @ManyToOne
    @JoinColumn( name = "id_roadtrip" )
    private RoadTrip roadTrip;
    @ManyToOne
    @JoinColumn( name = "id_user" )
    private User user;

    public Comment() {
    }

    public Comment(String content, String title, Date date, RoadTrip roadTrip, User user) {
        this.content=content;
        this.title=title;
        this.date=date;
        this.roadTrip=roadTrip;
        this.user=user;
    }

    public Long getId_comment() {
        return id_comment;
    }

    public void setId_comment(Long id_comment) {
        this.id_comment=id_comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content=content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title=title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date=date;
    }

    public RoadTrip getRoadTrip() {
        return roadTrip;
    }

    public void setRoadTrip(RoadTrip roadTrip) {
        this.roadTrip=roadTrip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id_comment=" + id_comment +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", roadTrip=" + roadTrip +
                ", user=" + user +
                '}';
    }
}
