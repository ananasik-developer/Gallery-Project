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
        HttpSession session = req.getSession(false);
        SessionUser currentUser = session != null ?
                (SessionUser) session.getAttribute("currentUser") : null;

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String galleryIdParam = req.getParameter("galleryId");

        long galleryId;
        try {
            galleryId = Long.parseLong(galleryIdParam);
        } catch (NumberFormatException | NullPointerException e) {
            resp.sendError(404, "Некорректный ID галереи");
            return;
        }

        try {
            galleryService.findById(galleryId);
        } catch (RuntimeException e) {
            resp.sendError(403, "Нет доступа к этой галерее");
            return;
        }

        List<Artwork> userArtworks = artworkService.getUserGallery(currentUser.getId())
                .stream()
                .filter(a -> "PUBLISHED".equals(a.getStatus()))
                .toList();

        req.setAttribute("galleryId", galleryId);
        req.setAttribute("userArtworks", userArtworks);

        req.getRequestDispatcher("/WEB-INF/jsp/gallery-add-artwork.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        SessionUser currentUser = session != null ?
                (SessionUser) session.getAttribute("currentUser") : null;

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        long galleryId = Long.parseLong(req.getParameter("galleryId"));
        long artworkId = Long.parseLong(req.getParameter("artworkId"));

        try {
            galleryService.addArtwork(galleryId, artworkId, currentUser);
            resp.sendRedirect(req.getContextPath() + "/gallery/" + galleryId);
        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            List<Artwork> userArtworks = artworkService.getUserGallery(currentUser.getId())
                    .stream()
                    .filter(a -> "PUBLISHED".equals(a.getStatus()))
                    .toList();
            req.setAttribute("galleryId", galleryId);
            req.setAttribute("userArtworks", userArtworks);
            req.getRequestDispatcher("/WEB-INF/jsp/gallery-add-artwork.jsp").forward(req, resp);
        }
    }
}
