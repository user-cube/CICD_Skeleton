package com.ies.smartroom.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ies.smartroom.api.entities.internal.Sensor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@ToString
@AllArgsConstructor
@Document(collection = "humidity")
public class Humidity implements Sensor {
    @Id
    @JsonIgnore
    private String id;
    @Field("home")
    private long home;
    @Field("room")
    private String room;
    @Field("timestamp")
    private String timestamp;
    @Field("humidity")
    private double humidity;

    public String getDate() {
        return this.timestamp.split(" ")[0];
    }

    @Override
    @JsonIgnore
    public double getValue() {
        return this.humidity;
    }

    public String getTime() {
        return this.timestamp.split(" ")[1];
    }
}