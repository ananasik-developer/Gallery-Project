package ru.student.gallery.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.model.Gallery;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.service.GalleryService;

import java.io.IOException;

@WebServlet("/gallery/create")
public class GalleryCreateServlet extends HttpServlet {

    private GalleryService galleryService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        galleryService = registry.getService("galleryService", GalleryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/gallery-create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        SessionUser currentUser = (SessionUser) session.getAttribute("currentUser");

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String title = req.getParameter("title");
        String description = req.getParameter("description");

        try {
            Gallery gallery = new Gallery();
            gallery.setTitle(title);
            gallery.setDescription(description);

            galleryService.createGallery(gallery, currentUser);

            resp.sendRedirect(req.getContextPath() + "/gallery/" + gallery.getId());

        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("title", title);
            req.setAttribute("description", description);
            req.getRequestDispatcher("/WEB-INF/jsp/gallery-create.jsp").forward(req, resp);
        }
    }
}
