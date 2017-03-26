package com.intfinit.earthquakes.dao.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

import static com.intfinit.earthquakes.dao.entity.EarthquakeRecord.MIN_MAGNITUDE_PARAM;
import static javax.persistence.GenerationType.IDENTITY;

@NamedQueries({
        @NamedQuery(
                name = EarthquakeRecord.GET_ALL_EARTHQUAKE_RECORDS,
                query = "select er from EarthquakeRecord as er"
        ),
        @NamedQuery(
                name = EarthquakeRecord.GET_EARTHQUAKE_RECORDS_WITH_GE_MAGNITUDE,
                query = "select er from EarthquakeRecord as er where er.magnitude >= :" + MIN_MAGNITUDE_PARAM
        )
})
@Entity
@Table(name = "earthquake_data")
public class EarthquakeRecord {

    public static final String GET_ALL_EARTHQUAKE_RECORDS = "GET_ALL_EARTHQUAKE_RECORDS";
    public static final String GET_EARTHQUAKE_RECORDS_WITH_GE_MAGNITUDE = "GET_EARTHQUAKE_RECORDS_WITH_GE_MAGNITUDE";
    public static final String MIN_MAGNITUDE_PARAM = "min_magnitude";
    private static final String EARTHQUAKE_DATETIME = "earthquake_datetime";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String MAGNITUDE = "magnitude";
    private static final String DEPTH = "depth";
    private static final String REGION = "region";
    private static final String SOURCE_NAME = "source_name";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 2)
    @Column(name = SOURCE_NAME, columnDefinition = "char(2)")
    private String sourceName;

    @NotNull
    @Column(name = EARTHQUAKE_DATETIME)
    @NaturalId
    private OffsetDateTime earthQuakeDatetime;

    @NotNull
    @Column(name = LATITUDE)
    @NaturalId
    private Double latitude;

    @NotNull
    @Column(name = LONGITUDE)
    @NaturalId
    private Double longitude;

    @NotNull
    @Column(name = MAGNITUDE)
    @NaturalId
    private Double magnitude;

    @NotNull
    @Column(name = DEPTH)
    @NaturalId
    private Double depth;

    @NotNull
    @Column(name = REGION)
    private String region;

    public EarthquakeRecord() {
        // no-arg hibernate constructor
    }

    private EarthquakeRecord(String sourceName, OffsetDateTime earthQuakeDatetime, Double latitude, Double longitude,
                             Double magnitude, Double depth, String region) {
        this.sourceName = sourceName;
        this.earthQuakeDatetime = earthQuakeDatetime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.magnitude = magnitude;
        this.depth = depth;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public OffsetDateTime getEarthQuakeDatetime() {
        return earthQuakeDatetime;
    }

    public void setEarthQuakeDatetime(OffsetDateTime earthQuakeDatetime) {
        this.earthQuakeDatetime = earthQuakeDatetime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

        public EarthquakeRecord build() {
            return new EarthquakeRecord(sourceName, earthQuakeDatetime, latitude, longitude, magnitude, depth, region);
        }
    }
}