<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Регистрация"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <h1>Регистрация</h1>

        <c:if test="${not empty error}">
            <p class="notice error"><c:out value="${error}"/></p>
        </c:if>

        <form class="form" method="post" action="<c:url value='/register'/>">
            <input type="hidden" name="csrfToken" value="${csrfToken}">
            <input type="hidden" name="formToken" value="${formToken}">
            <label>Логин, минимум 3 символа
                <input type="text" name="username" value="<c:out value='${username}'/>" minlength="3" required>
            </label>
            <label>Email
                <input type="email" name="email" value="<c:out value='${email}'/>" required>
            </label>
            <label>Пароль, минимум 6 символов
                <input type="password" name="password" minlength="6" required>
            </label>
            <button type="submit">Зарегистрироваться</button>
            <a class="cancel" href="<c:url value='/login'/>">Войти</a>
        </form>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
