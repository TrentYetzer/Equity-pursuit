package com.ep.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A Games.
 */
@Entity
@Table(name = "games")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Games implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "playtime", nullable = false)
    private String playtime;

    @NotNull
    @Column(name = "listing_list", nullable = false)
    private String listingList;

    @NotNull
    @Column(name = "score", nullable = false)
    private Integer score;

    @NotNull
    @Column(name = "time", nullable = false)
    private ZonedDateTime time;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("games")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaytime() {
        return playtime;
    }

    public Games playtime(String playtime) {
        this.playtime = playtime;
        return this;
    }

    public void setPlaytime(String playtime) {
        this.playtime = playtime;
    }

    public String getListingList() {
        return listingList;
    }

    public Games listingList(String listingList) {
        this.listingList = listingList;
        return this;
    }

    public void setListingList(String listingList) {
        this.listingList = listingList;
    }

    public Integer getScore() {
        return score;
    }

    public Games score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Games time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public Games user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Games)) {
            return false;
        }
        return id != null && id.equals(((Games) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Games{" +
            "id=" + getId() +
            ", playtime='" + getPlaytime() + "'" +
            ", listingList='" + getListingList() + "'" +
            ", score=" + getScore() +
            ", time='" + getTime() + "'" +
            "}";
    }
}
