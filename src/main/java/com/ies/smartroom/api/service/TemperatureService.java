package com.ies.smartroom.api.service;

import com.ies.smartroom.api.repositories.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemperatureService extends SensorService {
    @Autowired
    public TemperatureService(TemperatureRepository temperatureRepository) {
        super(temperatureRepository);
    }
}