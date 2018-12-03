package org.ln.autopartagedata.domaine;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(UserStep.PK.class)
public class UserStep {

    @Id
    @ManyToOne
    @JoinColumn(name="UserStep_userId", referencedColumnName="id_user")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="UserStep_stepId", referencedColumnName="id_step")
    private Step step;

    private boolean validate;

    public UserStep() {
    }

    public UserStep(User user, Step step, boolean validate) {
        this.user=user;
        this.step=step;
        this.validate=validate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step=step;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate=validate;
    }

    public static class PK implements Serializable{
        Long user;
        Long step;

        public Long getUser() {
            return user;
        }

        public void setUser(Long user) {
            this.user=user;
        }

        public Long getStep() {
            return step;
        }

        public void setStep(Long step) {
            this.step=step;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PK)) return false;
            PK pk=(PK) o;
            return Objects.equals(user, pk.user) &&
                    Objects.equals(step, pk.step);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user, step);
        }
    }

}