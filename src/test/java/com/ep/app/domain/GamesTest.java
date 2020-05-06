package com.ep.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ep.app.web.rest.TestUtil;

public class GamesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Games.class);
        Games games1 = new Games();
        games1.setId(1L);
        Games games2 = new Games();
        games2.setId(games1.getId());
        assertThat(games1).isEqualTo(games2);
        games2.setId(2L);
        assertThat(games1).isNotEqualTo(games2);
        games1.setId(null);
        assertThat(games1).isNotEqualTo(games2);
    }
}
