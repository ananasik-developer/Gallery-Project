package ru.student.gallery.model;

public class SessionUser {
    private final Long id;
    private final String username;
    private final Role role;
    private final String avatarPath;

    public SessionUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.avatarPath = user.getAvatarPath();
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }
    public String getAvatarPath() { return avatarPath; }
}
