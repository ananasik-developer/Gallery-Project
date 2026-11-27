<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Редактирование работы"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <div class="form-box">
            <h1>Редактирование работы</h1>

            <c:if test="${not empty error}">
                <p class="notice error"><c:out value="${error}"/></p>
            </c:if>

            <form class="form" method="post" action="<c:url value='/artwork/edit/${artwork.id}'/>">
                <input type="hidden" name="csrfToken" value="${csrfToken}">
                <input type="hidden" name="formToken" value="${formToken}">
                <label>Название
                    <input type="text" name="title" value="<c:out value='${artwork.title}'/>" required maxlength="128">
                </label>
                <label>Описание
                    <textarea name="description" rows="5" maxlength="2000"><c:out value="${artwork.description}"/></textarea>
                </label>

                <div class="tag-picker">
                    <span class="tag-picker-label">Основные теги</span>
                    <div class="tag-choices">
                        <c:forEach var="t" items="${generalTags}">
                            <label class="tag-check">
                                <input type="checkbox" name="tagIds" value="${t.id}" ${selectedTagIds.contains(t.id) ? 'checked' : ''}>
                                <c:out value="${t.name}"/>
                            </label>
                        </c:forEach>
                    </div>

                    <label class="tag-custom">
                        Свои теги через запятую, не больше трёх
                        <input type="text" name="customTags" value="<c:out value='${customTags}'/>" maxlength="200" placeholder="autumn, sketch">
                    </label>

                    <label class="tag-custom">
                        Группа для новых тегов
                        <select name="customCategory">
                            <option value="META" ${customCategory == 'META' ? 'selected' : ''}>Стиль</option>
                            <option value="CHARACTER" ${customCategory == 'CHARACTER' ? 'selected' : ''}>Персонажи</option>
                        </select>
                    </label>
                </div>

                <button type="submit">Сохранить</button>
                <a class="cancel" href="<c:url value='/artwork/${artwork.id}'/>">Отмена</a>
            </form>
        </div>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
