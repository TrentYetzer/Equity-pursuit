package com.ep.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.ep.app.domain.Listings} entity. This class is used
 * in {@link com.ep.app.web.rest.ListingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /listings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ListingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter zpid;

    private StringFilter street;

    private StringFilter city;

    private StringFilter state;

    private IntegerFilter zipCode;

    private DoubleFilter zestimate;

    private IntegerFilter address;

    private DoubleFilter longitude;

    private DoubleFilter latitude;

    public ListingsCriteria() {
    }

    public ListingsCriteria(ListingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.zpid = other.zpid == null ? null : other.zpid.copy();
        this.street = other.street == null ? null : other.street.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.zipCode = other.zipCode == null ? null : other.zipCode.copy();
        this.zestimate = other.zestimate == null ? null : other.zestimate.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
    }

    @Override
    public ListingsCriteria copy() {
        return new ListingsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getZpid() {
        return zpid;
    }

    public void setZpid(IntegerFilter zpid) {
        this.zpid = zpid;
    }

    public StringFilter getStreet() {
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public IntegerFilter getZipCode() {
        return zipCode;
    }

    public void setZipCode(IntegerFilter zipCode) {
        this.zipCode = zipCode;
    }

    public DoubleFilter getZestimate() {
        return zestimate;
    }

    public void setZestimate(DoubleFilter zestimate) {
        this.zestimate = zestimate;
    }

    public IntegerFilter getAddress() {
        return address;
    }

    public void setAddress(IntegerFilter address) {
        this.address = address;
    }

    public DoubleFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(DoubleFilter longitude) {
        this.longitude = longitude;
    }

    public DoubleFilter getLatitude() {
        return latitude;
    }

    public void setLatitude(DoubleFilter latitude) {
        this.latitude = latitude;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ListingsCriteria that = (ListingsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(zpid, that.zpid) &&
            Objects.equals(street, that.street) &&
            Objects.equals(city, that.city) &&
            Objects.equals(state, that.state) &&
            Objects.equals(zipCode, that.zipCode) &&
            Objects.equals(zestimate, that.zestimate) &&
            Objects.equals(address, that.address) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(latitude, that.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        zpid,
        street,
        city,
        state,
        zipCode,
        zestimate,
        address,
        longitude,
        latitude
        );
    }

    @Override
    public String toString() {
        return "ListingsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (zpid != null ? "zpid=" + zpid + ", " : "") +
                (street != null ? "street=" + street + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (zipCode != null ? "zipCode=" + zipCode + ", " : "") +
                (zestimate != null ? "zestimate=" + zestimate + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
            "}";
    }

}
