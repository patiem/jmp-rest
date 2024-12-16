package com.epa.m.multi.jmprest.repository;

import com.epa.m.multi.jmprest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
