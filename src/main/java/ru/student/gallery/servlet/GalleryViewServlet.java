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
import ru.student.gallery.model.Gallery;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.service.GalleryService;

import java.io.IOException;
import java.util.List;

@WebServlet("/gallery/*")
public class GalleryViewServlet extends HttpServlet {
    private GalleryService galleryService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        galleryService = registry.getService("galleryService", GalleryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        SessionUser currentUser = session != null ?
                (SessionUser) session.getAttribute("currentUser") : null;

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
            Gallery gallery = galleryService.findById(galleryId);
            List<Artwork> artworks = galleryService.getGalleryArtworks(galleryId);
            boolean isOwner = currentUser != null && gallery.getAuthorId().equals(currentUser.getId());

            req.setAttribute("gallery", gallery);
            req.setAttribute("artworks", artworks);
            req.setAttribute("isOwner", isOwner);

            req.getRequestDispatcher("/WEB-INF/jsp/gallery-view.jsp").forward(req, resp);

        } catch (NotFoundException e) {
            resp.sendError(404, e.getMessage());
        } catch (AccessDeniedException e) {
            resp.sendError(403, e.getMessage());
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

}
