package com.ies.smartroom.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ies.smartroom.api.entities.internal.Sensor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@ToString
@Document(collection="co2")
public class Co2 implements Sensor {
    @Id
    private String id;
    @Field("home")
    private long home;
    @Field("room")
    private String room;
    @Field("timestamp") @JsonIgnore
    private String timestamp;
    @Field("co2(ppm)")
    private double co2;

    public String getDate() {
        return this.timestamp.split(" ")[0];
    }

    @Override  @JsonIgnore
    public double getValue() {
        return this.co2;
    }
    public String getTime() {
        return this.timestamp.split(" ")[1];
    }
}