package app.model;

import java.util.Arrays;

/**
 * Enumeration of sentiments.
 */
public enum Mood {
    SORROW,
    CALM,
    RAGE;

    public static String getAllNames() {
//        StringBuilder sb = new StringBuilder();
//        for (Mood md : values()) {
//            sb.append(md.name()).append(" ");
//        }
//        return sb.toString().trim();
        return Arrays.stream(values()).map(Enum::name).reduce((a, b) -> a + " " + b).orElse("");
    }
}