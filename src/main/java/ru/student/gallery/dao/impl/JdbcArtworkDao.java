package ru.student.gallery.dao.impl;

import ru.student.gallery.config.DbConfig;
import ru.student.gallery.dao.ArtworkDao;
import ru.student.gallery.model.Artwork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcArtworkDao implements ArtworkDao {

    @Override
    public void save(Artwork art) {
        String sql = "INSERT INTO artworks (title, description, image_path, author_id, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, art.getTitle());
            ps.setString(2, art.getDescription());
            ps.setString(3, art.getImagePath());
            ps.setLong(4, art.getAuthorId());
            ps.setString(5, art.getStatus() != null ? art.getStatus() : "DRAFT");

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    art.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения работы", e);
        }
    }

    @Override
    public Optional<Artwork> findById(long id) {
        String sql = "SELECT a.*, u.username as author_username FROM artworks a JOIN users u ON a.author_id = u.id WHERE a.id = ?";
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
    public List<Artwork> findPublished(int limit, int offset) {
        String sql = "SELECT a.*, u.username as author_username FROM artworks a JOIN users u ON a.author_id = u.id WHERE a.status = 'PUBLISHED' ORDER BY a.created_at DESC LIMIT ? OFFSET ?";
        List<Artwork> list = new ArrayList<>();
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public int countPublished() {
        String sql = "SELECT COUNT(*) FROM artworks WHERE status = 'PUBLISHED'";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Artwork> findByAuthorId(long authorId) {
        String sql = "SELECT a.*, u.username as author_username FROM artworks a " +
                "JOIN users u ON a.author_id = u.id " +
                "WHERE a.author_id = ? ORDER BY a.created_at DESC";

        List<Artwork> list = new ArrayList<>();
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
    public List<Artwork> findPublishedByTag(long tagId, int limit) {
        String sql = "SELECT a.*, u.username as author_username FROM artworks a " +
                "JOIN users u ON a.author_id = u.id " +
                "JOIN artwork_tags at ON at.artwork_id = a.id " +
                "WHERE a.status = 'PUBLISHED' AND at.tag_id = ? " +
                "ORDER BY a.created_at DESC LIMIT ?";
        List<Artwork> list = new ArrayList<>();
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, tagId);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void update(Artwork art) {
        String sql = "UPDATE artworks SET title=?, description=?, image_path=?, status=?, updated_at=CURRENT_TIMESTAMP WHERE id=?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, art.getTitle());
            ps.setString(2, art.getDescription());
            ps.setString(3, art.getImagePath());
            ps.setString(4, art.getStatus());
            ps.setLong(5, art.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM artworks WHERE id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Artwork> search(String query) {
        String sql = "SELECT a.*, u.username as author_username FROM artworks a " +
                "JOIN users u ON a.author_id = u.id " +
                "WHERE a.status = 'PUBLISHED' AND (" +
                "a.title ILIKE ? OR a.description ILIKE ? OR u.username ILIKE ? OR " +
                "EXISTS (SELECT 1 FROM artwork_tags at " +
                "JOIN tags t ON t.id = at.tag_id " +
                "WHERE at.artwork_id = a.id AND t.name ILIKE ?)) " +
                "ORDER BY (a.title ILIKE ?) DESC, a.created_at DESC";

        String pattern = "%" + query + "%";
        List<Artwork> list = new ArrayList<>();
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            ps.setString(4, pattern);
            ps.setString(5, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private Artwork mapRow(ResultSet rs) throws SQLException {
        Artwork a = new Artwork();
        a.setId(rs.getLong("id"));
        a.setTitle(rs.getString("title"));
        a.setDescription(rs.getString("description"));
        a.setImagePath(rs.getString("image_path"));
        a.setAuthorId(rs.getLong("author_id"));
        a.setStatus(rs.getString("status"));
        a.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) a.setUpdatedAt(updatedAt.toLocalDateTime());

        String username = rs.getString("author_username");
        if (username != null) a.setAuthorUsername(username);

        return a;
    }
}
