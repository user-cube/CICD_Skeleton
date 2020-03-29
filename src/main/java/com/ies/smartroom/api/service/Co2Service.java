package com.ies.smartroom.api.service;

import com.ies.smartroom.api.repositories.Co2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Co2Service extends SensorService {
    @Autowired
    public Co2Service(Co2Repository co2Repository) {
        super(co2Repository);
    }
}