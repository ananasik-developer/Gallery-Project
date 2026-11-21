package ru.student.gallery.dao.impl;

import ru.student.gallery.config.DbConfig;
import ru.student.gallery.dao.GalleryDao;
import ru.student.gallery.model.Artwork;
import ru.student.gallery.model.Gallery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGalleryDao implements GalleryDao {

    @Override
    public void save(Gallery g) {
        String sql = "INSERT INTO galleries (title, description, cover_artwork_id, author_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, g.getTitle());
            ps.setString(2, g.getDescription());
            ps.setObject(3, g.getCoverArtworkId(), Types.BIGINT);
            ps.setLong(4, g.getAuthorId());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) g.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания галереи", e);
        }
    }

    @Override
    public List<Artwork> getGalleryArtworks(long galleryId) {
        String sql = "SELECT a.*, u.username as author_username FROM artworks a " +
                "JOIN users u ON a.author_id = u.id " +
                "JOIN gallery_artworks ga ON a.id = ga.artwork_id " +
                "WHERE ga.gallery_id = ? ORDER BY ga.sort_order ASC";

        List<Artwork> list = new ArrayList<>();
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, galleryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Artwork a = new Artwork();
                    a.setId(rs.getLong("id"));
                    a.setTitle(rs.getString("title"));
                    a.setDescription(rs.getString("description"));
                    a.setImagePath(rs.getString("image_path"));
                    a.setAuthorId(rs.getLong("author_id"));
                    a.setStatus(rs.getString("status"));
                    a.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                    String username = rs.getString("author_username");
                    if (username != null) a.setAuthorUsername(username);
                    list.add(a);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Gallery> findById(long id) {
        String sql = "SELECT g.*, u.username as author_username FROM galleries g JOIN users u ON g.author_id = u.id WHERE g.id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Gallery> findByAuthorId(long authorId) {
        String sql = "SELECT g.*, u.username as author_username FROM galleries g " +
                "JOIN users u ON g.author_id = u.id " +
                "WHERE g.author_id = ? ORDER BY g.created_at DESC";
        List<Gallery> list = new ArrayList<>();
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, authorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void addArtworkToGallery(long galleryId, long artworkId, int sortOrder) {
        String sql = "INSERT INTO gallery_artworks (gallery_id, artwork_id, sort_order) VALUES (?, ?, ?)";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, galleryId);
            ps.setLong(2, artworkId);
            ps.setInt(3, sortOrder);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления работы в галерею", e);
        }
    }

    @Override
    public void removeArtworkFromGallery(long galleryId, long artworkId) {
        String sql = "DELETE FROM gallery_artworks WHERE gallery_id = ? AND artwork_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, galleryId);
            ps.setLong(2, artworkId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Gallery> findAvailableForCommission() {
        String sql = "SELECT g.*, u.username as author_username FROM galleries g " +
                "JOIN users u ON g.author_id = u.id " +
                "WHERE g.available_for_commission = true " +
                "ORDER BY g.created_at DESC";
        List<Gallery> list = new ArrayList<>();
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void setCommission(long galleryId, boolean available) {
        String sql = "UPDATE galleries SET available_for_commission = ? WHERE id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, available);
            ps.setLong(2, galleryId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Gallery> findFeatured() {
        String sql = "SELECT g.*, u.username as author_username FROM galleries g " +
                "JOIN users u ON g.author_id = u.id " +
                "WHERE g.featured = true " +
                "ORDER BY g.created_at DESC";
        List<Gallery> list = new ArrayList<>();
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void setFeatured(long galleryId, boolean featured) {
        String sql = "UPDATE galleries SET featured = ? WHERE id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, featured);
            ps.setLong(2, galleryId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Gallery mapRow(ResultSet rs) throws SQLException {
        Gallery g = new Gallery();
        g.setId(rs.getLong("id"));
        g.setTitle(rs.getString("title"));
        g.setDescription(rs.getString("description"));
        g.setCoverArtworkId(rs.getObject("cover_artwork_id", Long.class));
        g.setAuthorId(rs.getLong("author_id"));
        g.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        String username = rs.getString("author_username");
        if (username != null) g.setAuthorUsername(username);

        g.setAvailableForCommission(rs.getBoolean("available_for_commission"));
        g.setFeatured(rs.getBoolean("featured"));

        return g;
    }
}