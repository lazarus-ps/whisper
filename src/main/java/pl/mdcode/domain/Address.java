package pl.mdcode.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "place_or_city", length = 30, nullable = false)
    private String placeOrCity;

    @Size(max = 30)
    @Column(name = "street", length = 30)
    private String street;

    @NotNull
    @Size(max = 30)
    @Column(name = "province", length = 30, nullable = false)
    private String province;

    @NotNull
    @Size(max = 6)
    @Column(name = "postal_code", length = 6, nullable = false)
    private String postalCode;

    @ManyToOne(optional = false)
    @NotNull
    private UserData userData;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaceOrCity() {
        return placeOrCity;
    }

    public Address placeOrCity(String placeOrCity) {
        this.placeOrCity = placeOrCity;
        return this;
    }

    public void setPlaceOrCity(String placeOrCity) {
        this.placeOrCity = placeOrCity;
    }

    public String getStreet() {
        return street;
    }

    public Address street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getProvince() {
        return province;
    }

    public Address province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Address postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public UserData getUserData() {
        return userData;
    }

    public Address userData(UserData userData) {
        this.userData = userData;
        return this;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
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
        Address address = (Address) o;
        if (address.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), address.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", placeOrCity='" + getPlaceOrCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", province='" + getProvince() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            "}";
    }
}
