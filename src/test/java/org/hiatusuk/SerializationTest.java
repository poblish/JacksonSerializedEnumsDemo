package org.hiatusuk;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import static org.assertj.core.api.Assertions.assertThat;

public class SerializationTest {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testSerializedOutputActive() throws JsonProcessingException {
        String serd = MAPPER.writeValueAsString( new Container("Foo", Status.ACTIVE, "Bar") );
        assertThat(serd).isEqualTo("{\"name\":\"Foo\",\"status\":\"Active state\",\"other\":\"Bar\"}");
    }

    @Test
    public void testSerializedOutputUnknown() throws JsonProcessingException {
        String serd = MAPPER.writeValueAsString( new Container("Foo", Status.UNKNOWN, "Bar") );
        assertThat(serd).isEqualTo("{\"name\":\"Foo\",\"status\":\"???\",\"other\":\"Bar\"}");
    }

    @Test
    public void testSerializedOutputCancelled() throws JsonProcessingException {
        String serd = MAPPER.writeValueAsString( new Container("Foo", Status.CANCELLED_EXPIRED, "Bar") );
        assertThat(serd).isEqualTo("{\"name\":\"Foo\",\"status\":\"CANCELLED_EXPIRED\",\"other\":\"Bar\"}");
    }

    @Test
    public void testDeserActive() throws IOException {
        Container ctr = MAPPER.readValue("{\"name\":\"Foo\",\"status\":\"Active state\",\"other\":\"Bar\"}", Container.class);
        assertThat(ctr.getName()).isEqualTo("Foo");
        assertThat(ctr.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(ctr.getOther()).isEqualTo("Bar");
    }

    @Test
    public void testDeserUnknown() throws IOException {
        Container ctr = MAPPER.readValue("{\"name\":\"Foo\",\"status\":\"???\",\"other\":\"Bar\"}", Container.class);
        assertThat(ctr.getName()).isEqualTo("Foo");
        assertThat(ctr.getStatus()).isEqualTo(Status.UNKNOWN);
        assertThat(ctr.getOther()).isEqualTo("Bar");
    }

    @Test
    public void testDeserWithDefaultedStatus() throws IOException {
        Container ctr = MAPPER.readValue("{\"name\":\"Foo\",\"other\":\"Bar\"}", Container.class);
        assertThat(ctr.getStatus()).isEqualTo(Status.UNKNOWN);
    }

    @Test
    public void testDeserWithUnmappableEnumValue() throws IOException {
        try {
            MAPPER.readValue("{\"name\":\"Foo\",\"status\":\"xxxxxxxx\",\"other\":\"Bar\"}", Container.class);
            Assert.fail("Shoould have failed");
        }
        catch (InvalidFormatException e) {
            assertThat(e.getMessage()).startsWith("Cannot deserialize value");
        }
    }
}