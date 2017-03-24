package com.intfinit.commons.dropwizard.logging.filter.alt;

import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class BodyLoggingFilterTest {
    private Logger logger = mock(Logger.class);
    private FieldMasker masker = new FieldMasker("password");

    @Rule
    public ResourceTestRule resources = ResourceTestRule.builder()
            .addProvider(new BodyLoggingFilter(logger, 1000, masker::mask))
            .setMapper(Jackson.newObjectMapper())
            .addResource(new MyResource())
            .build();

    @Test
    public void shouldLogRequestAndResponseBodies() {
        List<String> messages = new ArrayList<>();
        doAnswer(invocation -> messages.add((String) invocation.getArguments()[0])).when(logger).info(anyString());

        Response response = resources.getJerseyTest().target("/foo").request().post(Entity.json(new MyRequest()));
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

        assertThat(messages.size()).isEqualTo(2);

        assertThat(messages.get(0)).contains("1 > Content-Length: 37").contains("{\"user\":\"Andrew\",\"password\":\"***\"}");

        assertThat(messages.get(1)).contains("1 < 200\n").contains("\nfoo\n");
    }

    @Path("/")
    public static class MyResource {
        @POST
        @Path("/foo")
        public String foo(MyRequest request) {
            return "foo";
        }
    }

    public static class MyRequest {
        public String user = "Andrew";
        public String password = "secret";
    }
}