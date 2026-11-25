package ru.student.gallery.service;

import ru.student.gallery.dao.TagDao;
import ru.student.gallery.model.Tag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TagService {

    private static final int MAX_TAGS = 10;
    private static final int MAX_NAME_LENGTH = 50;

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

    public List<String> parseNames(String raw) {
        List<String> names = new ArrayList<>();

        if (raw == null) {
            return names;
        }

        for (String part : raw.split(",")) {
            String name = part.trim().replaceAll("\\s+", " ");
            if (name.isEmpty()) {
                continue;
            }
            if (name.length() > MAX_NAME_LENGTH) {
                throw new IllegalArgumentException("Название тега длиннее пятидесяти символов");
            }
            if (!name.matches("[\\p{L}\\p{N} \\-]+")) {
                throw new IllegalArgumentException("В теге допустимы буквы, цифры, пробел и дефис");
            }

            boolean duplicate = false;
            for (String existing : names) {
                if (existing.equalsIgnoreCase(name)) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                names.add(name);
            }
        }
        return names;
    }

    public void save(long artworkId, String[] tagIds, String customTags) {
        List<Long> ids = parseIds(tagIds);
        List<String> names = parseNames(customTags);

        if (ids.size() + names.size() > MAX_TAGS) {
            throw new IllegalArgumentException("Можно выбрать не больше десяти тегов");
        }

        for (String name : names) {
            ids.add(findOrCreate(name));
        }

        List<Long> unique = new ArrayList<>();
        for (Long id : ids) {
            if (!unique.contains(id)) {
                unique.add(id);
            }
        }

        List<Long> known = new ArrayList<>();
        for (Tag tag : tagDao.findAll()) {
            known.add(tag.getId());
        }
        unique.retainAll(known);

        tagDao.setArtworkTags(artworkId, unique);
    }

    private long findOrCreate(String name) {
        Optional<Tag> existing = tagDao.findByName(name);
        if (existing.isPresent()) {
            return existing.get().getId();
        }

        Tag tag = new Tag();
        tag.setName(name);
        tag.setCategory("GENERAL");
        tagDao.save(tag);
        return tag.getId();
    }
}
