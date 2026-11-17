package ru.student.gallery.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.model.User;
import ru.student.gallery.service.UserService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        var registry = (ru.student.gallery.config.ServiceRegistry)
                getServletContext().getAttribute("serviceRegistry");

        if (registry == null) {
            throw new ServletException("ServiceRegistry не найден! Проверь AppContextListener.");
        }

        this.userService = registry.getService("userService", UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Optional<User> user = userService.authenticate(username, password);
//        if (user.isEmpty()) {
//            req.setAttribute("error", "Неверный логин или пароль");
//            req.setAttribute("username", username);
//            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
//            return;
//        }

        if (user.isEmpty()) {
            resp.setContentType("text/plain;charset=UTF-8");
            resp.setStatus(401);
            resp.getWriter().write("Неверный логин или пароль");
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("currentUser", new SessionUser(user.get()));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
