package com.ies.smartroom.api.service;

import com.ies.smartroom.api.entities.Credential;
import com.ies.smartroom.api.entities.Politic;
import com.ies.smartroom.api.entities.internal.AddPolitic;
import com.ies.smartroom.api.repositories.PoliticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ListIterator;

@Service
public class PoliticService {

    @Autowired
    private PoliticRepository politicRepository;

    public Politic getPolitic(long home) {
        ListIterator<Politic> politics = politicRepository.findByHome(home).listIterator();
        if (politics.hasNext())
            return politics.next();
        return null;
    }

    public Politic SavePolitic(long home, AddPolitic addPolitic) throws Exception {
        ListIterator<Politic> politics = politicRepository.findByHome(home).listIterator();
        if (politics.hasNext())
            throw new Exception("Politic already exists");
        return politicRepository.save(new Politic(null, home, addPolitic.getCo2(), addPolitic.getTemp_min(), addPolitic. getTemp_max()));
    }

    public Politic UpdatePolitic(long home, AddPolitic addPolitic) throws Exception {
        ListIterator<Politic> politics = politicRepository.findByHome(home).listIterator();
        if (!politics.hasNext())
            throw new Exception("Politic is not defined");
        Politic politic = politics.next();

        politic.setCo2(addPolitic.getCo2());
        politic.setTemp_min(addPolitic.getTemp_min());
        politic.setTemp_max(addPolitic.getTemp_max());

        return politicRepository.save(politic);
    }

    public Politic DeletePolitic(long home) throws Exception {
        ListIterator<Politic> politics = politicRepository.findByHome(home).listIterator();
        if (!politics.hasNext())
            throw new Exception("Politic is not defined");
        Politic politic = politics.next();
        politicRepository.delete(politic);
        return politic;
    }
}
