package com.ep.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ep.app.web.rest.TestUtil;

public class HintsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hints.class);
        Hints hints1 = new Hints();
        hints1.setId(1L);
        Hints hints2 = new Hints();
        hints2.setId(hints1.getId());
        assertThat(hints1).isEqualTo(hints2);
        hints2.setId(2L);
        assertThat(hints1).isNotEqualTo(hints2);
        hints1.setId(null);
        assertThat(hints1).isNotEqualTo(hints2);
    }
}
