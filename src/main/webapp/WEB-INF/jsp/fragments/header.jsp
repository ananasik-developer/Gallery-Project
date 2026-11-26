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
        <a href="<c:url value='/artists'/>">Поиск исполнителя</a>
        <a href="<c:url value='/about'/>">О нас</a>
    </nav>
    <div class="user-box">
        <c:choose>
            <c:when test="${not empty currentUser}">
                <a class="action-link" href="<c:url value='/artwork/create'/>">Новая работа</a>
                <a class="action-link" href="<c:url value='/gallery/create'/>">Новая галерея</a>
                <a class="nickname" href="<c:url value='/profile'/>"><c:out value="${currentUser.username}"/></a>
                <span class="user-id">id: ${currentUser.id}</span>
                <c:choose>
                    <c:when test="${not empty currentUser.avatarPath}">
                        <img class="avatar" src="<c:url value='${currentUser.avatarPath}'/>" alt="">
                    </c:when>
                    <c:otherwise>
                        <span class="avatar"></span>
                    </c:otherwise>
                </c:choose>
                <c:if test="${currentUser.role == 'ADMIN'}">
                    <a class="auth-link" href="<c:url value='/admin'/>">Админка</a>
                </c:if>
                <form class="logout-form" method="post" action="<c:url value='/logout'/>">
                    <input type="hidden" name="csrfToken" value="${csrfToken}">
                    <input type="hidden" name="formToken" value="${formToken}">
                    <button type="submit">Выйти</button>
                </form>
            </c:when>
            <c:otherwise>
                <a class="auth-link" href="<c:url value='/login'/>">Войти</a>
                <a class="auth-link" href="<c:url value='/register'/>">Регистрация</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>
