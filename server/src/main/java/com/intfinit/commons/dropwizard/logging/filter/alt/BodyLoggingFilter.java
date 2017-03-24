package com.intfinit.commons.dropwizard.logging.filter.alt;

import com.google.common.base.MoreObjects;
import org.glassfish.jersey.filter.LoggingFilter;
import org.slf4j.Logger;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.util.function.Function;

/**
 * Logs the full request and response bodies using Jersey's in-built filters.
 */
@PreMatching
@Priority(Integer.MIN_VALUE)
public class BodyLoggingFilter implements ContainerRequestFilter, ClientRequestFilter, ContainerResponseFilter, ClientResponseFilter, WriterInterceptor {
    private final LoggingFilter loggingFilter;

    public BodyLoggingFilter(Logger logger, int maxEntitySize, Function<String, String> mask) {
        loggingFilter = new LoggingFilter(new LoggingForwarder(logger, mask), maxEntitySize);
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        loggingFilter.filter(clientRequestContext);
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext, ClientResponseContext clientResponseContext) throws IOException {
        loggingFilter.filter(clientRequestContext, clientResponseContext);
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        loggingFilter.filter(containerRequestContext);
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        loggingFilter.filter(containerRequestContext, containerResponseContext);
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext) throws IOException, WebApplicationException {
        loggingFilter.aroundWriteTo(writerInterceptorContext);
    }

    /**
     * Forwards to an SLF4 logger (optionally masking private data).
     */
    static class LoggingForwarder extends java.util.logging.Logger {
        private static final Function<String, String> DEFAULT_MASK = (s) -> s;
        private final Logger logger;
        private final Function<String, String> mask;

        public LoggingForwarder(Logger logger, Function<String, String> mask) {
            super(LoggingForwarder.class.getName(), null);
            this.logger = logger;
            this.mask = MoreObjects.firstNonNull(mask, DEFAULT_MASK);
        }

        @Override
        public void info(String msg) {
            logger.info(mask.apply(msg));
        }
    }
}
