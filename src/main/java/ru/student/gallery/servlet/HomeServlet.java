package ru.student.gallery.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.student.gallery.config.ServiceRegistry;
import ru.student.gallery.model.Artwork;
import ru.student.gallery.model.Tag;
import ru.student.gallery.service.ArtworkService;
import ru.student.gallery.service.GalleryService;
import ru.student.gallery.service.TagService;
import ru.student.gallery.util.Pager;

import java.io.IOException;
import java.util.List;

@WebServlet("")
public class HomeServlet extends HttpServlet {

    private static final int PAGE_SIZE = 24;
    private static final int TAG_LIMIT = 60;

    private ArtworkService artworkService;
    private GalleryService galleryService;
    private TagService tagService;

    @Override
    public void init() throws ServletException {
        var registry = (ServiceRegistry) getServletContext().getAttribute("serviceRegistry");
        artworkService = registry.getService("artworkService", ArtworkService.class);
        galleryService = registry.getService("galleryService", GalleryService.class);
        tagService = registry.getService("tagService", TagService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String query = req.getParameter("q");
        Long tagId = parseLong(req.getParameter("tag"));

        List<Tag> allTags = tagService.findAll();
        req.setAttribute("tagGroups", tagService.groupByCategory(allTags));

        List<Artwork> feed;
        int page = 1;
        int pages = 1;

        if (query != null && !query.trim().isEmpty()) {
            query = query.trim();
            feed = artworkService.search(query);
            req.setAttribute("searchQuery", query);
        } else if (tagId != null) {
            feed = artworkService.getByTag(tagId, TAG_LIMIT);
            req.setAttribute("activeTagId", tagId);
            for (Tag tag : allTags) {
                if (tag.getId().equals(tagId)) {
                    req.setAttribute("activeTag", tag);
                    break;
                }
            }
        } else {
            pages = Math.max(1, (artworkService.countPublished() + PAGE_SIZE - 1) / PAGE_SIZE);
            page = parsePage(req.getParameter("page"), pages);
            feed = artworkService.getFeed(PAGE_SIZE, (page - 1) * PAGE_SIZE);
        }

        req.setAttribute("feed", feed);
        req.setAttribute("page", page);
        req.setAttribute("pages", pages);
        req.setAttribute("pageList", Pager.build(page, pages));
        req.setAttribute("featuredGalleries", galleryService.getFeaturedGalleries());
        req.setAttribute("popularGalleries", galleryService.getPopularGalleries(5));

        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }

    private Long parseLong(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private int parsePage(String value, int pages) {
        if (value == null) {
            return 1;
        }
        try {
            int page = Integer.parseInt(value);
            if (page < 1) {
                return 1;
            }
            return Math.min(page, pages);
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
