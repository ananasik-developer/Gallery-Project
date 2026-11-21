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

@WebServlet("")
public class HomeServlet extends HttpServlet {
    private ArtworkService artworkService;
    private GalleryService galleryService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        artworkService = registry.getService("artworkService", ArtworkService.class);
        galleryService = registry.getService("galleryService", GalleryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        SessionUser currentUser = session == null
                ? null
                : (SessionUser) session.getAttribute("currentUser");

        String query = req.getParameter("q");
        List<Artwork> feed;

        if (query != null && !query.trim().isEmpty()) {
            query = query.trim();
            feed = artworkService.search(query);
            req.setAttribute("searchQuery", query);
        } else {
            feed = artworkService.getFeed(currentUser, 20);
        }

        req.setAttribute("feed", feed);
        req.setAttribute("featuredGalleries", galleryService.getFeaturedGalleries());
        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }
}
