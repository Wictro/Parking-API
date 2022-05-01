package com.wictro.cacttus.backend.repository;

import com.wictro.cacttus.backend.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User getUserByUsername(String username);
    User getUserByEmail(String email);
}
