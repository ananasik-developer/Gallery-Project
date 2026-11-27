package ru.student.gallery.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.student.gallery.fake.FakeUserDao;
import ru.student.gallery.model.Role;
import ru.student.gallery.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest {

    private FakeUserDao userDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDao = new FakeUserDao();
        userService = new UserService(userDao);
    }

    @Test
    void registerSavesActiveUser() {
        userService.register("laboba", "laboba@local", "secret123");

        User saved = userDao.findByUsername("laboba").orElseThrow();

        assertEquals(Role.USER, saved.getRole());
        assertTrue(saved.isActive());
        assertNotEquals("secret123", saved.getPasswordHash());
    }

    @Test
    void registerRejectsShortLogin() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.register("ab", "ab@local", "secret123"));
    }

    @Test
    void registerRejectsBusyLogin() {
        userService.register("laboba", "laboba@local", "secret123");

        assertThrows(IllegalArgumentException.class,
                () -> userService.register("laboba", "other@local", "secret123"));
    }

    @Test
    void authenticateWithCorrectPassword() {
        userService.register("laboba", "laboba@local", "secret123");

        Optional<User> user = userService.authenticate("laboba", "secret123");

        assertTrue(user.isPresent());
    }

    @Test
    void authenticateWithWrongPassword() {
        userService.register("laboba", "laboba@local", "secret123");

        assertTrue(userService.authenticate("laboba", "wrong").isEmpty());
    }

    @Test
    void authenticateRejectsBlockedUser() {
        userService.register("laboba", "laboba@local", "secret123");
        long id = userDao.findByUsername("laboba").orElseThrow().getId();

        userDao.setActive(id, false);

        assertTrue(userService.authenticate("laboba", "secret123").isEmpty());
    }
}
