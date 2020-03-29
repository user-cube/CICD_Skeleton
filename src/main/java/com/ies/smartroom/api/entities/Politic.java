package com.ies.smartroom.api.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@ToString
@AllArgsConstructor
@Document(collection="politic")
public class Politic {
    @Id
    @JsonIgnore
    private String id;
    @Field("home")
    private long home;
    @Field("co2")
    private double co2;
    @Field("temp_min")
    private double temp_min;
    @Field("temp_max")
    private double temp_max;
}
