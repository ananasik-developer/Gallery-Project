package ru.student.gallery.dao.impl;

import ru.student.gallery.config.DbConfig;
import ru.student.gallery.dao.SubscriptionDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcSubscriptionDao implements SubscriptionDao {

    @Override
    public boolean exists(long subscriberId, long targetId) {
        String sql = "SELECT 1 FROM subscriptions WHERE subscriber_id = ? AND subscribed_to_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, subscriberId);
            ps.setLong(2, targetId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(long subscriberId, long targetId) {
        String sql = "INSERT INTO subscriptions (subscriber_id, subscribed_to_id) VALUES (?, ?)";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, subscriberId);
            ps.setLong(2, targetId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(long subscriberId, long targetId) {
        String sql = "DELETE FROM subscriptions WHERE subscriber_id = ? AND subscribed_to_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, subscriberId);
            ps.setLong(2, targetId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countFollowers(long userId) {
        return count("SELECT COUNT(*) FROM subscriptions WHERE subscribed_to_id = ?", userId);
    }

    @Override
    public int countFollowing(long userId) {
        return count("SELECT COUNT(*) FROM subscriptions WHERE subscriber_id = ?", userId);
    }

    private int count(String sql, long userId) {
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
