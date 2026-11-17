package ru.student.gallery.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private Long id;
    private String name;
    private String category; // GENERAL / META / CHARACTER

    private transient Integer usageCount; // Для визуализации популярности
}
