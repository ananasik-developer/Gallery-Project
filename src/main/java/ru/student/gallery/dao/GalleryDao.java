package ru.student.gallery.dao;

import ru.student.gallery.model.Artwork;
import ru.student.gallery.model.Gallery;
import java.util.List;
import java.util.Optional;

public interface GalleryDao {
    void save(Gallery gallery);
    Optional<Gallery> findById(long id);
    List<Gallery> findByAuthorId(long authorId);
    void addArtworkToGallery(long galleryId, long artworkId, int sortOrder);
    void removeArtworkFromGallery(long galleryId, long artworkId);

    List<Artwork> getGalleryArtworks(long galleryId);

    boolean containsArtwork(long galleryId, long artworkId);

    List<Gallery> findAvailableForCommission();

    List<Gallery> findFeatured();

    List<Gallery> findPopular(int limit);

    void setPublication(long galleryId, boolean featured, boolean available);

    void delete(long galleryId);
}
