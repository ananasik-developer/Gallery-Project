package ru.student.gallery.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.exception.NotFoundException;
import ru.student.gallery.model.Artwork;
import ru.student.gallery.model.Gallery;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.model.User;
import ru.student.gallery.service.ArtworkService;
import ru.student.gallery.service.GalleryService;
import ru.student.gallery.service.SubscriptionService;
import ru.student.gallery.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/profile/*")
public class ProfileServlet extends HttpServlet {

    private UserService userService;
    private ArtworkService artworkService;
    private GalleryService galleryService;
    private SubscriptionService subscriptionService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        userService = registry.getService("userService", UserService.class);
        artworkService = registry.getService("artworkService", ArtworkService.class);
        galleryService = registry.getService("galleryService", GalleryService.class);
        subscriptionService = registry.getService("subscriptionService", SubscriptionService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        SessionUser currentUser = session == null
                ? null
                : (SessionUser) session.getAttribute("currentUser");

        String pathInfo = req.getPathInfo();
        long userId;

        if (pathInfo == null || pathInfo.length() <= 1 || "/".equals(pathInfo)) {
            if (currentUser == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            userId = currentUser.getId();
        } else {
            try {
                userId = Long.parseLong(pathInfo.substring(1));
            } catch (NumberFormatException e) {
                resp.sendError(404, "Некорректный ID пользователя");
                return;
            }
        }

        User profileUser;
        try {
            profileUser = userService.findById(userId);
        } catch (NotFoundException e) {
            resp.sendError(404, "Пользователь не найден");
            return;
        }

        boolean isOwner = currentUser != null && profileUser.getId().equals(currentUser.getId());

        List<Artwork> published = artworkService.getPublishedByAuthor(userId);
        List<Artwork> drafts = isOwner ? artworkService.getDraftsByAuthor(userId) : List.of();
        List<Gallery> galleries = galleryService.getByAuthorId(userId);

        req.setAttribute("profileUser", profileUser);
        req.setAttribute("isOwner", isOwner);
        req.setAttribute("published", published);
        req.setAttribute("drafts", drafts);
        req.setAttribute("galleries", galleries);
        req.setAttribute("followers", subscriptionService.countFollowers(userId));
        req.setAttribute("following", subscriptionService.countFollowing(userId));
        req.setAttribute("subscribed", currentUser != null && !isOwner
                && subscriptionService.isSubscribed(currentUser.getId(), userId));

        req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
    }
}
