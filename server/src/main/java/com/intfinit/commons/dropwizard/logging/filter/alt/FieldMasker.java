package com.intfinit.commons.dropwizard.logging.filter.alt;

import com.google.common.base.Joiner;

import java.util.regex.Pattern;

/**
 * FieldMasker replaces occurrences of matching JSON fields with a mask of "***".
 * It is designed to strip things like passwords and card numbers from logs.
 *
 * TODO - Handle numeric fields as well
 */
public class FieldMasker { private final Pattern pattern;

    public FieldMasker(String... fields) {
        String fieldSet = Joiner.on("|").join(fields);
        String quotePattern = "(\"[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*\")"; // Handle escaped quotes
        pattern = Pattern.compile("\"(" + fieldSet + ")\"(\\s*:\\s*)" + quotePattern);
    }

    public String mask(String string) {
        return pattern.matcher(string).replaceAll("\"$1\"$2\"***\"");
    }
}