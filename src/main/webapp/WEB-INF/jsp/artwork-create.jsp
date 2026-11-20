<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Новая работа"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <h1>Новая работа</h1>
        <p class="subtitle">Работа сохранится как черновик. Опубликовать её можно со страницы работы.</p>

        <c:if test="${not empty error}">
            <p class="notice error"><c:out value="${error}"/></p>
        </c:if>

        <form class="form" method="post" action="<c:url value='/artwork/create'/>" enctype="multipart/form-data">
            <label>Название
                <input type="text" name="title" value="<c:out value='${title}'/>" required maxlength="500">
            </label>
            <label>Описание
                <textarea name="description" rows="5"><c:out value="${description}"/></textarea>
            </label>
            <label>Изображение, JPG или PNG до 10 МБ
                <input type="file" name="imageFile" accept="image/jpeg,image/png" required>
            </label>
            <button type="submit">Сохранить черновик</button>
            <a class="cancel" href="<c:url value='/'/>">Отмена</a>
        </form>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
