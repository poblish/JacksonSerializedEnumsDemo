package org.hiatusuk;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializationTest {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testSerializedOutputActive() throws JsonProcessingException {
        String serd = MAPPER.writeValueAsString( new Container("Foo", Status.ACTIVE_STATE, "Bar") );
        assertThat(serd, equalTo("{\"name\":\"Foo\",\"status\":\"Active state\",\"other\":\"Bar\"}"));
    }

    @Test
    public void testSerializedOutputUnknown() throws JsonProcessingException {
        String serd = MAPPER.writeValueAsString( new Container("Foo", Status.UNKNOWN, "Bar") );
        assertThat(serd, equalTo("{\"name\":\"Foo\",\"status\":\"???\",\"other\":\"Bar\"}"));
    }

    @Test
    public void testSerializedOutputCancelled() throws JsonProcessingException {
        String serd = MAPPER.writeValueAsString( new Container("Foo", Status.CANCELLED_BECAUSE, "Bar") );
        assertThat(serd, equalTo("{\"name\":\"Foo\",\"status\":\"CANCELLED_BECAUSE\",\"other\":\"Bar\"}"));
    }

    @Test
    public void testDeserActive() throws IOException {
        Container ctr = MAPPER.readValue("{\"name\":\"Foo\",\"status\":\"Active state\",\"other\":\"Bar\"}", Container.class);
        assertThat(ctr.getName(), equalTo("Foo"));
        assertThat(ctr.getStatus(), equalTo(Status.ACTIVE_STATE));
        assertThat(ctr.getOther(), equalTo("Bar"));
    }

    @Test
    public void testDeserUnknown() throws IOException {
        Container ctr = MAPPER.readValue("{\"name\":\"Foo\",\"status\":\"???\",\"other\":\"Bar\"}", Container.class);
        assertThat(ctr.getName(), equalTo("Foo"));
        assertThat(ctr.getStatus(), equalTo(Status.UNKNOWN));
        assertThat(ctr.getOther(), equalTo("Bar"));
    }

    @Test
    public void testDeserWithDefaultedStatus() throws IOException {
        Container ctr = MAPPER.readValue("{\"name\":\"Foo\",\"other\":\"Bar\"}", Container.class);
        assertThat(ctr.getStatus(), equalTo(Status.UNKNOWN));
    }
}