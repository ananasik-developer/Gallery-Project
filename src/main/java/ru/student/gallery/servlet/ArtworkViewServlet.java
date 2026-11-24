package ru.student.gallery.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.model.Artwork;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.service.ArtworkService;
import ru.student.gallery.service.LikeService;

import java.io.IOException;

@WebServlet("/artwork/*")
public class ArtworkViewServlet extends HttpServlet {
    private ArtworkService artworkService;
    private LikeService likeService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        artworkService = registry.getService("artworkService", ArtworkService.class);
        likeService = registry.getService("likeService", LikeService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        SessionUser currentUser = session == null
                ? null
                : (SessionUser) session.getAttribute("currentUser");

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(404, "ID работы не указан");
            return;
        }

        long artworkId;
        try {
            artworkId = Long.parseLong(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            resp.sendError(404, "Некорректный ID работы");
            return;
        }

        try {
            Artwork art = artworkService.getArtworkById(artworkId, currentUser);
            req.setAttribute("artwork", art);
            req.setAttribute("isOwner", currentUser != null && art.getAuthorId().equals(currentUser.getId()));
            req.setAttribute("likeCount", likeService.countByArtwork(artworkId));
            req.setAttribute("liked", currentUser != null
                    && likeService.isLikedBy(currentUser.getId(), artworkId));
            req.getRequestDispatcher("/WEB-INF/jsp/artwork-view.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            resp.sendError(404, e.getMessage());
        } catch (RuntimeException e) {
            resp.sendError(403, e.getMessage());
        }
    }
}
