package ru.student.gallery.fake;

import ru.student.gallery.dao.UserDao;
import ru.student.gallery.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeUserDao implements UserDao {

    private final List<User> users = new ArrayList<>();
    private long nextId = 1;

    @Override
    public Optional<User> findById(long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            user.setId(nextId++);
        }
        users.add(user);
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                return;
            }
        }
    }

    @Override
    public void setActive(long userId, boolean active) {
        findById(userId).ifPresent(user -> user.setActive(active));
    }

    @Override
    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}
