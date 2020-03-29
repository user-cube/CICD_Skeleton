package com.ies.smartroom.api.controllers;

import com.ies.smartroom.api.service.Co2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/co2")
public class Co2Controller extends SensorController {

    @Autowired
    public Co2Controller(Co2Service co2Service) {     super(co2Service);   }
}
