package ru.student.gallery.service;

import ru.student.gallery.dao.ArtworkDao;
import ru.student.gallery.dao.GalleryDao;
import ru.student.gallery.exception.AccessDeniedException;
import ru.student.gallery.exception.NotFoundException;
import ru.student.gallery.model.Artwork;
import ru.student.gallery.model.Gallery;
import ru.student.gallery.model.SessionUser;

import java.util.List;

public class GalleryService {
    private final GalleryDao galleryDao;
    private final ArtworkDao artworkDao;

    public GalleryService(GalleryDao galleryDao, ArtworkDao artworkDao) {
        this.galleryDao = galleryDao;
        this.artworkDao = artworkDao;
    }

    public void createGallery(Gallery gallery, SessionUser author) {
        gallery.setAuthorId(author.getId());
        if (gallery.getTitle() == null || gallery.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Название галереи обязательно");
        }
        galleryDao.save(gallery);
    }

    public List<Artwork> getGalleryArtworks(long galleryId) {
        return galleryDao.getGalleryArtworks(galleryId).stream()
                .filter(a -> "PUBLISHED".equals(a.getStatus()))
                .toList();
    }

    public List<Gallery> getCommissionGalleries() {
        return fillArtworks(galleryDao.findAvailableForCommission());
    }

    public List<Gallery> getFeaturedGalleries() {
        return fillArtworks(galleryDao.findFeatured());
    }

    public void setPublication(long galleryId, boolean featured, boolean available, SessionUser owner) {
        Gallery gallery = galleryDao.findById(galleryId)
                .orElseThrow(() -> new NotFoundException("Галерея не найдена"));

        if (!gallery.getAuthorId().equals(owner.getId())) {
            throw new AccessDeniedException("Нет прав на изменение чужой галереи");
        }

        galleryDao.setPublication(galleryId, featured, available);
    }

    private List<Gallery> fillArtworks(List<Gallery> galleries) {
        for (Gallery gallery : galleries) {
            gallery.setArtworks(getGalleryArtworks(gallery.getId()));
        }
        return galleries;
    }

    public List<Gallery> getPopularGalleries(int limit) {
        return fillArtworks(galleryDao.findPopular(limit));
    }

    public List<Gallery> getByAuthorId(long authorId) {
        return galleryDao.findByAuthorId(authorId);
    }

    public void addArtwork(long galleryId, long artworkId, SessionUser owner) {
        Gallery gallery = galleryDao.findById(galleryId)
                .orElseThrow(() -> new NotFoundException("Галерея не найдена"));

        if (!gallery.getAuthorId().equals(owner.getId())) {
            throw new AccessDeniedException("Нет прав на редактирование чужой галереи");
        }

        Artwork artwork = artworkDao.findById(artworkId)
                .orElseThrow(() -> new NotFoundException("Работа не найдена"));

        if (!artwork.getAuthorId().equals(owner.getId())) {
            throw new AccessDeniedException("В галерею можно добавлять только свои работы");
        }

        if (!"PUBLISHED".equals(artwork.getStatus())) {
            throw new IllegalArgumentException("Сначала опубликуйте работу");
        }

        if (galleryDao.containsArtwork(galleryId, artworkId)) {
            throw new IllegalArgumentException("Эта работа уже есть в галерее");
        }

        int order = galleryDao.getGalleryArtworks(galleryId).size();
        galleryDao.addArtworkToGallery(galleryId, artworkId, order);
    }

    public Gallery findById(long id) {
        return galleryDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Галерея не найдена"));
    }

    public void deleteGallery(long galleryId, SessionUser owner) {
        Gallery gallery = galleryDao.findById(galleryId)
                .orElseThrow(() -> new NotFoundException("Галерея не найдена"));

        if (!gallery.getAuthorId().equals(owner.getId())) {
            throw new AccessDeniedException("Нет прав на удаление чужой галереи");
        }

        galleryDao.delete(galleryId);
    }
}
