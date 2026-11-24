<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Новая галерея"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <h1>Новая галерея</h1>
        <p class="subtitle">Галерея собирает ваши работы в одну подборку по теме.</p>

        <c:if test="${not empty error}">
            <p class="notice error"><c:out value="${error}"/></p>
        </c:if>

        <form class="form" method="post" action="<c:url value='/gallery/create'/>">
            <input type="hidden" name="csrfToken" value="${csrfToken}">
            <input type="hidden" name="formToken" value="${formToken}">
            <label>Название
                <input type="text" name="title" value="<c:out value='${title}'/>" required maxlength="500">
            </label>
            <label>Описание
                <textarea name="description" rows="5"><c:out value="${description}"/></textarea>
            </label>
            <button type="submit">Создать галерею</button>
            <a class="cancel" href="<c:url value='/'/>">Отмена</a>
        </form>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
