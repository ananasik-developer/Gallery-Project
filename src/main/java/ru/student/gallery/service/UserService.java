package ru.student.gallery.service;

import ru.student.gallery.dao.UserDao;
import ru.student.gallery.exception.AccessDeniedException;
import ru.student.gallery.exception.NotFoundException;
import ru.student.gallery.model.Role;
import ru.student.gallery.model.SessionUser;
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

    public User updateProfile(long userId, String username, String bio, String tgLink, String avatarPath) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        String newName = username == null ? "" : username.trim();
        if (newName.length() < 3) {
            throw new IllegalArgumentException("Имя пользователя: минимум 3 символа");
        }
        if (newName.length() > 50) {
            throw new IllegalArgumentException("Имя пользователя: максимум 50 символов");
        }

        Optional<User> sameName = userDao.findByUsername(newName);
        if (sameName.isPresent() && !sameName.get().getId().equals(userId)) {
            throw new IllegalArgumentException("Имя уже занято");
        }

        String newBio = bio == null ? "" : bio.trim();
        if (newBio.length() > 1000) {
            throw new IllegalArgumentException("О себе: максимум 1000 символов");
        }

        String newTg = tgLink == null ? "" : tgLink.trim();
        if (newTg.length() > 255) {
            throw new IllegalArgumentException("Ссылка: максимум 255 символов");
        }

        user.setUsername(newName);
        user.setBio(newBio.isEmpty() ? null : newBio);
        user.setTgLink(newTg.isEmpty() ? null : newTg);
        user.setAvatarPath(avatarPath);

        userDao.update(user);
        return user;
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
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public boolean isActive(long userId) {
        return userDao.findById(userId)
                .map(User::isActive)
                .orElse(false);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void setActive(long userId, boolean active, SessionUser admin) {
        if (admin.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Действие доступно только администратору");
        }
        if (admin.getId() == userId) {
            throw new IllegalArgumentException("Нельзя заблокировать самого себя");
        }

        userDao.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        userDao.setActive(userId, active);
    }
}
