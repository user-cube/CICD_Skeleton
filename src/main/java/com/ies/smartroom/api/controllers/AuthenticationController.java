package com.ies.smartroom.api.controllers;


import com.ies.smartroom.api.authentication.CredentialsdAuthenticationToken;
import com.ies.smartroom.api.authentication.JWTUtil;
import com.ies.smartroom.api.authentication.model.AuthRequest;
import com.ies.smartroom.api.authentication.model.AuthResponse;
import com.ies.smartroom.api.authentication.model.Message;
import com.ies.smartroom.api.entities.User;
import com.ies.smartroom.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class AuthenticationController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
        try {
            User userDetails = userService.findBycredentials(ar.email, ar.password);
            String token = jwtUtil.generateToken(userDetails);
            return Mono.just(
                    ResponseEntity.ok(new AuthResponse(token))
            );
        } catch (Exception e) {
            return Mono.just(
                    new ResponseEntity<>("Unauthenticated", HttpStatus.UNAUTHORIZED)
            );
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> listUsers() {
        return Mono.just(
                ResponseEntity.ok(userService.listAll()));//, HttpStatus.OK)
    }

    @RequestMapping(value = "/tokenInfo", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getTokenInfo(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return Mono.just(ResponseEntity.ok(new Message(authentication.toString())));
        }
        return Mono.just(
                new ResponseEntity<>("Unauthenticated", HttpStatus.UNAUTHORIZED)
        );
    }
}
