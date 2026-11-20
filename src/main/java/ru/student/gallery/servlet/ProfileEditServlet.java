package ru.student.gallery.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.model.User;
import ru.student.gallery.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/profile/edit")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class ProfileEditServlet extends HttpServlet {

    private UserService userService;
    private String avatarDir;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        userService = registry.getService("userService", UserService.class);

        avatarDir = getServletContext().getRealPath("/uploads/avatars");
        File dir = new File(avatarDir);
        if (!dir.exists()) dir.mkdirs();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        SessionUser currentUser = currentUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("profileUser", userService.findById(currentUser.getId()));
        req.getRequestDispatcher("/WEB-INF/jsp/profile-edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        SessionUser currentUser = currentUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = userService.findById(currentUser.getId());

        String username = req.getParameter("username");
        String bio = req.getParameter("bio");
        String tgLink = req.getParameter("tgLink");
        String avatarPath = user.getAvatarPath();

        try {
            Part avatarPart = req.getPart("avatar");
            if (avatarPart != null && avatarPart.getSize() > 0) {
                String contentType = avatarPart.getContentType();
                if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
                    throw new IllegalArgumentException("Аватар: только JPG или PNG");
                }

                String extension = "image/png".equals(contentType) ? ".png" : ".jpg";
                String fileName = "avatar_" + user.getId() + "_" + System.currentTimeMillis() + extension;
                String filePath = Paths.get(avatarDir, fileName).toString();
                avatarPart.write(filePath);
                avatarPath = "/uploads/avatars/" + fileName;
            }

            User updated = userService.updateProfile(user.getId(), username, bio, tgLink, avatarPath);

            HttpSession session = req.getSession(true);
            session.setAttribute("currentUser", new SessionUser(updated));

            resp.sendRedirect(req.getContextPath() + "/profile");

        } catch (IllegalArgumentException e) {
            User form = new User();
            form.setId(user.getId());
            form.setUsername(username);
            form.setBio(bio);
            form.setTgLink(tgLink);
            form.setAvatarPath(avatarPath);
            form.setRole(user.getRole());
            form.setCreatedAt(user.getCreatedAt());

            req.setAttribute("profileUser", form);
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/profile-edit.jsp").forward(req, resp);
        }
    }

    private SessionUser currentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null ? null : (SessionUser) session.getAttribute("currentUser");
    }
}
