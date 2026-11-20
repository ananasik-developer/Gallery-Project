<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${pageTitle}"/></title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<header class="site-header">
    <a class="logo" href="<c:url value='/'/>"></a>
    <nav class="main-nav">
        <a href="<c:url value='/'/>">Главная</a>
        <a href="<c:url value='/about'/>">О нас</a>
    </nav>
    <div class="user-box">
        <c:choose>
            <c:when test="${not empty currentUser}">
                <a class="action-link" href="<c:url value='/artwork/create'/>">Новая работа</a>
                <a class="action-link" href="<c:url value='/gallery/create'/>">Новая галерея</a>
                <span class="nickname"><c:out value="${currentUser.username}"/></span>
                <span class="user-id">id: ${currentUser.id}</span>
                <span class="avatar"></span>
                <a class="auth-link" href="<c:url value='/logout'/>">Выйти</a>
            </c:when>
            <c:otherwise>
                <a class="auth-link" href="<c:url value='/login'/>">Войти</a>
                <a class="auth-link" href="<c:url value='/register'/>">Регистрация</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>
