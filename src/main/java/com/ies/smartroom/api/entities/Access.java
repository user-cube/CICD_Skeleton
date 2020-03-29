package com.ies.smartroom.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@ToString
@Document(collection="access")
public class Access {

    @Id
    @JsonIgnore
    private String id;
    @Field("home")
    private long home;
    @Field("room")
    private String room;
    @Field("timestamp")
    private String timestamp;
    @Field("user")
    private String user;
    @Field("origin")
    private String origin;
    @Field("cart_id")
    private String cart_id;
    @Field("action")
    private String action;


    public String getDate() {
        return this.timestamp.split(" ")[0];
    }
    public String getTime() {
        return this.timestamp.split(" ")[1];
    }

}
