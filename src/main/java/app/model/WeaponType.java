package app.model;

import java.util.Arrays;

/**
 * Listing of weapons
 */
public enum WeaponType {
    HAMMER,
    AXE,
    SHOTGUN,
    RIFLE,
    KNIFE;

    public static String getAllNames() {
//        StringBuilder sb = new StringBuilder();
//        for (WeaponType wt : values()) {
//            sb.append(wt.name()).append(" ");
//        }
//        return sb.toString().trim();
        return Arrays.stream(values()).map(Enum::name).reduce((a, b) -> a + " " + b).orElse("");
    }
}