package com.ies.smartroom.api.controllers;


import com.ies.smartroom.api.entities.Politic;
import com.ies.smartroom.api.entities.internal.AddPolitic;
import com.ies.smartroom.api.service.PoliticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
@RequestMapping("/politic")
public class PoliticController {

    @Autowired
    private PoliticService politicService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Mono<ResponseEntity> getHomePolitic(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            Politic politic = politicService.getPolitic(home);
            return Mono.just(
                    ResponseEntity.ok(politic)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Mono<ResponseEntity> addHomePolitic(Authentication authentication, @RequestBody AddPolitic addPolitic) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            try {
                Politic savePolitic = politicService.SavePolitic(home, addPolitic);
                return Mono.just(
                        ResponseEntity.ok(savePolitic)
                );
            } catch (Exception ex) {
                return Mono.just(
                        new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT)
                );
            }
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Mono<ResponseEntity> updateHomePolitic(Authentication authentication, @RequestBody AddPolitic addPolitic) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            try {
                Politic updatePolitic = politicService.UpdatePolitic(home, addPolitic);
                return Mono.just(
                        ResponseEntity.ok(updatePolitic)
                );
            } catch (Exception ex) {
                return Mono.just(
                        new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT)
                );
            }
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public Mono<ResponseEntity> deleteHomePolitic(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            try {
                Politic deletePolitic = politicService.DeletePolitic(home);
                return Mono.just(
                        ResponseEntity.ok(deletePolitic)
                );
            } catch (Exception ex) {
                return Mono.just(
                        new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT)
                );
            }
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
