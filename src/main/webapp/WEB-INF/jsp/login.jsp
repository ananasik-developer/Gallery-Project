<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Вход"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <h1>Вход</h1>

        <c:if test="${param.registered == '1'}">
            <p class="notice">Регистрация прошла успешно. Войдите под своим логином.</p>
        </c:if>

        <c:if test="${param.blocked == '1'}">
            <p class="notice error">Аккаунт заблокирован администратором.</p>
        </c:if>

        <c:if test="${not empty error}">
            <p class="notice error"><c:out value="${error}"/></p>
        </c:if>

        <form class="form" method="post" action="<c:url value='/login'/>">
            <input type="hidden" name="csrfToken" value="${csrfToken}">
            <input type="hidden" name="formToken" value="${formToken}">
            <label>Логин
                <input type="text" name="username" value="<c:out value='${username}'/>" required autofocus>
            </label>
            <label>Пароль
                <input type="password" name="password" required>
            </label>
            <button type="submit">Войти</button>
            <a class="cancel" href="<c:url value='/register'/>">Зарегистрироваться</a>
        </form>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
