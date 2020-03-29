package com.ies.smartroom.api.repositories;

import com.ies.smartroom.api.entities.internal.Sensor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface SensorRepository<T> extends MongoRepository<T, String> {
    List<Sensor> findByHome(long home);
    @Query(value="{'home':?0,'timestamp':{$gte:?1,$lt:?2}} ", sort="{'timestamp':-1}")
    List<Sensor> findByHomeAndRange(long home, String from, String to);
}
