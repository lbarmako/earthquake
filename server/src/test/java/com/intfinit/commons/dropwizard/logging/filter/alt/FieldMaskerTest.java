package com.intfinit.commons.dropwizard.logging.filter.alt;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldMaskerTest {

    @Test
    public void shouldMaskSimpleFieldIgnoringWhitespace() {
        FieldMasker masker = new FieldMasker("password");

        assertThat(masker.mask("\"password\":\"hello\"")).isEqualTo("\"password\":\"***\"");
        assertThat(masker.mask("\n \"password\" : \n\"hello\" ")).isEqualTo("\n \"password\" : \n\"***\" ");
    }

    @Test
    public void shouldMaskMultipleFields() {
        String payload = "{\"email\":\"automated.test4@intfinit.com\",\"password\":\"Pass\\\"word1234#\"," +
                "\"firstName\":\"firstName\",\"surname\":\"surname\",\"dob\":\"1995-07-28\"," +
                "\"mobile\":null,\"verificationUrl\":null,\"optInForPromotions\":false,\"optInForSms\":false}";

        FieldMasker masker = new FieldMasker("password", "dob");

        String mask = masker.mask(payload);

        assertThat(mask).isEqualTo("{\"email\":\"automated.test4@intfinit.com\",\"password\":\"***\"," +
                "\"firstName\":\"firstName\",\"surname\":\"surname\",\"dob\":\"***\"," +
                "\"mobile\":null,\"verificationUrl\":null,\"optInForPromotions\":false,\"optInForSms\":false}");
    }
}