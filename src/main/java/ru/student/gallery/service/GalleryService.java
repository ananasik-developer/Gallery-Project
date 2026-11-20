package ru.student.gallery.service;

import ru.student.gallery.dao.GalleryDao;
import ru.student.gallery.model.Artwork;
import ru.student.gallery.model.Gallery;
import ru.student.gallery.model.SessionUser;

import java.util.List;

public class GalleryService {
    private final GalleryDao galleryDao;

    public GalleryService(GalleryDao galleryDao) {
        this.galleryDao = galleryDao;
    }

    public void createGallery(Gallery gallery, SessionUser author) {
        gallery.setAuthorId(author.getId());
        if (gallery.getTitle() == null || gallery.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Название галереи обязательно");
        }
        galleryDao.save(gallery);
    }

    public List<Artwork> getGalleryArtworks(long galleryId) {
        return galleryDao.getGalleryArtworks(galleryId);
    }

    public List<Gallery> getUserGalleries(SessionUser user) {
        return galleryDao.findByAuthorId(user.getId());
    }

    public void addArtwork(long galleryId, long artworkId, SessionUser owner) {
        Gallery g = galleryDao.findById(galleryId)
                .orElseThrow(() -> new RuntimeException("Галерея не найдена"));

        if (!g.getAuthorId().equals(owner.getId())) {
            throw new RuntimeException("Нет прав на редактирование чужой галереи");
        }

        galleryDao.addArtworkToGallery(galleryId, artworkId, 0);
    }

    public Gallery findById(long id) {
        return galleryDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Галерея не найдена"));
    }
}
