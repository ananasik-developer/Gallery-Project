package ru.student.gallery.service;

import ru.student.gallery.dao.UserDao;
import ru.student.gallery.model.Role;
import ru.student.gallery.model.User;
import ru.student.gallery.util.PasswordHasher;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void register(String username, String email, String password) {
        if (username == null || username.trim().length() < 3) {
            throw new IllegalArgumentException("Имя пользователя: минимум 3 символа");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Некорректный email");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Пароль: минимум 6 символов");
        }

        String u = username.trim();
        String e = email.trim();

        if (userDao.existsByUsername(u)) {
            throw new IllegalArgumentException("Имя уже занято");
        }
        if (userDao.existsByEmail(e)) {
            throw new IllegalArgumentException("Email уже зарегистрирован");
        }

        User user = new User();
        user.setUsername(u);
        user.setEmail(e);
        user.setPasswordHash(PasswordHasher.hash(password));
        user.setRole(Role.USER);
        user.setActive(true);
        userDao.save(user);
    }

    public Optional<User> authenticate(String username, String password) {
        if (username == null || password == null) return Optional.empty();
        Optional<User> found = userDao.findByUsername(username.trim());
        if (found.isEmpty()) return Optional.empty();
        User u = found.get();
        if (!u.isActive()) return Optional.empty();
        if (!PasswordHasher.matches(password, u.getPasswordHash())) return Optional.empty();
        return Optional.of(u);
    }

    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findById(long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
    }

    public List<User> findAll() {
        return userDao.findAll();
    }
}
