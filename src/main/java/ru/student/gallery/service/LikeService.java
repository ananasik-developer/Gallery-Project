package ru.student.gallery.service;

import ru.student.gallery.dao.LikeDao;

public class LikeService {

    private final LikeDao likeDao;

    public LikeService(LikeDao likeDao) {
        this.likeDao = likeDao;
    }

    public int countByArtwork(long artworkId) {
        return likeDao.countByArtwork(artworkId);
    }

    public boolean isLikedBy(long userId, long artworkId) {
        return likeDao.exists(userId, artworkId);
    }

    public boolean toggle(long userId, long artworkId) {
        if (likeDao.exists(userId, artworkId)) {
            likeDao.remove(userId, artworkId);
            return false;
        }
        likeDao.add(userId, artworkId);
        return true;
    }
}
