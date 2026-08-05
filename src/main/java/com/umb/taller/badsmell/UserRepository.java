package com.umb.taller.badsmell;

import com.umb.taller.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
    Optional<User> findByEmail(String email);
}
