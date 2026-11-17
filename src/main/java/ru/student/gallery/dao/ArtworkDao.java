package ru.student.gallery.dao;

import ru.student.gallery.model.Artwork;
import java.util.List;
import java.util.Optional;

public interface ArtworkDao {
    void save(Artwork artwork);
    Optional<Artwork> findById(long id);
    List<Artwork> findPublished(int limit, int offset);
    List<Artwork> findByAuthorId(long authorId);
    void update(Artwork artwork);
    void delete(long id);
    List<Artwork> searchByTitle(String query);
}
