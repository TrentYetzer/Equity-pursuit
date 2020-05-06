package com.ep.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Hints.
 */
@Entity
@Table(name = "hints")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Hints implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "hint", nullable = false)
    private String hint;

    @NotNull
    @Column(name = "modifier", nullable = false)
    private Double modifier;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("hints")
    private Listings listings;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHint() {
        return hint;
    }

    public Hints hint(String hint) {
        this.hint = hint;
        return this;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Double getModifier() {
        return modifier;
    }

    public Hints modifier(Double modifier) {
        this.modifier = modifier;
        return this;
    }

    public void setModifier(Double modifier) {
        this.modifier = modifier;
    }

    public Listings getListings() {
        return listings;
    }

    public Hints listings(Listings listings) {
        this.listings = listings;
        return this;
    }

    public void setListings(Listings listings) {
        this.listings = listings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hints)) {
            return false;
        }
        return id != null && id.equals(((Hints) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Hints{" +
            "id=" + getId() +
            ", hint='" + getHint() + "'" +
            ", modifier=" + getModifier() +
            "}";
    }
}
