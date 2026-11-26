package ru.student.gallery.service;

import ru.student.gallery.dao.SubscriptionDao;

public class SubscriptionService {

    private final SubscriptionDao subscriptionDao;

    public SubscriptionService(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    public boolean isSubscribed(long subscriberId, long targetId) {
        return subscriptionDao.exists(subscriberId, targetId);
    }

    public int countFollowers(long userId) {
        return subscriptionDao.countFollowers(userId);
    }

    public int countFollowing(long userId) {
        return subscriptionDao.countFollowing(userId);
    }

    public boolean toggle(long subscriberId, long targetId) {
        if (subscriberId == targetId) {
            throw new IllegalArgumentException("На себя подписаться нельзя");
        }

        if (subscriptionDao.exists(subscriberId, targetId)) {
            subscriptionDao.remove(subscriberId, targetId);
            return false;
        }

        subscriptionDao.add(subscriberId, targetId);
        return true;
    }
}
