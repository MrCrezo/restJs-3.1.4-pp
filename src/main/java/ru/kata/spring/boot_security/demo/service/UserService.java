package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> getUserList();

    User getUser(int id);

    void saveUser(User user, Set<Role> role, String pass);

    void updateUser(User user, int id, Set<Role> role, String pass);

    void deleteUser(int id);

    @Transactional(readOnly = true)
    User getUserByName(String username);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
