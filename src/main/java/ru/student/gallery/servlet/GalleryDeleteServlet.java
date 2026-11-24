package ru.student.gallery.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.service.GalleryService;

import java.io.IOException;

@WebServlet("/gallery/delete/*")
public class GalleryDeleteServlet extends HttpServlet {

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

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(404, "ID галереи не указан");
            return;
        }

        long galleryId;
        try {
            galleryId = Long.parseLong(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            resp.sendError(404, "Некорректный ID галереи");
            return;
        }

        try {
            galleryService.deleteGallery(galleryId, currentUser);
            resp.sendRedirect(req.getContextPath() + "/profile");
        } catch (RuntimeException e) {
            resp.sendError(403, e.getMessage());
        }
    }
}
