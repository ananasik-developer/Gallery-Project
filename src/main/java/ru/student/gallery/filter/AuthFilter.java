package ru.student.gallery.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.service.UserService;

import java.io.IOException;

public class AuthFilter implements Filter {

    private UserService userService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        var registry = (ServiceRegistry) config.getServletContext().getAttribute("serviceRegistry");
        if (registry == null) {
            throw new ServletException("ServiceRegistry не найден! Проверь AppContextListener.");
        }
        userService = registry.getService("userService", UserService.class);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(false);
        SessionUser user = session == null ? null : (SessionUser) session.getAttribute("currentUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!userService.isActive(user.getId())) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login?blocked=1");
            return;
        }

        chain.doFilter(req, resp);
    }
}
