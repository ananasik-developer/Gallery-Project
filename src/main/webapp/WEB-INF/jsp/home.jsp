<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Галерея</title>
    <style>
        .feed-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .artwork-card {
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 4px;
        }
        .artwork-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            display: block;
        }
        .artwork-card h3 {
            margin: 10px 0 5px;
            font-size: 16px;
        }
        .artwork-card p {
            margin: 0;
            color: #666;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <h1>Добро пожаловать в галерею!</h1>

    <c:choose>
        <c:when test="${not empty currentUser}">
            <p>Вы вошли как: <strong>${currentUser.username}</strong></p>
            <a href="<c:url value='/artwork/create'/>">Создать новую работу</a>
            <a href="<c:url value='/gallery/create'/>">Создать галерею</a>|
            <a href="<c:url value='/logout'/>">Выйти</a>
        </c:when>
        <c:otherwise>
            <p>Вы не авторизованы.</p>
            <a href="<c:url value='/login'/>">Войти</a> |
            <a href="<c:url value='/register'/>">Регистрация</a>
        </c:otherwise>
    </c:choose>

    <h2>Последние публикации</h2>

    <c:if test="${empty feed}">
        <p>Пока нет опубликованных работ.</p>
    </c:if>

    <div class="feed-grid">
        <c:forEach var="art" items="${feed}">
            <div class="artwork-card">
                <a href="<c:url value='/artwork/${art.id}'/>">
                    <img src="<c:url value='${art.imagePath}'/>" alt="${art.title}">
                    <h3>${art.title}</h3>
                    <p>Автор: ${art.authorUsername}</p>
                </a>
            </div>
        </c:forEach>
    </div>
</body>
</html>