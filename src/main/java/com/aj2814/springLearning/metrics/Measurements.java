package com.aj2814.springLearning.metrics;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "statistic",
        "value"
})
@Generated("jsonschema2pojo")
public class Measurements {

    @JsonProperty("statistic")
    public String statistic;
    @JsonProperty("value")
    public Integer value;

}