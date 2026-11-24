package ru.student.gallery.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorLogFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(ErrorLogFilter.class.getName());

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        try {
            chain.doFilter(req, resp);
        } catch (ServletException | IOException | RuntimeException e) {
            LOG.log(Level.SEVERE,
                    "Ошибка при обработке " + request.getMethod() + " " + request.getRequestURI(), e);
            throw e;
        }
    }
}
