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
@Document(collection="credential")
public class Credential {
    @Id
    @JsonIgnore
    private String id;
    @Field("home")
    private long home;
    @Field("user")
    private String user;
    @Field("cart_id")
    private String cart_id;
}
