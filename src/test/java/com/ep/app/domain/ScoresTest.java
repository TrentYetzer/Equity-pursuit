package com.ep.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ep.app.web.rest.TestUtil;

public class ScoresTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scores.class);
        Scores scores1 = new Scores();
        scores1.setId(1L);
        Scores scores2 = new Scores();
        scores2.setId(scores1.getId());
        assertThat(scores1).isEqualTo(scores2);
        scores2.setId(2L);
        assertThat(scores1).isNotEqualTo(scores2);
        scores1.setId(null);
        assertThat(scores1).isNotEqualTo(scores2);
    }
}
