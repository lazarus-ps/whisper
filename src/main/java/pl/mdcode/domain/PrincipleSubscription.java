package pl.mdcode.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import pl.mdcode.domain.enumeration.PAYMENT_STATUS;

/**
 * A PrincipleSubscription.
 */
@Entity
@Table(name = "principle_subscription")
public class PrincipleSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "payment_token", nullable = false)
    private String paymentToken;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PAYMENT_STATUS paymentStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private SubscriptionDetails subscriptionDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public PrincipleSubscription paymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
        return this;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public PrincipleSubscription startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public PrincipleSubscription endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public PAYMENT_STATUS getPaymentStatus() {
        return paymentStatus;
    }

    public PrincipleSubscription paymentStatus(PAYMENT_STATUS paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PAYMENT_STATUS paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public SubscriptionDetails getSubscriptionDetails() {
        return subscriptionDetails;
    }

    public PrincipleSubscription subscriptionDetails(SubscriptionDetails subscriptionDetails) {
        this.subscriptionDetails = subscriptionDetails;
        return this;
    }

    public void setSubscriptionDetails(SubscriptionDetails subscriptionDetails) {
        this.subscriptionDetails = subscriptionDetails;
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
        PrincipleSubscription principleSubscription = (PrincipleSubscription) o;
        if (principleSubscription.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), principleSubscription.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrincipleSubscription{" +
            "id=" + getId() +
            ", paymentToken='" + getPaymentToken() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            "}";
    }
}
