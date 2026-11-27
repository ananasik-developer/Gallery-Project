package ru.student.gallery.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.student.gallery.exception.AccessDeniedException;
import ru.student.gallery.fake.FakeArtworkDao;
import ru.student.gallery.model.Artwork;
import ru.student.gallery.model.Role;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArtworkServiceTest {

    private FakeArtworkDao artworkDao;
    private ArtworkService artworkService;
    private SessionUser owner;
    private SessionUser other;

    @BeforeEach
    void setUp() {
        artworkDao = new FakeArtworkDao();
        artworkService = new ArtworkService(artworkDao);
        owner = new SessionUser(sessionUser(1, "laboba"));
        other = new SessionUser(sessionUser(2, "shishka"));
    }

    @Test
    void createdArtworkIsDraft() {
        Artwork artwork = new Artwork();
        artwork.setTitle("Красота 1");

        artworkService.createArtwork(artwork, owner);

        assertEquals("DRAFT", artwork.getStatus());
        assertEquals(1L, artwork.getAuthorId());
    }

    @Test
    void createRejectsEmptyTitle() {
        Artwork artwork = new Artwork();
        artwork.setTitle("   ");

        assertThrows(IllegalArgumentException.class,
                () -> artworkService.createArtwork(artwork, owner));
    }

    @Test
    void publishChangesStatus() {
        Artwork artwork = givenArtwork("Красота 1", 1, "DRAFT");

        artworkService.publishArtwork(artwork.getId(), owner);

        assertEquals("PUBLISHED", artworkDao.findById(artwork.getId()).orElseThrow().getStatus());
    }

    @Test
    void publishingForeignArtworkIsDenied() {
        Artwork artwork = givenArtwork("Красота 1", 1, "DRAFT");

        assertThrows(AccessDeniedException.class,
                () -> artworkService.publishArtwork(artwork.getId(), other));
    }

    @Test
    void draftIsHiddenFromOtherUsers() {
        Artwork artwork = givenArtwork("Скетч", 1, "DRAFT");

        assertThrows(AccessDeniedException.class,
                () -> artworkService.getArtworkById(artwork.getId(), other));
    }

    @Test
    void publishedArtworkCannotBeEdited() {
        Artwork artwork = givenArtwork("Красный шарф", 1, "PUBLISHED");

        assertThrows(AccessDeniedException.class,
                () -> artworkService.updateArtwork(artwork.getId(), "Новое имя", "Описание", owner));
    }

    @Test
    void deletingForeignArtworkIsDenied() {
        Artwork artwork = givenArtwork("Красота 1", 1, "PUBLISHED");

        assertThrows(AccessDeniedException.class,
                () -> artworkService.deleteArtwork(artwork.getId(), other));
    }

    private Artwork givenArtwork(String title, long authorId, String status) {
        Artwork artwork = new Artwork();
        artwork.setTitle(title);
        artwork.setImagePath("/uploads/artworks/test.jpg");
        artwork.setAuthorId(authorId);
        artwork.setStatus(status);
        artworkDao.save(artwork);
        return artwork;
    }

    private User sessionUser(long id, String username) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setRole(Role.USER);
        return user;
    }
}
