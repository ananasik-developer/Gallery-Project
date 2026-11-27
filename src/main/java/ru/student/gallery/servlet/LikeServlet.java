package ru.student.gallery.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.exception.AccessDeniedException;
import ru.student.gallery.exception.NotFoundException;
import ru.student.gallery.model.Artwork;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.service.ArtworkService;
import ru.student.gallery.service.LikeService;

import java.io.IOException;

@WebServlet("/like")
public class LikeServlet extends HttpServlet {

    private LikeService likeService;
    private ArtworkService artworkService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        likeService = registry.getService("likeService", LikeService.class);
        artworkService = registry.getService("artworkService", ArtworkService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        SessionUser currentUser = session == null
                ? null
                : (SessionUser) session.getAttribute("currentUser");

        resp.setContentType("application/json;charset=UTF-8");

        if (currentUser == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"login\"}");
            return;
        }

        long artworkId;
        try {
            artworkId = Long.parseLong(req.getParameter("artworkId"));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"badId\"}");
            return;
        }

        try {
            Artwork artwork = artworkService.getArtworkById(artworkId, currentUser);
            if (!"PUBLISHED".equals(artwork.getStatus())) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().write("{\"error\":\"draft\"}");
                return;
            }
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"notFound\"}");
            return;
        } catch (AccessDeniedException e) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("{\"error\":\"forbidden\"}");
            return;
        }

        boolean liked = likeService.toggle(currentUser.getId(), artworkId);
        int count = likeService.countByArtwork(artworkId);

        resp.getWriter().write("{\"liked\":" + liked + ",\"count\":" + count + "}");
    }
}
