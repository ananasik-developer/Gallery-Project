<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${artwork.title}</title>
</head>
<body>
    <h1>${artwork.title}</h1>

    <p><strong>Автор:</strong> ${artwork.authorUsername}</p>
    <p><strong>Статус:</strong>
        <c:choose>
            <c:when test="${artwork.status == 'DRAFT'}">📝 Черновик</c:when>
            <c:otherwise>Опубликовано</c:otherwise>
        </c:choose>
    </p>

    <p><strong>Описание:</strong></p>
    <p>${artwork.description}</p>

    <img src="<c:url value='${artwork.imagePath}'/>"
         alt="${artwork.title}"
         style="max-width: 100%; height: auto; border: 1px solid #ccc;">
    <br><br>
    <c:if test="${isOwner && artwork.status == 'DRAFT'}">
        <form method="post" action="<c:url value='/artwork/publish'/>">
            <input type="hidden" name="artworkId" value="${artwork.id}">
            <button type="submit">Опубликовать работу</button>
        </form>
    </c:if>

    <br>
    <a href="<c:url value='/'/>">← На главную</a>
</body>
</html>