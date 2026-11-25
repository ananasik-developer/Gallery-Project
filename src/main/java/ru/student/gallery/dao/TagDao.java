package ru.student.gallery.dao;

import ru.student.gallery.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    List<Tag> findAll();
    List<Tag> findByArtwork(long artworkId);
    Optional<Tag> findByName(String name);
    void save(Tag tag);
    void setArtworkTags(long artworkId, List<Long> tagIds);
}
