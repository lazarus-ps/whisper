package pl.mdcode.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import pl.mdcode.domain.enumeration.GENDER;

/**
 * A UserData.
 */
@Entity
@Table(name = "user_data")
public class UserData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GENDER gender;

    @Size(max = 11)
    @Column(name = "pesel", length = 11)
    private String pesel;

    @NotNull
    @Size(max = 12)
    @Column(name = "phone_number", length = 12, nullable = false)
    private String phoneNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Principle principle;

    @OneToOne
    @JoinColumn(unique = true)
    private Agent agent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public UserData birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public GENDER getGender() {
        return gender;
    }

    public UserData gender(GENDER gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public String getPesel() {
        return pesel;
    }

    public UserData pesel(String pesel) {
        this.pesel = pesel;
        return this;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserData phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public UserData user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Principle getPrinciple() {
        return principle;
    }

    public UserData principle(Principle principle) {
        this.principle = principle;
        return this;
    }

    public void setPrinciple(Principle principle) {
        this.principle = principle;
    }

    public Agent getAgent() {
        return agent;
    }

    public UserData agent(Agent agent) {
        this.agent = agent;
        return this;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserData userData = (UserData) o;
        if (userData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserData{" +
            "id=" + getId() +
            ", birthDate='" + getBirthDate() + "'" +
            ", gender='" + getGender() + "'" +
            ", pesel='" + getPesel() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
