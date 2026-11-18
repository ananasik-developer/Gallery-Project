package ru.student.gallery.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.dao.ArtworkDao;
import ru.student.gallery.dao.GalleryDao;
import ru.student.gallery.dao.UserDao;
import ru.student.gallery.dao.impl.JdbcArtworkDao;
import ru.student.gallery.dao.impl.JdbcGalleryDao;
import ru.student.gallery.dao.impl.JdbcUserDao;
import ru.student.gallery.service.ArtworkService;
import ru.student.gallery.service.GalleryService;
import ru.student.gallery.service.UserService;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDao userDao = new JdbcUserDao();
        UserService userService = new UserService(userDao);

        ArtworkDao artworkDao = new JdbcArtworkDao();
        ArtworkService artworkService = new ArtworkService(artworkDao);

        GalleryDao galleryDao = new JdbcGalleryDao();
        GalleryService galleryService = new GalleryService(galleryDao);

        ServiceRegistry registry = new ServiceRegistry();
        registry.register("userService", userService);
        registry.register("artworkService", artworkService);
        registry.register("galleryService", galleryService);

        ServletContext ctx = sce.getServletContext();
        ctx.setAttribute("serviceRegistry", registry);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
