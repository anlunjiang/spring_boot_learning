package com.aj2814.springLearning.metrics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "description",
        "baseUnit",
        "measurements",
        "availableTags"
})
@Generated("jsonschema2pojo")
public class RateLimitRemain {

    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("baseUnit")
    public String baseUnit;
    @JsonProperty("measurements")
    public List<Measurements> measurements = null;
    @JsonProperty("availableTags")
    public List<String> availableTags = null;

    public int getMeasurements() {
        return this.measurements.get(0).value;
    }

}