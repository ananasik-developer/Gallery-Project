package ru.student.gallery.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.student.gallery.util.Tokens;

import java.io.IOException;

public class FormSecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();
        String context = request.getContextPath();

        if (uri.startsWith(context + "/css/")
                || uri.startsWith(context + "/js/")
                || uri.startsWith(context + "/uploads/")) {
            chain.doFilter(req, resp);
            return;
        }

        HttpSession session = request.getSession();

        request.setAttribute(Tokens.CSRF, Tokens.getOrCreate(session, Tokens.CSRF));
        request.setAttribute(Tokens.FORM, Tokens.getOrCreate(session, Tokens.FORM));

        if ("POST".equalsIgnoreCase(request.getMethod()) && !isMultipart(request)) {
            boolean repeatable = "/like".equals(request.getServletPath());

            if (!Tokens.accept(request, session, !repeatable)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "Форма устарела, обновите страницу и попробуйте снова");
                return;
            }
        }

        chain.doFilter(req, resp);
    }

    private boolean isMultipart(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("multipart/");
    }
}
