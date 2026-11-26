package ru.student.gallery.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.service.SubscriptionService;

import java.io.IOException;

@WebServlet("/subscribe")
public class SubscriptionServlet extends HttpServlet {

    private SubscriptionService subscriptionService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        subscriptionService = registry.getService("subscriptionService", SubscriptionService.class);
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

        long userId;
        try {
            userId = Long.parseLong(req.getParameter("userId"));
        } catch (NumberFormatException e) {
            resp.sendError(404, "Некорректный ID пользователя");
            return;
        }

        try {
            subscriptionService.toggle(currentUser.getId(), userId);
        } catch (RuntimeException e) {
            resp.sendError(403, e.getMessage());
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/profile/" + userId);
    }
}
