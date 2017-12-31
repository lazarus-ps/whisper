package pl.mdcode.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import pl.mdcode.domain.enumeration.PRINCIPLE_STATUS;

/**
 * A Principle.
 */
@Entity
@Table(name = "principle")
public class Principle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 10)
    @Column(name = "nip", length = 10)
    private String nip;

    @Size(max = 100)
    @Column(name = "company_name", length = 100)
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "principle_status")
    private PRINCIPLE_STATUS principleStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private PrincipleSubscription principleSubscription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public Principle nip(String nip) {
        this.nip = nip;
        return this;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Principle companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public PRINCIPLE_STATUS getPrincipleStatus() {
        return principleStatus;
    }

    public Principle principleStatus(PRINCIPLE_STATUS principleStatus) {
        this.principleStatus = principleStatus;
        return this;
    }

    public void setPrincipleStatus(PRINCIPLE_STATUS principleStatus) {
        this.principleStatus = principleStatus;
    }

    public PrincipleSubscription getPrincipleSubscription() {
        return principleSubscription;
    }

    public Principle principleSubscription(PrincipleSubscription principleSubscription) {
        this.principleSubscription = principleSubscription;
        return this;
    }

    public void setPrincipleSubscription(PrincipleSubscription principleSubscription) {
        this.principleSubscription = principleSubscription;
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
        Principle principle = (Principle) o;
        if (principle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), principle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Principle{" +
            "id=" + getId() +
            ", nip='" + getNip() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", principleStatus='" + getPrincipleStatus() + "'" +
            "}";
    }
}
