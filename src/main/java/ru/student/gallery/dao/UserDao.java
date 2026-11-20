package ru.student.gallery.dao;

import ru.student.gallery.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    void save(User user);
    void update(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<User> findAll();
}
