package web.app.caravanamedieval.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    COMERCIANTE,
    CARAVANERO;

    @JsonCreator
    public static Role fromString(String value) {
        return Role.valueOf(value.toUpperCase());
    }
}
