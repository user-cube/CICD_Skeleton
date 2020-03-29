package com.ies.smartroom.api.service;

import java.util.Arrays;
import java.util.List;

import com.ies.smartroom.api.entities.User;
import com.ies.smartroom.api.authentication.model.Role;
import com.ies.smartroom.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Null;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    // this is just an example, you can load the user from the database from the repository

    public User findBycredentials(String email, String password) throws NoSuchFieldException {
        List<User> usersBD = userRepository.findByEmail(email);
        if (usersBD != null) {
            User _user = usersBD.listIterator().next();
            if (_user.getPassword().equals(password)) {
                return _user;
            }
            throw new SecurityException();
        }
        throw new NoSuchFieldException(email);
    }

    public List<User>  listAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

}
