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
import java.util.List;

@WebServlet("/")
public class HomeServlet extends HttpServlet {
    private ArtworkService artworkService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        artworkService = registry.getService("artworkService", ArtworkService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        SessionUser currentUser = (SessionUser) session.getAttribute("currentUser");

        List<Artwork> feed = artworkService.getFeed(currentUser, 20);
        req.setAttribute("feed", feed);
        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }
}
