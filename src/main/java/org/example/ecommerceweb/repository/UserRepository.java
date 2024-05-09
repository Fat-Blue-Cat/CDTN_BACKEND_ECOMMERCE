package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNameOrEmailAddress(String username, String email);

    boolean existsByUserName(String username);

    boolean existsByEmailAddress(String email);



//    Optional<User> findByUserNameOrEmailAddress(String username, String email);


}
