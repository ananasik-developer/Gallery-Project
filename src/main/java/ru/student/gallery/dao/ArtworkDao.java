package ru.student.gallery.dao;

import ru.student.gallery.model.Artwork;
import java.util.List;
import java.util.Optional;

public interface ArtworkDao {
    void save(Artwork artwork);
    Optional<Artwork> findById(long id);
    List<Artwork> findPublished(int limit, int offset);
    int countPublished();
    List<Artwork> findByAuthorId(long authorId);
    List<Artwork> findPublishedByTag(long tagId, int limit);
    void update(Artwork artwork);
    void delete(long id);
    List<Artwork> search(String query);
}
