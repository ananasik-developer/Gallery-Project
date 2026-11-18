<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${gallery.title}</title>
    <style>
        body { font-family: sans-serif; max-width: 1200px; margin: 0 auto; padding: 20px; }
        .gallery-header { margin-bottom: 30px; border-bottom: 1px solid #eee; padding-bottom: 20px; }
        .gallery-meta { color: #666; font-size: 14px; margin-top: 5px; }
        .artworks-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 15px;
        }
        .artwork-thumb img {
            width: 100%; height: 150px; object-fit: cover;
            border-radius: 4px; display: block;
        }
        .artwork-thumb { text-decoration: none; color: inherit; }
        .artwork-thumb p { margin: 5px 0 0; font-size: 13px; text-align: center; }
        .empty-gallery { color: #666; font-style: italic; padding: 40px; text-align: center; border: 2px dashed #ddd; border-radius: 8px; }
    </style>
</head>
<body>
    <div class="gallery-header">
        <h1>${gallery.title}</h1>
        <p>${gallery.description}</p>
        <div class="gallery-meta">
            Автор: ${gallery.authorUsername} |
            Работ: ${artworks.size()}
        </div>
        <c:if test="${isOwner}">
            <br>
            <c:if test="${not empty gallery.id}">
                <a href="<c:url value='/gallery-manage/add-artwork?galleryId=${gallery.id}'/>">Добавить работу</a>
            </c:if>
        </c:if>
    </div>

    <c:if test="${empty artworks}">
        <div class="empty-gallery">
            В этой галерее пока нет работ.
            <c:if test="${isOwner}">
                <br><a href="<c:url value='/gallery-manage/add-artwork?galleryId=${gallery.id}'/>">Добавить первую работу</a>
            </c:if>
        </div>
    </c:if>

    <div class="artworks-grid">
        <c:forEach var="art" items="${artworks}">
            <a href="<c:url value='/artwork/${art.id}'/>" class="artwork-thumb">
                <img src="<c:url value='${art.imagePath}'/>" alt="${art.title}" loading="lazy">
                <p>${art.title}</p>
            </a>
        </c:forEach>
    </div>

    <br>
    <a href="<c:url value='/'/>">На главную</a>
</body>
</html>