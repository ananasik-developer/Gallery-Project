<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Редактирование профиля"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <h1>Редактирование профиля</h1>
        <p class="subtitle">Логин виден другим пользователям и используется для входа.</p>

        <c:if test="${not empty error}">
            <p class="notice error"><c:out value="${error}"/></p>
        </c:if>

        <form class="form" method="post" action="<c:url value='/profile/edit'/>" enctype="multipart/form-data">
            <label>Логин
                <input type="text" name="username" value="<c:out value='${profileUser.username}'/>" minlength="3" maxlength="50" required>
            </label>
            <label>О себе
                <textarea name="bio" rows="5" maxlength="1000"><c:out value="${profileUser.bio}"/></textarea>
            </label>
            <label>Ссылка на телеграм
                <input type="text" name="tgLink" value="<c:out value='${profileUser.tgLink}'/>" maxlength="255" placeholder="@nickname">
            </label>
            <label>Аватар, JPG или PNG до 5 МБ
                <input type="file" name="avatar" accept="image/jpeg,image/png">
            </label>
            <button type="submit">Сохранить</button>
            <a class="cancel" href="<c:url value='/profile'/>">Отмена</a>
        </form>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
