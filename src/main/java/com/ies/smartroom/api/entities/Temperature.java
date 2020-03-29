package com.ies.smartroom.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ies.smartroom.api.entities.internal.Sensor;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@ToString
@AllArgsConstructor
@Document(collection = "temperature")
public class Temperature implements Sensor {

    @Id
    @JsonIgnore
    private String id;
    @Field("home")
    private long home;
    @Field("room")
    private String room;
    @Field("timestamp")
    private String timestamp;
    @Field("temp")
    private double temp;

    public String getDate() {
        return this.timestamp.split(" ")[0];
    }

    @Override
    @JsonIgnore
    public double getValue() {
        return this.temp;
    }

    public String getTime() {
        return this.timestamp.split(" ")[1];
    }
}