package com.memes.backend.repository;

import com.memes.backend.model.ERole;
import com.memes.backend.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
