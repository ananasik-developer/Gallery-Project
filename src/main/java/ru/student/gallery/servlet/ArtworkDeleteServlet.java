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
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.service.ArtworkService;

import java.io.IOException;

@WebServlet("/artwork/delete/*")
public class ArtworkDeleteServlet extends HttpServlet {

    private ArtworkService artworkService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        artworkService = registry.getService("artworkService", ArtworkService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        SessionUser currentUser = session == null
                ? null
                : (SessionUser) session.getAttribute("currentUser");

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

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
            artworkService.deleteArtwork(artworkId, currentUser);
            resp.sendRedirect(req.getContextPath() + "/profile");
        } catch (NotFoundException e) {
            resp.sendError(404, e.getMessage());
        } catch (AccessDeniedException e) {
            resp.sendError(403, e.getMessage());
        }
    }
}
