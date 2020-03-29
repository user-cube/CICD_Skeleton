package com.ies.smartroom.api.service;

import com.ies.smartroom.api.entities.Access;
import com.ies.smartroom.api.entities.Credential;
import com.ies.smartroom.api.entities.internal.AddCredential;
import com.ies.smartroom.api.repositories.AccessRepository;
import com.ies.smartroom.api.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccessService {

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private CredentialRepository credentialRepository;


    public List<Access> findAll(long home) {
        return accessRepository.findByHome(home);
    }

    public List<Access> getByDay(String day, long home) {
        try {
            return accessRepository.findByHomeAndDate(
                    home,
                    startDayToTimeStamp(day),
                    endDayToTimeStamp(day)
            );
        } catch (Exception e) {
            return null;
        }
    }

    public List<Access> today(long home) {
        return getByDay(LocalDate.now().toString(), home);
    }

    public List<Access> getByDateRange(String from, String to, long home) {
        List<Access> acs;
        try{
            acs = accessRepository.findByHomeAndDate(
                    home,
                    startDayToTimeStamp(from),
                    endDayToTimeStamp(to)
            );
            return acs;
        } catch (Exception ex){
            return null;
        }
    }

    public List<Access> getLastWeek(long home) {
        LocalDate today = LocalDate.now();
        String to = today.toString();
        String from = today.minusDays(7).toString();
        return getByDateRange(from,to,home);
    }

    public List<Access> getUnauthorizedAccess(long home){
        return accessRepository.findUnauthorized(home);
    }

    public List<Access> getLastUnauthorizedAccess(long home){
        List <Access> acs = new ArrayList<>();
        acs.add(
                getUnauthorizedAccess(home)
                        .listIterator().next());
        return acs;
    }

    public List<Credential> getAllCredentials(long home){
        return credentialRepository.findByHome(home);
    }

    public Credential SaveCredential(long home, AddCredential addCredential) throws Exception {
        List<Credential> credentials = credentialRepository.findHomeAndByCartId(home, addCredential.getCart_id());
        if (!credentials.isEmpty()) {
            throw new Exception("Cart Id already added");
        }
        Credential credential = new Credential(null, home, addCredential.getUser(), addCredential.getCart_id());
        return credentialRepository.save(credential);
    }
    public Credential UpdateCredential(long home, AddCredential addCredential) throws Exception {
        List<Credential> credentials = credentialRepository.findHomeAndByCartId(home, addCredential.getCart_id());
        if (credentials.isEmpty()) {
            throw new Exception("Cart Id is not registered");
        }
        Credential credential = credentials.listIterator().next();
        credential.setUser(addCredential.getUser());
        return credentialRepository.save(credential);
    }

    public Credential DeleteCredential(long home, String cart_id) throws Exception {
        List<Credential> credentials = credentialRepository.findHomeAndByCartId(home, cart_id);
        if (credentials.isEmpty()){
            throw new Exception("Cart Id is not authorized");
        }
        Credential credential = credentials.listIterator().next();
        credentialRepository.delete(credential);
        return credential;
    }

    private String startDayToTimeStamp(String day){
        return day + " 00:00:00";
    }

    private String endDayToTimeStamp(String day){
        return day+" 23:59:59";
    }

}
