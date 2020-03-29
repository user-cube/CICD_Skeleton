package com.ies.smartroom.api.repositories;

import com.ies.smartroom.api.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {
    List<User> findByEmail(String email);
}
