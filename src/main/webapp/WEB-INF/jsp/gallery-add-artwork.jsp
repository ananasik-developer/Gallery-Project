<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Добавить работу в галерею</title>
</head>
<body>
    <h2>Добавить работу в галерею</h2>

    <c:if test="${not empty error}">
        <p style="color: red;"><c:out value="${error}"/></p>
    </c:if>

    <c:if test="${empty userArtworks}">
        <p>У вас нет опубликованных работ. Сначала создайте и опубликуйте работу.</p>
        <a href="<c:url value='/artwork/create'/>">Создать работу</a>
    </c:if>

    <c:if test="${not empty userArtworks}">
        <form method="post" action="<c:url value='/gallery-manage/add-artwork'/>">
            <input type="hidden" name="galleryId" value="${galleryId}">

            <label>Выберите работу:<br>
                <select name="artworkId" required style="width: 100%; padding: 8px; margin-top: 5px;">
                    <option value="">Выберите</option>
                    <c:forEach var="art" items="${userArtworks}">
                        <option value="${art.id}"><c:out value="${art.title}"/></option>
                    </c:forEach>
                </select>
            </label>

            <br><br>
            <button type="submit">Добавить в галерею</button>
            <a href="<c:url value='/gallery/${galleryId}'/>">Отмена</a>
        </form>
    </c:if>
</body>
</html>