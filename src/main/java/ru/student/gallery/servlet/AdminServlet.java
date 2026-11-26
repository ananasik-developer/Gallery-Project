package ru.student.gallery.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.model.Role;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.service.UserService;

import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        userService = registry.getService("userService", UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        SessionUser currentUser = currentUser(req);
        if (!isAdmin(currentUser)) {
            resp.sendError(403, "Раздел доступен только администратору");
            return;
        }

        req.setAttribute("users", userService.findAll());
        req.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        SessionUser currentUser = currentUser(req);
        if (!isAdmin(currentUser)) {
            resp.sendError(403, "Раздел доступен только администратору");
            return;
        }

        long userId;
        try {
            userId = Long.parseLong(req.getParameter("userId"));
        } catch (NumberFormatException e) {
            resp.sendError(404, "Некорректный ID пользователя");
            return;
        }

        boolean active = Boolean.parseBoolean(req.getParameter("active"));

        try {
            userService.setActive(userId, active, currentUser);
        } catch (IllegalArgumentException e) {
            resp.sendError(404, e.getMessage());
            return;
        } catch (RuntimeException e) {
            resp.sendError(403, e.getMessage());
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin");
    }

    private SessionUser currentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null ? null : (SessionUser) session.getAttribute("currentUser");
    }

    private boolean isAdmin(SessionUser user) {
        return user != null && user.getRole() == Role.ADMIN;
    }
}
