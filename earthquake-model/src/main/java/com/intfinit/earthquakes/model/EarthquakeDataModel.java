package com.intfinit.earthquakes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.intfinit.earthquakes.serializers.OffsetDateTimeDeserializer;
import com.intfinit.earthquakes.serializers.OffsetDateTimeSerializer;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

public class EarthquakeDataModel {

    @Size(max = 2)
    @NotNull
    private String sourceName;

    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @NotNull
    private OffsetDateTime earthQuakeDatetime;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    @DecimalMin(value = "0.0")
    private Double magnitude;

    @NotNull
    @DecimalMin(value = "0.0")
    private Double depth;

    @NotBlank
    private String region;

    @JsonCreator
    public EarthquakeDataModel(@JsonProperty("sourceName") String sourceName,
                               @JsonProperty("earthQuakeDatetime") OffsetDateTime earthQuakeDatetime,
                               @JsonProperty("latitude") Double latitude,
                               @JsonProperty("longitude") Double longitude,
                               @JsonProperty("magnitude") Double magnitude,
                               @JsonProperty("depth") Double depth,
                               @JsonProperty("region") String region) {
        this.sourceName = sourceName;
        this.earthQuakeDatetime = earthQuakeDatetime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.magnitude = magnitude;
        this.depth = depth;
        this.region = region;
    }

    public String getSourceName() {
        return sourceName;
    }

    public OffsetDateTime getEarthQuakeDatetime() {
        return earthQuakeDatetime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public Double getDepth() {
        return depth;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EarthquakeDataModel that = (EarthquakeDataModel) o;
        return Objects.equals(sourceName, that.sourceName) &&
                Objects.equals(earthQuakeDatetime, that.earthQuakeDatetime) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(magnitude, that.magnitude) &&
                Objects.equals(depth, that.depth) &&
                Objects.equals(region, that.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceName, earthQuakeDatetime, latitude, longitude, magnitude, depth, region);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("sourceName", sourceName)
                .add("earthQuakeDatetime", earthQuakeDatetime)
                .add("latitude", latitude)
                .add("longitude", longitude)
                .add("magnitude", magnitude)
                .add("depth", depth)
                .add("region", region)
                .toString();
    }
}
