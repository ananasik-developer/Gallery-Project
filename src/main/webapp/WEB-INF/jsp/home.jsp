<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Галерея</title>
</head>
<body>
    <h1>Добро пожаловать в галерею!</h1>

    <c:choose>
        <c:when test="${not empty currentUser}">
            <p>Вы вошли как: <strong>${currentUser.username}</strong></p>
            <p>Роль: ${currentUser.role}</p>

            <br>
            <a href="<c:url value='/artwork/create'/>">Создать новую работу</a> |
            <a href="<c:url value='/logout'/>">Выйти</a>
        </c:when>
        <c:otherwise>
            <p>Вы не авторизованы.</p>
            <a href="<c:url value='/login'/>">Войти</a> |
            <a href="<c:url value='/register'/>">Регистрация</a>
        </c:otherwise>
    </c:choose>
</body>
</html>