package com.ies.smartroom.api.repositories;

import com.ies.smartroom.api.entities.Credential;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CredentialRepository extends MongoRepository<Credential,String> {
    List<Credential> findByHome(long home);
    @Query(value="{'home':?0, 'cart_id':'?1'}", sort="{'timestamp':-1}")
    List<Credential> findHomeAndByCartId(long home, String cartId);
}
