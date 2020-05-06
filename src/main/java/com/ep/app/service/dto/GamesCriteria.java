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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.ep.app.domain.Games} entity. This class is used
 * in {@link com.ep.app.web.rest.GamesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /games?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GamesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter playtime;

    private StringFilter listingList;

    private IntegerFilter score;

    private ZonedDateTimeFilter time;

    private LongFilter userId;

    public GamesCriteria() {
    }

    public GamesCriteria(GamesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.playtime = other.playtime == null ? null : other.playtime.copy();
        this.listingList = other.listingList == null ? null : other.listingList.copy();
        this.score = other.score == null ? null : other.score.copy();
        this.time = other.time == null ? null : other.time.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public GamesCriteria copy() {
        return new GamesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPlaytime() {
        return playtime;
    }

    public void setPlaytime(StringFilter playtime) {
        this.playtime = playtime;
    }

    public StringFilter getListingList() {
        return listingList;
    }

    public void setListingList(StringFilter listingList) {
        this.listingList = listingList;
    }

    public IntegerFilter getScore() {
        return score;
    }

    public void setScore(IntegerFilter score) {
        this.score = score;
    }

    public ZonedDateTimeFilter getTime() {
        return time;
    }

    public void setTime(ZonedDateTimeFilter time) {
        this.time = time;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GamesCriteria that = (GamesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(playtime, that.playtime) &&
            Objects.equals(listingList, that.listingList) &&
            Objects.equals(score, that.score) &&
            Objects.equals(time, that.time) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        playtime,
        listingList,
        score,
        time,
        userId
        );
    }

    @Override
    public String toString() {
        return "GamesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (playtime != null ? "playtime=" + playtime + ", " : "") +
                (listingList != null ? "listingList=" + listingList + ", " : "") +
                (score != null ? "score=" + score + ", " : "") +
                (time != null ? "time=" + time + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
