package ru.student.gallery.dao;

import ru.student.gallery.model.Tag;

import java.util.List;

public interface TagDao {
    List<Tag> findAll();
    List<Tag> findByArtwork(long artworkId);
    void setArtworkTags(long artworkId, List<Long> tagIds);
}
