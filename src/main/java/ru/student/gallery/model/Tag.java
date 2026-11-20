package ru.student.gallery.model;

public class Tag {
    private Long id;
    private String name;
    private String category;
    private Integer usageCount;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getUsageCount() {
        return usageCount;
    }
    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }
}
