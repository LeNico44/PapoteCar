package org.ln.autopartagedata.domaine;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@IdClass(Comment.PK.class)
public class Comment implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName="id_user")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="roadTripId", referencedColumnName="id_roadTrip")
    private RoadTrip roadTrip;

    private String content;
    private String title;
    private Date date;

    public Comment() {
    }

    public Comment(User user, RoadTrip roadTrip, String content, String title, Date date) {
        this.user=user;
        this.roadTrip=roadTrip;
        this.content=content;
        this.title=title;
        this.date=date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public RoadTrip getRoadTrip() {
        return roadTrip;
    }

    public void setRoadTrip(RoadTrip roadTrip) {
        this.roadTrip=roadTrip;
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

    public static class PK implements Serializable{
        Long user;
        Long roadTrip;

        public Long getUser() {
            return user;
        }

        public void setUser(Long user) {
            this.user=user;
        }

        public Long getRoadTrip() {
            return roadTrip;
        }

        public void setRoadTrip(Long roadTrip) {
            this.roadTrip=roadTrip;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PK)) return false;
            PK pk=(PK) o;
            return Objects.equals(user, pk.user) &&
                    Objects.equals(roadTrip, pk.roadTrip);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user, roadTrip);
        }
    }
}
