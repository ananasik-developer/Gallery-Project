package ru.student.gallery.service;

import ru.student.gallery.dao.ArtworkDao;
import ru.student.gallery.model.Artwork;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.model.User;

import java.util.List;

public class ArtworkService {

    private final ArtworkDao artworkDao;

    public ArtworkService(ArtworkDao artworkDao) {
        this.artworkDao = artworkDao;
    }

    public List<Artwork> getFeed(SessionUser currentUser, int limit) {
        return artworkDao.findPublished(limit, 0);
    }

    public void createArtwork(Artwork artwork, SessionUser author) {
        artwork.setAuthorId(author.getId());
        artwork.setStatus("DRAFT");

        if (artwork.getTitle() == null || artwork.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Название работы обязательно");
        }

        artworkDao.save(artwork);
    }

    public void publishArtwork(long artworkId, SessionUser currentUser) {
        Artwork art = artworkDao.findById(artworkId)
                .orElseThrow(() -> new RuntimeException("Работа не найдена"));

        if (!art.getAuthorId().equals(currentUser.getId())) {
            throw new RuntimeException("Нет прав на публикацию чужой работы");
        }

        art.setStatus("PUBLISHED");
        artworkDao.update(art);
    }

    public Artwork getArtworkById(long id, SessionUser currentUser) {
        Artwork art = artworkDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Работа не найдена"));

        if ("DRAFT".equals(art.getStatus())) {
            if (currentUser == null || !art.getAuthorId().equals(currentUser.getId())) {
                throw new RuntimeException("Нет прав на просмотр чужого черновика");
            }
        }

        return art;
    }

    public List<Artwork> getPublishedByAuthor(long authorId) {
        return artworkDao.findByAuthorId(authorId).stream()
                .filter(a -> "PUBLISHED".equals(a.getStatus()))
                .toList();
    }

    public List<Artwork> getDraftsByAuthor(long authorId) {
        return artworkDao.findByAuthorId(authorId).stream()
                .filter(a -> "DRAFT".equals(a.getStatus()))
                .toList();
    }

    public List<Artwork> search(String query) {
        if (query == null || query.length() < 2) {
            return List.of();
        }
        return artworkDao.searchByTitle(query);
    }
}