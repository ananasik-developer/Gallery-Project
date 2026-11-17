package ru.student.gallery.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Gallery {
    private Long id;
    private String title;
    private String description;
    private Long coverArtworkId;
    private Long authorId;
    private LocalDateTime createdAt;

    private transient List<Artwork> artworks; // Внутри галереи
    private transient String authorUsername;
}
