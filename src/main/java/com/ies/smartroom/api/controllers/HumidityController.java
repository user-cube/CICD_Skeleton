package com.ies.smartroom.api.controllers;

import com.ies.smartroom.api.service.HumidityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/humidity")
public class HumidityController extends SensorController {

    @Autowired
    public HumidityController(HumidityService humidityService) {
        super(humidityService);
    }
}

