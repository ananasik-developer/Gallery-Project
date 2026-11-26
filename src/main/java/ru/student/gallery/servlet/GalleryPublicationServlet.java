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
import ru.student.gallery.service.GalleryService;

import java.io.IOException;

@WebServlet("/gallery/publication")
public class GalleryPublicationServlet extends HttpServlet {

    private GalleryService galleryService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        galleryService = registry.getService("galleryService", GalleryService.class);
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

        long galleryId;
        try {
            galleryId = Long.parseLong(req.getParameter("galleryId"));
        } catch (NumberFormatException e) {
            resp.sendError(404, "Некорректный ID галереи");
            return;
        }

        boolean featured = Boolean.parseBoolean(req.getParameter("featured"));
        boolean available = Boolean.parseBoolean(req.getParameter("available"));

        try {
            galleryService.setPublication(galleryId, featured, available, currentUser);
        } catch (NotFoundException e) {
            resp.sendError(404, e.getMessage());
            return;
        } catch (AccessDeniedException e) {
            resp.sendError(403, e.getMessage());
            return;
        }

        String back = req.getParameter("back");
        if ("profile".equals(back)) {
            resp.sendRedirect(req.getContextPath() + "/profile");
        } else {
            resp.sendRedirect(req.getContextPath() + "/gallery/" + galleryId);
        }
    }
}
