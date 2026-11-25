package ru.student.gallery.service;

import ru.student.gallery.dao.TagDao;
import ru.student.gallery.model.Tag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TagService {

    private static final int MAX_TAGS = 10;

    private final TagDao tagDao;

    public TagService(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    public List<Tag> findByArtwork(long artworkId) {
        return tagDao.findByArtwork(artworkId);
    }

    public List<Long> findIdsByArtwork(long artworkId) {
        List<Long> ids = new ArrayList<>();
        for (Tag tag : tagDao.findByArtwork(artworkId)) {
            ids.add(tag.getId());
        }
        return ids;
    }

    public Map<String, List<Tag>> groupByCategory(List<Tag> tags) {
        Map<String, List<Tag>> groups = new LinkedHashMap<>();
        groups.put("GENERAL", new ArrayList<>());
        groups.put("META", new ArrayList<>());
        groups.put("CHARACTER", new ArrayList<>());

        for (Tag tag : tags) {
            groups.computeIfAbsent(tag.getCategory(), key -> new ArrayList<>()).add(tag);
        }
        return groups;
    }

    public List<Long> parseIds(String[] tagIds) {
        List<Long> ids = new ArrayList<>();

        if (tagIds == null) {
            return ids;
        }

        for (String raw : tagIds) {
            try {
                long id = Long.parseLong(raw);
                if (!ids.contains(id)) {
                    ids.add(id);
                }
            } catch (NumberFormatException e) {
                continue;
            }
        }
        return ids;
    }

    public void save(long artworkId, String[] tagIds) {
        List<Long> ids = parseIds(tagIds);

        if (ids.size() > MAX_TAGS) {
            throw new IllegalArgumentException("Можно выбрать не больше десяти тегов");
        }

        List<Long> known = new ArrayList<>();
        for (Tag tag : tagDao.findAll()) {
            known.add(tag.getId());
        }
        ids.retainAll(known);

        tagDao.setArtworkTags(artworkId, ids);
    }
}
