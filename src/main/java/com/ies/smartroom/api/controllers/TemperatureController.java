package com.ies.smartroom.api.controllers;

import com.ies.smartroom.api.service.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/temperature")
public class TemperatureController extends SensorController {

    @Autowired
    public TemperatureController(TemperatureService temperatureService) {
        super(temperatureService);
    }

}
