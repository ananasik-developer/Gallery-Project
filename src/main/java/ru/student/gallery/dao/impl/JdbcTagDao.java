package ru.student.gallery.dao.impl;

import ru.student.gallery.config.DbConfig;
import ru.student.gallery.dao.TagDao;
import ru.student.gallery.model.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTagDao implements TagDao {

    @Override
    public List<Tag> findAll() {
        String sql = "SELECT t.id, t.name, t.category, COUNT(at.artwork_id) AS usage_count " +
                "FROM tags t LEFT JOIN artwork_tags at ON at.tag_id = t.id " +
                "GROUP BY t.id, t.name, t.category " +
                "ORDER BY t.category, t.name";
        List<Tag> list = new ArrayList<>();
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Tag tag = new Tag();
                tag.setId(rs.getLong("id"));
                tag.setName(rs.getString("name"));
                tag.setCategory(rs.getString("category"));
                tag.setUsageCount(rs.getInt("usage_count"));
                list.add(tag);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Tag> findByArtwork(long artworkId) {
        String sql = "SELECT t.id, t.name, t.category FROM tags t " +
                "JOIN artwork_tags at ON at.tag_id = t.id " +
                "WHERE at.artwork_id = ? " +
                "ORDER BY t.name";
        List<Tag> list = new ArrayList<>();
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, artworkId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tag tag = new Tag();
                    tag.setId(rs.getLong("id"));
                    tag.setName(rs.getString("name"));
                    tag.setCategory(rs.getString("category"));
                    list.add(tag);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        String sql = "SELECT id, name, category FROM tags WHERE LOWER(name) = LOWER(?) LIMIT 1";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tag tag = new Tag();
                    tag.setId(rs.getLong("id"));
                    tag.setName(rs.getString("name"));
                    tag.setCategory(rs.getString("category"));
                    return Optional.of(tag);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Tag tag) {
        String sql = "INSERT INTO tags (name, category) VALUES (?, ?)";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tag.getName());
            ps.setString(2, tag.getCategory());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    tag.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArtworkTags(long artworkId, List<Long> tagIds) {
        String deleteSql = "DELETE FROM artwork_tags WHERE artwork_id = ?";
        String insertSql = "INSERT INTO artwork_tags (artwork_id, tag_id) VALUES (?, ?)";

        try (Connection conn = DbConfig.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement delete = conn.prepareStatement(deleteSql)) {
                delete.setLong(1, artworkId);
                delete.executeUpdate();
            }

            try (PreparedStatement insert = conn.prepareStatement(insertSql)) {
                for (Long tagId : tagIds) {
                    insert.setLong(1, artworkId);
                    insert.setLong(2, tagId);
                    insert.addBatch();
                }
                insert.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
