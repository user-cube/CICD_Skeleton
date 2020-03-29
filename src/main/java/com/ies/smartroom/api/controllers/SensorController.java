package com.ies.smartroom.api.controllers;

import com.ies.smartroom.api.entities.internal.Average;
import com.ies.smartroom.api.entities.internal.Sensor;
import com.ies.smartroom.api.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@RestController
public abstract class SensorController {

    protected SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAll(Authentication authentication) {
        if (authentication.isAuthenticated()) {

            long home = getAuthenticationHome(authentication);
            return Mono.just(
                    ResponseEntity.ok(sensorService.findAll(home))
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }
    @RequestMapping(value = "/{day}", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAllByDay(@PathVariable String day, Authentication authentication) {
        if (authentication.isAuthenticated()) {

            long home = getAuthenticationHome(authentication);
            List<?> sensors = sensorService.getByDay(day, home);
            return Mono.just(
                    ResponseEntity.ok(sensors)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }
    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAllOfToday(Authentication authentication) {
        if (authentication.isAuthenticated()) {

            long home = getAuthenticationHome(authentication);
            List<?> sensors = sensorService.getNow(home);
            return Mono.just(
                    ResponseEntity.ok(sensors)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/", params = {"from", "to"}, method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> GetAllByDateRange(@RequestParam String from, @RequestParam String to, Authentication authentication) {
        if (authentication.isAuthenticated()) {

            long home = getAuthenticationHome(authentication);
            List<?> sensors = sensorService.getByDateRange(from, to, home);
            return Mono.just(
                    ResponseEntity.ok(sensors)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/week", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAllOfLastWeek(Authentication authentication) {
        if (authentication.isAuthenticated()) {

            long home = getAuthenticationHome(authentication);
            List<?> sensors = sensorService.getLastWeek(home);
            return Mono.just(
                    ResponseEntity.ok(sensors)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/average", params = {"from", "to"}, method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAverageByDate(@RequestParam String from, @RequestParam String to, Authentication authentication) {
        if (authentication.isAuthenticated()) {

            long home = getAuthenticationHome(authentication);
            List<Average> average = sensorService.getAverageRange(from, to, home);
            return Mono.just(
                    ResponseEntity.ok(average)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/average/week", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAverageOfLastWeek(Authentication authentication) {
        if (authentication.isAuthenticated()) {

            long home = getAuthenticationHome(authentication);
            List<Average>  average = sensorService.getAverageWeek(home);
            return Mono.just(
                    ResponseEntity.ok(average)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/average/{day}", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAverageByDay(@PathVariable String day, Authentication authentication) {
        if (authentication.isAuthenticated()) {

            long home = getAuthenticationHome(authentication);
            Average average = sensorService.getAverageDay(day, home);
            return Mono.just(
                    ResponseEntity.ok(average)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }
    @RequestMapping(value = "/average/today", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAverageOfToday(Authentication authentication) {
        if (authentication.isAuthenticated()) {

            long home = getAuthenticationHome(authentication);
            Average average = sensorService.getAverageToday(home);
            return Mono.just(
                    ResponseEntity.ok(average)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    private long getAuthenticationHome(Authentication authentication){
        Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
        return Long.valueOf(String.valueOf(obj));
    }
}
