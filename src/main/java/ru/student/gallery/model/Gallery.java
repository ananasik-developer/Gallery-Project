package ru.student.gallery.model;

import java.time.LocalDateTime;
import java.util.List;

public class Gallery {
    private Long id;
    private String title;
    private String description;
    private Long coverArtworkId;
    private Long authorId;
    private LocalDateTime createdAt;
    private List<Artwork> artworks;
    private String authorUsername;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCoverArtworkId() {
        return coverArtworkId;
    }
    public void setCoverArtworkId(Long coverArtworkId) {
        this.coverArtworkId = coverArtworkId;
    }

    public Long getAuthorId() {
        return authorId;
    }
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }
    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }
    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
