package ru.student.gallery.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.dao.ArtworkDao;
import ru.student.gallery.dao.GalleryDao;
import ru.student.gallery.dao.LikeDao;
import ru.student.gallery.dao.TagDao;
import ru.student.gallery.dao.UserDao;
import ru.student.gallery.dao.impl.JdbcArtworkDao;
import ru.student.gallery.dao.impl.JdbcGalleryDao;
import ru.student.gallery.dao.impl.JdbcLikeDao;
import ru.student.gallery.dao.impl.JdbcTagDao;
import ru.student.gallery.dao.impl.JdbcUserDao;
import ru.student.gallery.service.ArtworkService;
import ru.student.gallery.service.GalleryService;
import ru.student.gallery.service.LikeService;
import ru.student.gallery.service.TagService;
import ru.student.gallery.service.UserService;

import java.util.logging.Logger;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDao userDao = new JdbcUserDao();
        UserService userService = new UserService(userDao);

        ArtworkDao artworkDao = new JdbcArtworkDao();
        ArtworkService artworkService = new ArtworkService(artworkDao);

        GalleryDao galleryDao = new JdbcGalleryDao();
        GalleryService galleryService = new GalleryService(galleryDao, artworkDao);

        LikeDao likeDao = new JdbcLikeDao();
        LikeService likeService = new LikeService(likeDao);

        TagDao tagDao = new JdbcTagDao();
        TagService tagService = new TagService(tagDao);

        ServiceRegistry registry = new ServiceRegistry();
        registry.register("userService", userService);
        registry.register("artworkService", artworkService);
        registry.register("galleryService", galleryService);
        registry.register("likeService", likeService);
        registry.register("tagService", tagService);

        ServletContext ctx = sce.getServletContext();
        ctx.setAttribute("serviceRegistry", registry);

        LOG.info("Приложение запущено, сервисы собраны: " + ctx.getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("Приложение остановлено");
    }
}
