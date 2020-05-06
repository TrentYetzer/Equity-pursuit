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
 * Criteria class for the {@link com.ep.app.domain.Hints} entity. This class is used
 * in {@link com.ep.app.web.rest.HintsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /hints?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HintsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter hint;

    private DoubleFilter modifier;

    private LongFilter listingsId;

    public HintsCriteria() {
    }

    public HintsCriteria(HintsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.hint = other.hint == null ? null : other.hint.copy();
        this.modifier = other.modifier == null ? null : other.modifier.copy();
        this.listingsId = other.listingsId == null ? null : other.listingsId.copy();
    }

    @Override
    public HintsCriteria copy() {
        return new HintsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getHint() {
        return hint;
    }

    public void setHint(StringFilter hint) {
        this.hint = hint;
    }

    public DoubleFilter getModifier() {
        return modifier;
    }

    public void setModifier(DoubleFilter modifier) {
        this.modifier = modifier;
    }

    public LongFilter getListingsId() {
        return listingsId;
    }

    public void setListingsId(LongFilter listingsId) {
        this.listingsId = listingsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HintsCriteria that = (HintsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(hint, that.hint) &&
            Objects.equals(modifier, that.modifier) &&
            Objects.equals(listingsId, that.listingsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        hint,
        modifier,
        listingsId
        );
    }

    @Override
    public String toString() {
        return "HintsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (hint != null ? "hint=" + hint + ", " : "") +
                (modifier != null ? "modifier=" + modifier + ", " : "") +
                (listingsId != null ? "listingsId=" + listingsId + ", " : "") +
            "}";
    }

}
