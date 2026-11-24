package ru.student.gallery.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.student.gallery.model.SessionUser;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/artwork/create",
        "/artwork/edit/*",
        "/artwork/delete/*",
        "/artwork/publish",
        "/gallery/publication",
        "/like",
        "/gallery/create",
        "/gallery/edit/*",
        "/gallery/delete/*",
        "/gallery-manage/add-artwork",
        "/profile/edit",
        "/logout"
})
public class AuthFilter implements Filter {

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
        chain.doFilter(req, resp);
    }
}
