package org.hiatusuk;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {

    @JsonProperty("???") UNKNOWN,
    @JsonProperty("Active state") ACTIVE,
    CANCELLED_EXPIRED
}
