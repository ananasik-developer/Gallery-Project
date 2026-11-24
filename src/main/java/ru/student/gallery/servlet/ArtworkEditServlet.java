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

import java.io.IOException;

@WebServlet("/artwork/edit/*")
public class ArtworkEditServlet extends HttpServlet {

    private ArtworkService artworkService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        artworkService = registry.getService("artworkService", ArtworkService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        SessionUser currentUser = currentUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Long artworkId = parseId(req);

        if (artworkId == null) {
            resp.sendError(404, "Некорректный ID работы");
            return;
        }

        try {
            Artwork art = artworkService.getArtworkById(artworkId, currentUser);
            if (!art.getAuthorId().equals(currentUser.getId())) {
                resp.sendError(403, "Редактировать можно только свои работы");
                return;
            }
            req.setAttribute("artwork", art);
            req.getRequestDispatcher("/WEB-INF/jsp/artwork-edit.jsp").forward(req, resp);
        } catch (RuntimeException e) {
            resp.sendError(403, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        SessionUser currentUser = currentUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Long artworkId = parseId(req);

        if (artworkId == null) {
            resp.sendError(404, "Некорректный ID работы");
            return;
        }

        String title = req.getParameter("title");
        String description = req.getParameter("description");

        try {
            Artwork art = artworkService.updateArtwork(artworkId, title, description, currentUser);
            resp.sendRedirect(req.getContextPath() + "/artwork/" + art.getId());
        } catch (IllegalArgumentException e) {
            Artwork art = artworkService.getArtworkById(artworkId, currentUser);
            art.setTitle(title);
            art.setDescription(description);

            req.setAttribute("artwork", art);
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/artwork-edit.jsp").forward(req, resp);
        } catch (RuntimeException e) {
            resp.sendError(403, e.getMessage());
        }
    }

    private SessionUser currentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null ? null : (SessionUser) session.getAttribute("currentUser");
    }

    private Long parseId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            return null;
        }
        try {
            return Long.parseLong(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
