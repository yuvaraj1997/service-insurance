package com.boltech.service_insurance.repository;

import com.boltech.service_insurance.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    User getByEmail(String email);

    User getById(String id);
}
