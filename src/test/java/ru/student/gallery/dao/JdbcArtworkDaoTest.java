package ru.student.gallery.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.student.gallery.config.DbConfig;
import ru.student.gallery.dao.impl.JdbcArtworkDao;
import ru.student.gallery.dao.impl.JdbcUserDao;
import ru.student.gallery.model.Artwork;
import ru.student.gallery.model.Role;
import ru.student.gallery.model.User;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JdbcArtworkDaoTest {

    private final JdbcUserDao userDao = new JdbcUserDao();
    private final JdbcArtworkDao artworkDao = new JdbcArtworkDao();

    @BeforeEach
    void clearTables() throws Exception {
        try (Connection conn = DbConfig.getConnection();
             Statement statement = conn.createStatement()) {
            statement.execute("TRUNCATE TABLE users RESTART IDENTITY CASCADE");
        }
    }

    @Test
    void savedArtworkKeepsAuthorName() {
        User author = newUser("laboba");
        Artwork artwork = newArtwork(author.getId(), "Красота 1", "DRAFT");

        Artwork found = artworkDao.findById(artwork.getId()).orElseThrow();

        assertEquals("Красота 1", found.getTitle());
        assertEquals("laboba", found.getAuthorUsername());
    }

    @Test
    void publishedFeedIgnoresDrafts() {
        User author = newUser("laboba");
        newArtwork(author.getId(), "Красота 1", "PUBLISHED");
        newArtwork(author.getId(), "Красота 2", "PUBLISHED");
        newArtwork(author.getId(), "Скетч", "DRAFT");

        assertEquals(2, artworkDao.countPublished());
        assertEquals(2, artworkDao.findPublished(10, 0).size());
    }

    @Test
    void artworksAreFoundByAuthor() {
        User first = newUser("laboba");
        User second = newUser("shishka");
        newArtwork(first.getId(), "Красота 1", "PUBLISHED");
        newArtwork(first.getId(), "Красота 2", "DRAFT");
        newArtwork(second.getId(), "Красота 3", "PUBLISHED");

        List<Artwork> artworks = artworkDao.findByAuthorId(first.getId());

        assertEquals(2, artworks.size());
    }

    @Test
    void updateChangesTitleAndStatus() {
        User author = newUser("laboba");
        Artwork artwork = newArtwork(author.getId(), "Красный шарф", "DRAFT");
        artwork.setTitle("Красота 1, версия 2");
        artwork.setStatus("PUBLISHED");

        artworkDao.update(artwork);

        Artwork updated = artworkDao.findById(artwork.getId()).orElseThrow();
        assertEquals("Красота 1, версия 2", updated.getTitle());
        assertEquals("PUBLISHED", updated.getStatus());
    }

    @Test
    void deleteRemovesArtwork() {
        User author = newUser("laboba");
        Artwork artwork = newArtwork(author.getId(), "Красота 1", "PUBLISHED");

        artworkDao.delete(artwork.getId());

        assertTrue(artworkDao.findById(artwork.getId()).isEmpty());
    }

    private User newUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(username + "@local");
        user.setPasswordHash("hash");
        user.setRole(Role.USER);
        user.setActive(true);
        userDao.save(user);
        return user;
    }

    private Artwork newArtwork(long authorId, String title, String status) {
        Artwork artwork = new Artwork();
        artwork.setTitle(title);
        artwork.setImagePath("/uploads/artworks/test.jpg");
        artwork.setAuthorId(authorId);
        artwork.setStatus(status);
        artworkDao.save(artwork);
        return artwork;
    }
}
