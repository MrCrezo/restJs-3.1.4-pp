package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query("from User where email = :email")
    User findByEmail(@Param("email") String email);

    @Query(value = "from User u inner join fetch u.roles where u.roles = :roles")
    User getUser();
}
