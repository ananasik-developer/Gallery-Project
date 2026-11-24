<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Редактирование работы"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <h1>Редактирование работы</h1>
        <p class="subtitle">Меняются название и описание, изображение остаётся прежним.</p>

        <c:if test="${not empty error}">
            <p class="notice error"><c:out value="${error}"/></p>
        </c:if>

        <form class="form" method="post" action="<c:url value='/artwork/edit/${artwork.id}'/>">
            <label>Название
                <input type="text" name="title" value="<c:out value='${artwork.title}'/>" required maxlength="128">
            </label>
            <label>Описание
                <textarea name="description" rows="5" maxlength="2000"><c:out value="${artwork.description}"/></textarea>
            </label>
            <button type="submit">Сохранить</button>
            <a class="cancel" href="<c:url value='/artwork/${artwork.id}'/>">Отмена</a>
        </form>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
