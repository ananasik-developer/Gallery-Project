package ru.student.gallery.fake;

import ru.student.gallery.dao.ArtworkDao;
import ru.student.gallery.model.Artwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeArtworkDao implements ArtworkDao {

    private final List<Artwork> artworks = new ArrayList<>();
    private long nextId = 1;

    @Override
    public void save(Artwork artwork) {
        if (artwork.getId() == null) {
            artwork.setId(nextId++);
        }
        artworks.add(artwork);
    }

    @Override
    public Optional<Artwork> findById(long id) {
        for (Artwork artwork : artworks) {
            if (artwork.getId() == id) {
                return Optional.of(artwork);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Artwork> findPublished(int limit, int offset) {
        List<Artwork> published = new ArrayList<>();
        for (Artwork artwork : artworks) {
            if ("PUBLISHED".equals(artwork.getStatus())) {
                published.add(artwork);
            }
        }

        int from = Math.min(offset, published.size());
        int to = Math.min(from + limit, published.size());
        return new ArrayList<>(published.subList(from, to));
    }

    @Override
    public int countPublished() {
        int count = 0;
        for (Artwork artwork : artworks) {
            if ("PUBLISHED".equals(artwork.getStatus())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public List<Artwork> findByAuthorId(long authorId) {
        List<Artwork> result = new ArrayList<>();
        for (Artwork artwork : artworks) {
            if (artwork.getAuthorId() == authorId) {
                result.add(artwork);
            }
        }
        return result;
    }

    @Override
    public List<Artwork> findPublishedByTag(long tagId, int limit) {
        return new ArrayList<>();
    }

    @Override
    public void update(Artwork artwork) {
        for (int i = 0; i < artworks.size(); i++) {
            if (artworks.get(i).getId().equals(artwork.getId())) {
                artworks.set(i, artwork);
                return;
            }
        }
    }

    @Override
    public void delete(long id) {
        artworks.removeIf(artwork -> artwork.getId() == id);
    }

    @Override
    public List<Artwork> search(String query) {
        List<Artwork> result = new ArrayList<>();
        for (Artwork artwork : artworks) {
            if ("PUBLISHED".equals(artwork.getStatus())
                    && artwork.getTitle().toLowerCase().contains(query.toLowerCase())) {
                result.add(artwork);
            }
        }
        return result;
    }
}
