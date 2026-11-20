<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
</head>
<body>
    <h2>Регистрация</h2>

    <c:if test="${not empty error}">
        <p style="color: red;"><c:out value="${error}"/></p>
    </c:if>

    <c:if test="${param.registered == '1'}">
        <p style="color: green;">Регистрация успешна! Теперь войдите.</p>
    </c:if>

    <form method="post" action="<c:url value='/register'/>">
        <label>Логин (мин. 3 символа):<br>
            <input type="text" name="username"
                   value="<c:out value='${username}'/>" minlength="3" required>
        </label>
        <br><br>
        <label>Email:<br>
            <input type="email" name="email"
                   value="<c:out value='${email}'/>" required>
        </label>
        <br><br>
        <label>Пароль (мин. 6 символов):<br>
            <input type="password" name="password" minlength="6" required>
        </label>
        <br><br>
        <button type="submit">Зарегистрироваться</button>
    </form>

    <p>Уже есть аккаунт? <a href="<c:url value='/login'/>">Войти</a></p>
</body>
</html>