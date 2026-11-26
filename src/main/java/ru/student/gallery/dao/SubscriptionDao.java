package ru.student.gallery.dao;

public interface SubscriptionDao {
    boolean exists(long subscriberId, long targetId);
    void add(long subscriberId, long targetId);
    void remove(long subscriberId, long targetId);
    int countFollowers(long userId);
    int countFollowing(long userId);
}
