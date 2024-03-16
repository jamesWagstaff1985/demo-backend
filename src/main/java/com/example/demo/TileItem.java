package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TileItem {
    private String slaveId;

    @JsonIgnore
    private Integer red = 0;
    @JsonIgnore
    private Integer green = 0;
    @JsonIgnore
    private Integer blue = 0;
    @JsonIgnore
    private Integer white; // Brightness

    public TileItem(String slaveId, Integer randomValue, Integer white) {
        this.slaveId = slaveId;
        this.white = white;
        switch (randomValue) {
            case 1:
                red = 255;
                break;
            case 2:
                green = 255;
                break;
            default:
                blue = 255;
        }
    }

    public int getWRGB() {
        return (white << 24) | (red << 16) | (green << 8) | blue;
    }
}
