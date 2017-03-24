package com.intfinit.commons.dropwizard.logging.filter.alt;

import com.google.common.base.MoreObjects;
import org.slf4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Logs request and response bodies if the specified logger is in DEBUG mode, or the forceLogging
 * function returns true.
 */
public class DynamicBodyLoggingFilter implements ContainerRequestFilter, ClientRequestFilter, ContainerResponseFilter, ClientResponseFilter, WriterInterceptor {
    private static final String ENABLED_PROPERTY = DynamicBodyLoggingFilter.class.getName() + ".enabled";
    private static final Supplier<Boolean> DEFAULT_FORCED = () -> false;

    private final Logger logger;
    private final BodyLoggingFilter bodyLoggingFilter;
    private final Supplier<Boolean> forceLogging;

    public DynamicBodyLoggingFilter(Logger logger, int maxEntitySize, Function<String, String> mask, Supplier<Boolean> forceLogging) {
        this.logger = logger;
        this.forceLogging = MoreObjects.firstNonNull(forceLogging, DEFAULT_FORCED);
        this.bodyLoggingFilter = new BodyLoggingFilter(logger, maxEntitySize, mask);
    }

    private boolean isEnabled() {
        return logger.isDebugEnabled() || forceLogging.get();
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        if (isEnabled()) {
            clientRequestContext.setProperty(ENABLED_PROPERTY, true);
            bodyLoggingFilter.filter(clientRequestContext);
        }
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext, ClientResponseContext clientResponseContext) throws IOException {
        if (clientRequestContext.getProperty(ENABLED_PROPERTY) != null) {
            bodyLoggingFilter.filter(clientRequestContext, clientResponseContext);
        }
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if (isEnabled()) {
            containerRequestContext.setProperty(ENABLED_PROPERTY, true);
            bodyLoggingFilter.filter(containerRequestContext);
        }
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        if (containerRequestContext.getProperty(ENABLED_PROPERTY) != null) {
            bodyLoggingFilter.filter(containerRequestContext, containerResponseContext);
        }
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext) throws IOException, WebApplicationException {
        bodyLoggingFilter.aroundWriteTo(writerInterceptorContext);
    }
}
