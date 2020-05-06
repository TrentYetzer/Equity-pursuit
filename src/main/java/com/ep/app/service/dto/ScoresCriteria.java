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
 * Criteria class for the {@link com.ep.app.domain.Scores} entity. This class is used
 * in {@link com.ep.app.web.rest.ScoresResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /scores?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ScoresCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter score;

    private LongFilter gamesId;

    public ScoresCriteria() {
    }

    public ScoresCriteria(ScoresCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.score = other.score == null ? null : other.score.copy();
        this.gamesId = other.gamesId == null ? null : other.gamesId.copy();
    }

    @Override
    public ScoresCriteria copy() {
        return new ScoresCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getScore() {
        return score;
    }

    public void setScore(IntegerFilter score) {
        this.score = score;
    }

    public LongFilter getGamesId() {
        return gamesId;
    }

    public void setGamesId(LongFilter gamesId) {
        this.gamesId = gamesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ScoresCriteria that = (ScoresCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(score, that.score) &&
            Objects.equals(gamesId, that.gamesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        score,
        gamesId
        );
    }

    @Override
    public String toString() {
        return "ScoresCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (score != null ? "score=" + score + ", " : "") +
                (gamesId != null ? "gamesId=" + gamesId + ", " : "") +
            "}";
    }

}
