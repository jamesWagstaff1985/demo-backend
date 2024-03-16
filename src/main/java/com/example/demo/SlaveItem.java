package com.example.demo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SlaveItem {

    private String slaveTopic;
    private String weightPressed;

}
