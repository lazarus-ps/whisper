package pl.mdcode.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SubscriptionDetails.
 */
@Entity
@Table(name = "subscription_details")
public class SubscriptionDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Size(max = 300)
    @Column(name = "desctiprion", length = 300, nullable = false)
    private String desctiprion;

    @NotNull
    @Column(name = "number_of_campaigns", nullable = false)
    private Integer numberOfCampaigns;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SubscriptionDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesctiprion() {
        return desctiprion;
    }

    public SubscriptionDetails desctiprion(String desctiprion) {
        this.desctiprion = desctiprion;
        return this;
    }

    public void setDesctiprion(String desctiprion) {
        this.desctiprion = desctiprion;
    }

    public Integer getNumberOfCampaigns() {
        return numberOfCampaigns;
    }

    public SubscriptionDetails numberOfCampaigns(Integer numberOfCampaigns) {
        this.numberOfCampaigns = numberOfCampaigns;
        return this;
    }

    public void setNumberOfCampaigns(Integer numberOfCampaigns) {
        this.numberOfCampaigns = numberOfCampaigns;
    }

    public Double getPrice() {
        return price;
    }

    public SubscriptionDetails price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
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
        SubscriptionDetails subscriptionDetails = (SubscriptionDetails) o;
        if (subscriptionDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subscriptionDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubscriptionDetails{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desctiprion='" + getDesctiprion() + "'" +
            ", numberOfCampaigns='" + getNumberOfCampaigns() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
