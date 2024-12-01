package ru.kata.spring.boot_security.demo.service;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private UserDao userDao;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserDao userDao, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void saveUser(User user, Set<Role> role, String pass) {
        user.setPassword(passwordEncoder.encode(pass));
        user.setRoles(role);
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user, int id, Set<Role> role, String pass) {
        user.setPassword(passwordEncoder.encode(pass));
        user.setRoles(role);
        user.setId(id);
        userDao.updateUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByName(String username) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        return user.orElse(null);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", email));
        }
        Hibernate.initialize(user.getRoles());
        return user;
    }
}