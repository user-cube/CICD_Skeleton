package com.ies.smartroom.api.entities.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Sensor {
     String getDate();
     long getHome();
     String getRoom();
     @JsonIgnore
     double getValue();
}
