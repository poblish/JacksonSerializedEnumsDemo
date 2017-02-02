# Jackson Serialized Enums

This project demonstrates the seamless conversion of Java enums to and from custom String representations, with the minimum of extra configuration, using [Jackson 2.x](https://github.com/FasterXML/jackson-databind).

---

Our code maintains the following three statuses:

    Status.UNKNOWN, Status.ACTIVE_STATE, Status.CANCELLED_BECAUSE
    
And we need to synchronise with a JSON-consuming REST service that accepts only the following:

    "???", "Active state", "CANCELLED_BECAUSE"
    
This demonstration shows that no additional logic is required, and that the mapping of `enum <-> String` can be achieved **entirely** using `@JsonProperty("<value>")` mappings:

    public enum Status {
        @JsonProperty("???") UNKNOWN,
        @JsonProperty("Active state") ACTIVE_STATE,
        CANCELLED_BECAUSE
    }

No custom setters or getters, and no special constructors, are required.