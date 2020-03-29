package com.ies.smartroom.api.repositories;

import com.ies.smartroom.api.entities.Politic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PoliticRepository extends MongoRepository<Politic,String> {
    List<Politic> findByHome(long home);
}
