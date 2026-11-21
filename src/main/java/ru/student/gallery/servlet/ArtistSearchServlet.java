package ru.student.gallery.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.service.GalleryService;

import java.io.IOException;

@WebServlet("/artists")
public class ArtistSearchServlet extends HttpServlet {

    private GalleryService galleryService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        galleryService = registry.getService("galleryService", GalleryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("galleries", galleryService.getCommissionGalleries());
        req.getRequestDispatcher("/WEB-INF/jsp/artists.jsp").forward(req, resp);
    }
}
