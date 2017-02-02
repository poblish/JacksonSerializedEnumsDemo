package org.hiatusuk;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Container {

    @JsonProperty private String name;
    @JsonProperty private Status status = Status.UNKNOWN;
    @JsonProperty private String other;

    public Container() {
        // Non-@JsonCreator default ctor. No need for setters!
    }

    public Container(String name, Status status, String other) {
        this.name = name;
        this.status = status;
        this.other = other;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public String getOther() {
        return other;
    }
}
