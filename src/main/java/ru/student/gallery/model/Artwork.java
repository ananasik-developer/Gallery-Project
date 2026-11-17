package ru.student.gallery.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Artwork {
    private Long id;
    private String title;
    private String description;
    private String imagePath;
    private Long authorId;
    private String status;    // DRAFT / PUBLISHED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private transient String authorUsername;
}
