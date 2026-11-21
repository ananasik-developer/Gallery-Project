package ru.student.gallery.dao;

public interface LikeDao {
    boolean exists(long userId, long artworkId);
    void add(long userId, long artworkId);
    void remove(long userId, long artworkId);
    int countByArtwork(long artworkId);
}
