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
            <input type="hidden" name="csrfToken" value="${csrfToken}">
            <input type="hidden" name="formToken" value="${formToken}">
            <label>Название
                <input type="text" name="title" value="<c:out value='${title}'/>" required maxlength="128">
            </label>
            <label>Описание
                <textarea name="description" rows="5" maxlength="2000"><c:out value="${description}"/></textarea>
            </label>
            <label>Изображение, JPG или PNG до 10 МБ
                <input type="file" name="imageFile" accept="image/jpeg,image/png" required>
            </label>

            <div class="tag-picker">
                <span class="tag-picker-label">Теги, не больше десяти</span>
                <c:forEach var="t" items="${allTags}">
                    <label class="tag-check">
                        <input type="checkbox" name="tagIds" value="${t.id}" ${selectedTagIds.contains(t.id) ? 'checked' : ''}>
                        <c:out value="${t.name}"/>
                    </label>
                </c:forEach>

                <label class="tag-custom">
                    Свои теги через запятую
                    <input type="text" name="customTags" value="<c:out value='${customTags}'/>" maxlength="200" placeholder="autumn, sketch">
                </label>

                <label class="tag-custom">
                    Группа для новых тегов
                    <select name="customCategory">
                        <option value="GENERAL" ${customCategory == 'GENERAL' ? 'selected' : ''}>General</option>
                        <option value="META" ${customCategory == 'META' ? 'selected' : ''}>Meta</option>
                        <option value="CHARACTER" ${customCategory == 'CHARACTER' ? 'selected' : ''}>Characters</option>
                    </select>
                </label>
            </div>

            <button type="submit">Сохранить черновик</button>
            <a class="cancel" href="<c:url value='/'/>">Отмена</a>
        </form>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
