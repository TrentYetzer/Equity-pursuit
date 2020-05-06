package com.ep.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Listings.
 */
@Entity
@Table(name = "listings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Listings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "zpid", nullable = false, unique = true)
    private Integer zpid;

    @NotNull
    @Column(name = "street", nullable = false)
    private String street;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "state", nullable = false)
    private String state;

    @NotNull
    @Column(name = "zip_code", nullable = false)
    private Integer zipCode;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "zestimate", nullable = false)
    private Double zestimate;

    @NotNull
    @Column(name = "address", nullable = false)
    private Integer address;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getZpid() {
        return zpid;
    }

    public Listings zpid(Integer zpid) {
        this.zpid = zpid;
        return this;
    }

    public void setZpid(Integer zpid) {
        this.zpid = zpid;
    }

    public String getStreet() {
        return street;
    }

    public Listings street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public Listings city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Listings state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public Listings zipCode(Integer zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Double getZestimate() {
        return zestimate;
    }

    public Listings zestimate(Double zestimate) {
        this.zestimate = zestimate;
        return this;
    }

    public void setZestimate(Double zestimate) {
        this.zestimate = zestimate;
    }

    public Integer getAddress() {
        return address;
    }

    public Listings address(Integer address) {
        this.address = address;
        return this;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Listings longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Listings latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Listings)) {
            return false;
        }
        return id != null && id.equals(((Listings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Listings{" +
            "id=" + getId() +
            ", zpid=" + getZpid() +
            ", street='" + getStreet() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", zipCode=" + getZipCode() +
            ", zestimate=" + getZestimate() +
            ", address=" + getAddress() +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            "}";
    }
}
