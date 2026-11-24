package ru.student.gallery.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.model.Artwork;
import ru.student.gallery.model.SessionUser;
import ru.student.gallery.service.ArtworkService;
import ru.student.gallery.util.Tokens;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

@WebServlet("/artwork/create")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class ArtworkCreateServlet extends HttpServlet {

    private ArtworkService artworkService;
    private String uploadDir;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        artworkService = registry.getService("artworkService", ArtworkService.class);

        uploadDir = getServletContext().getRealPath("/uploads/artworks");
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/artwork-create.jsp").forward(req, resp);
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

        if (!Tokens.accept(req, session, true)) {
            resp.sendError(403, "Форма устарела, обновите страницу и попробуйте снова");
            return;
        }

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Part filePart = req.getPart("imageFile");

        try {
            if (filePart == null || filePart.getSize() == 0) {
                throw new IllegalArgumentException("Выберите изображение");
            }

            String fileName = LocalDate.now() + "_" + System.currentTimeMillis() + ".jpg";
            String filePath = Paths.get(uploadDir, fileName).toString();

            filePart.write(filePath);

            Artwork artwork = new Artwork();
            artwork.setTitle(title);
            artwork.setDescription(description);
            artwork.setImagePath("/uploads/artworks/" + fileName);

            artworkService.createArtwork(artwork, currentUser);

            resp.sendRedirect(req.getContextPath() + "/artwork/" + artwork.getId());

        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("title", title);
            req.setAttribute("description", description);
            req.getRequestDispatcher("/WEB-INF/jsp/artwork-create.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка загрузки файла: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/artwork-create.jsp").forward(req, resp);
        }
    }
}