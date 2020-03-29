package com.ies.smartroom.api.controllers;

import com.ies.smartroom.api.entities.Credential;
import com.ies.smartroom.api.entities.internal.AddCredential;
import com.ies.smartroom.api.entities.internal.RemoveCredential;
import com.ies.smartroom.api.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/access")
public class AccessController {

    @Autowired
    private AccessService accessService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAll(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            return Mono.just(
                    ResponseEntity.ok(accessService.findAll(home))
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/{day}", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAccessOfToday(@PathVariable String day, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            List<?> acs = accessService.getByDay(day, home);
            return Mono.just(ResponseEntity.ok(acs));
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAccessOfToday(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            List<?> acs = accessService.today(home);
            return Mono.just(ResponseEntity.ok(acs));
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/", params = {"from", "to"}, method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAccessByDateRange(@RequestParam String from, @RequestParam String to, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            List<?> acs = accessService.getByDateRange(from, to, home);
            return Mono.just(ResponseEntity.ok(acs));
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/week", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getAccessOfLastWeek(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            List<?> acs = accessService.getLastWeek(home);
            return Mono.just(
                    ResponseEntity.ok(acs)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
    public Mono<ResponseEntity> getUnauthorizedAccess(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            List<?> acs = accessService.getUnauthorizedAccess(home);
            return Mono.just(
                    ResponseEntity.ok(acs)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/unauthorized/last", method = RequestMethod.GET)
    public Mono<ResponseEntity> getLastUnauthorizedAccess(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            List<?> acs = accessService.getLastUnauthorizedAccess(home);
            return Mono.just(
                    ResponseEntity.ok(acs)
            );
        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/credential", method = RequestMethod.GET)
    public Mono<ResponseEntity> getAuthorizedCredentials(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            List<Credential> credentials = accessService.getAllCredentials(home);
            return Mono.just(
                    ResponseEntity.ok(credentials)
            );

        }
        return Mono.just(
                new ResponseEntity<>("Login is Needed", HttpStatus.UNAUTHORIZED)
        );
    }

    @RequestMapping(value = "/credential", method = RequestMethod.POST)
    public Mono<ResponseEntity> addCredential(Authentication authentication, @RequestBody AddCredential addCcredential) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            try {
                Credential saveCredential = accessService.SaveCredential(home, addCcredential);
                return Mono.just(
                        ResponseEntity.ok(saveCredential)
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



    @RequestMapping(value = "/credential", method = RequestMethod.PUT)
    public Mono<ResponseEntity> updateCredential(Authentication authentication, @RequestBody AddCredential AddCredential) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            try {
                Credential updateCredential = accessService.UpdateCredential(home, AddCredential);
                return Mono.just(
                        ResponseEntity.ok(updateCredential)
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
    @RequestMapping(value = "/credential", method = RequestMethod.DELETE)
    public Mono<ResponseEntity> deleteCredential(Authentication authentication, @RequestBody RemoveCredential cart_id) {
        if (authentication.isAuthenticated()) {
            long home = getAuthenticationHome(authentication);
            try {
                Credential deleteCredential = accessService.DeleteCredential(home, cart_id.getCart_id());
                return Mono.just(
                        ResponseEntity.ok(deleteCredential)
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

    private long getAuthenticationHome(Authentication authentication) {
        Object obj = ((HashMap<String, Object>) authentication.getPrincipal()).get("home");
        return Long.valueOf(String.valueOf(obj));
    }

}
