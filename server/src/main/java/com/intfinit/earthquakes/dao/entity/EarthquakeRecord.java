package com.intfinit.earthquakes.dao.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@NamedQueries({
        @NamedQuery(
                name = EarthquakeRecord.GET_ALL_EARTHQUAKE_RECORDS,
                query = "select er from EarthquakeRecord as er"
        )
})
@Entity
@Table(name = "earthquake_data")
public class EarthquakeRecord {

    public static final String GET_ALL_EARTHQUAKE_RECORDS = "GET_ALL_EARTHQUAKE_RECORDS";
    public static final String EARTHQUAKE_DATETIME = "earthquake_datetime";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String MAGNITUDE = "magnitude";
    public static final String DEPTH = "depth";
    public static final String REGION = "region";
    public static final String SOURCE_NAME = "source_name";

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

    public Long getId() {
        return id;
    }

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
}