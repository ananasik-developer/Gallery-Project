<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Новая галерея</title>
</head>
<body>
    <h2>Создание галереи</h2>

    <c:if test="${not empty error}">
        <p style="color: red;"><c:out value="${error}"/></p>
    </c:if>

    <form method="post" action="<c:url value='/gallery/create'/>">
        <label>Название:<br>
            <input type="text" name="title"
                   value="<c:out value='${title}'/>" required maxlength="500">
        </label>
        <br><br>
        <label>Описание:<br>
            <textarea name="description" rows="4" cols="50"><c:out value="${description}"/></textarea>
        </label>
        <br><br>

        <button type="submit">Создать галерею</button>
        <a href="<c:url value='/'/>">Отмена</a>
    </form>
</body>
</html>