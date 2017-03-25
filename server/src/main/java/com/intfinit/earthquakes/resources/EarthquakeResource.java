package com.intfinit.earthquakes.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.intfinit.earthquakes.model.EarthquakeRecordResponse;
import com.intfinit.earthquakes.services.EarthquakeRecordService;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/earthquakes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EarthquakeResource {

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
        return earthquakeRecordService.getEarthquakesResponse(minimumMagnitude, limit);
    }

}
