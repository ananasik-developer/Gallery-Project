package ru.student.gallery.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.student.gallery.config.DbConfig;
import ru.student.gallery.dao.impl.JdbcUserDao;
import ru.student.gallery.model.Role;
import ru.student.gallery.model.User;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JdbcUserDaoTest {

    private final JdbcUserDao userDao = new JdbcUserDao();

    @BeforeEach
    void clearTable() throws Exception {
        try (Connection conn = DbConfig.getConnection();
             Statement statement = conn.createStatement()) {
            statement.execute("TRUNCATE TABLE users RESTART IDENTITY CASCADE");
        }
    }

    @Test
    void saveAndFindById() {
        User user = newUser("laboba", "laboba@local");
        userDao.save(user);

        User found = userDao.findById(user.getId()).orElseThrow();

        assertEquals("laboba", found.getUsername());
        assertEquals("laboba@local", found.getEmail());
        assertEquals(Role.USER, found.getRole());
        assertTrue(found.isActive());
    }

    @Test
    void findByNameAndEmail() {
        User user = newUser("laboba", "laboba@local");
        userDao.save(user);

        assertTrue(userDao.findByUsername("laboba").isPresent());
        assertTrue(userDao.findByEmail("laboba@local").isPresent());
        assertTrue(userDao.findByUsername("nobody").isEmpty());
    }

    @Test
    void updateChangesProfile() {
        User user = newUser("laboba", "laboba@local");
        userDao.save(user);
        user.setUsername("shishka");
        user.setBio("Рисую фоны");

        userDao.update(user);

        User updated = userDao.findById(user.getId()).orElseThrow();
        assertEquals("shishka", updated.getUsername());
        assertEquals("Рисую фоны", updated.getBio());
    }

    @Test
    void setActiveBlocksUser() {
        User user = newUser("laboba", "laboba@local");
        userDao.save(user);

        userDao.setActive(user.getId(), false);

        assertFalse(userDao.findById(user.getId()).orElseThrow().isActive());
    }

    @Test
    void duplicateUsernameIsRejected() {
        userDao.save(newUser("laboba", "laboba@local"));

        assertThrows(RuntimeException.class, () -> userDao.save(newUser("laboba", "other@local")));
    }

    private User newUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash("hash");
        user.setRole(Role.USER);
        user.setActive(true);
        return user;
    }
}
