<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
</head>
<body>
    <h2>Вход в систему</h2>

    <c:if test="${not empty error}">
        <p style="color: red;"><c:out value="${error}"/></p>
    </c:if>

    <form method="post" action="<c:url value='/login'/>">
        <label>Логин:<br>
            <input type="text" name="username"
                   value="<c:out value='${username}'/>" required autofocus>
        </label>
        <br><br>
        <label>Пароль:<br>
            <input type="password" name="password" required>
        </label>
        <br><br>
        <button type="submit">Войти</button>
    </form>

    <p>Нет аккаунта? <a href="<c:url value='/register'/>">Зарегистрироваться</a></p>
</body>
</html>