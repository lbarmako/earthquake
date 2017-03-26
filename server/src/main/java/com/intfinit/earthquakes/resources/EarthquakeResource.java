package com.intfinit.earthquakes.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.intfinit.earthquakes.model.EarthquakeRecordModel;
import com.intfinit.earthquakes.model.EarthquakeRecordResponse;
import com.intfinit.earthquakes.services.EarthquakeRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/app/v1/earthquakes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EarthquakeResource {

    private static final Logger LOG = LoggerFactory.getLogger(EarthquakeResource.class);

    private EarthquakeRecordService earthquakeRecordService;

    @Inject
    public EarthquakeResource(EarthquakeRecordService earthquakeRecordService) {
        this.earthquakeRecordService = earthquakeRecordService;
    }

    @GET
    @Timed
    public EarthquakeRecordResponse getEarthquakes(@QueryParam("min_magnitude")
                                                   @DefaultValue("0.0")
                                                   @DecimalMin("0.0") Double minimumMagnitude,
                                                   @QueryParam("limit")
                                                   @DefaultValue("0")
                                                   @DecimalMin("0")
                                                   @DecimalMax("900") Integer limit) {
        LOG.info("Fetching earthquake records with min_magnitude={} and limit={}", minimumMagnitude, limit);
        return earthquakeRecordService.getEarthquakesResponse(minimumMagnitude, limit);
    }

    @POST
    @Timed
    public Response createEarthquakeRecord(EarthquakeRecordModel earthquakeRecordModel) {
        LOG.info("Processing earthquake record model={}", earthquakeRecordModel);
        return earthquakeRecordService.addEarthquakeRecord(earthquakeRecordModel);
    }

}
