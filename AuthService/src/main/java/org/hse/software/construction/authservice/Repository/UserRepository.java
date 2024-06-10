package org.hse.software.construction.authservice.Repository;

import org.hse.software.construction.authservice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
