package com.ep.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ep.app.web.rest.TestUtil;

public class ListingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Listings.class);
        Listings listings1 = new Listings();
        listings1.setId(1L);
        Listings listings2 = new Listings();
        listings2.setId(listings1.getId());
        assertThat(listings1).isEqualTo(listings2);
        listings2.setId(2L);
        assertThat(listings1).isNotEqualTo(listings2);
        listings1.setId(null);
        assertThat(listings1).isNotEqualTo(listings2);
    }
}
