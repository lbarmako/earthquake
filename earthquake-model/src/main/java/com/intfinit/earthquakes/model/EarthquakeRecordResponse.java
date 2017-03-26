package com.intfinit.earthquakes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.hash;

public class EarthquakeRecordResponse implements Serializable {

    private static final long serialVersionUID = 379639607700235261L;
    @NotNull
    private Integer recordCount;

    @NotNull
    private List<EarthquakeRecordModel> earthquakes;

    @JsonCreator
    public EarthquakeRecordResponse(@JsonProperty("recordCount") Integer recordCount,
                                    @JsonProperty("earthquakes") List<EarthquakeRecordModel> earthquakes) {
        this.recordCount = recordCount;
        this.earthquakes = earthquakes;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public List<EarthquakeRecordModel> getEarthquakes() {
        return earthquakes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EarthquakeRecordResponse that = (EarthquakeRecordResponse) o;
        return Objects.equals(recordCount, that.recordCount) &&
                Objects.equals(earthquakes, that.earthquakes);
    }

    @Override
    public int hashCode() {
        return hash(recordCount, earthquakes);
    }

    public static class Builder {
        private Integer recordCount;
        private List<EarthquakeRecordModel> earthquakes;

        public Builder withRecordCount(Integer recordCount) {
            this.recordCount = recordCount;
            return this;
        }

        public Builder withEarthquakes(List<EarthquakeRecordModel> earthquakes) {
            this.earthquakes = earthquakes;
            return this;
        }

        public EarthquakeRecordResponse build() {
            return new EarthquakeRecordResponse(recordCount, earthquakes);
        }

    }
}
