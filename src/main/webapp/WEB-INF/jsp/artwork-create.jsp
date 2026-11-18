<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Создание работы</title>
</head>
<body>
    <h2>Новая работа</h2>

    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <form method="post" action="<c:url value='/artwork/create'/>"
          enctype="multipart/form-data">
        <label>Название:<br>
            <input type="text" name="title"
                   value="${title}" required maxlength="500">
        </label>
        <br><br>
        <label>Описание:<br>
            <textarea name="description" rows="4" cols="50">${description}</textarea>
        </label>
        <br><br>
        <label>Изображение (JPG/PNG, макс 10MB):<br>
            <input type="file" name="imageFile" accept="image/jpeg,image/png" required>
        </label>
        <br><br>

        <button type="submit">Сохранить черновик</button>
        <a href="<c:url value='/'/>">Отмена</a>
    </form>
</body>
</html>