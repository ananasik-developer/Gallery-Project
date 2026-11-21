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

    List<Gallery> findAvailableForCommission();
    void setCommission(long galleryId, boolean available);

    List<Gallery> findFeatured();
    void setFeatured(long galleryId, boolean featured);
}
