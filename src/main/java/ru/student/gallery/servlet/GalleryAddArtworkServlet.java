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
import ru.student.gallery.service.ArtworkService;
import ru.student.gallery.service.GalleryService;

import java.io.IOException;
import java.util.List;

@WebServlet("/gallery-manage/add-artwork")
public class GalleryAddArtworkServlet extends HttpServlet {

    private GalleryService galleryService;
    private ArtworkService artworkService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        galleryService = registry.getService("galleryService", GalleryService.class);
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

        long galleryId;
        try {
            galleryId = Long.parseLong(req.getParameter("galleryId"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Некорректный ID галереи");
            return;
        }

        Gallery gallery;
        try {
            gallery = galleryService.findById(galleryId);
        } catch (NotFoundException e) {
            resp.sendError(404, e.getMessage());
            return;
        }

        if (!gallery.getAuthorId().equals(currentUser.getId())) {
            resp.sendError(403, "Добавлять работы можно только в свою галерею");
            return;
        }

        showForm(req, resp, galleryId, currentUser);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        SessionUser currentUser = currentUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        long galleryId;
        long artworkId;
        try {
            galleryId = Long.parseLong(req.getParameter("galleryId"));
            artworkId = Long.parseLong(req.getParameter("artworkId"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Некорректные данные формы");
            return;
        }

        try {
            galleryService.addArtwork(galleryId, artworkId, currentUser);
            resp.sendRedirect(req.getContextPath() + "/gallery/" + galleryId);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            showForm(req, resp, galleryId, currentUser);
        } catch (NotFoundException e) {
            resp.sendError(404, e.getMessage());
        } catch (AccessDeniedException e) {
            resp.sendError(403, e.getMessage());
        }
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, long galleryId, SessionUser currentUser)
            throws ServletException, IOException {
        List<Artwork> userArtworks = artworkService.getPublishedByAuthor(currentUser.getId());
        req.setAttribute("galleryId", galleryId);
        req.setAttribute("userArtworks", userArtworks);
        req.getRequestDispatcher("/WEB-INF/jsp/gallery-add-artwork.jsp").forward(req, resp);
    }

    private SessionUser currentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null ? null : (SessionUser) session.getAttribute("currentUser");
    }
}
