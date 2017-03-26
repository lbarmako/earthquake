package com.intfinit.earthquakes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.intfinit.earthquakes.serializers.OffsetDateTimeDeserializer;
import com.intfinit.earthquakes.serializers.OffsetDateTimeSerializer;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

public class EarthquakeRecordModel {

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
    public EarthquakeRecordModel(@JsonProperty("sourceName") String sourceName,
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EarthquakeRecordModel that = (EarthquakeRecordModel) o;
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

    public static class Builder {
        private String sourceName;
        private OffsetDateTime earthQuakeDatetime;
        private Double latitude;
        private Double longitude;
        private Double magnitude;
        private Double depth;
        private String region;

        public Builder withSourceName(String sourceName) {
            this.sourceName = sourceName;
            return this;
        }

        public Builder withEarthQuakeDatetime(OffsetDateTime earthQuakeDatetime) {
            this.earthQuakeDatetime = earthQuakeDatetime;
            return this;
        }

        public Builder withLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withMagnitude(Double magnitude) {
            this.magnitude = magnitude;
            return this;
        }

        public Builder withDepth(Double depth) {
            this.depth = depth;
            return this;
        }

        public Builder withRegion(String region) {
            this.region = region;
            return this;
        }

        public EarthquakeRecordModel build() {
            return new EarthquakeRecordModel(sourceName, earthQuakeDatetime, latitude, longitude, magnitude, depth, region);
        }
    }
}
