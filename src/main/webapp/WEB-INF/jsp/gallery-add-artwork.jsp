<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Добавить работу"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <div class="form-box">
            <h1>Добавить работу в галерею</h1>

            <c:if test="${not empty error}">
                <p class="notice error"><c:out value="${error}"/></p>
            </c:if>

            <c:if test="${empty userArtworks}">
                <p class="notice">
                    У вас нет опубликованных работ. Сначала создайте работу и опубликуйте её.
                </p>
                <a class="cancel" href="<c:url value='/artwork/create'/>">Создать работу</a>
            </c:if>

            <c:if test="${not empty userArtworks}">
                <form class="form" method="post" action="<c:url value='/gallery-manage/add-artwork'/>">
                    <input type="hidden" name="csrfToken" value="${csrfToken}">
                    <input type="hidden" name="formToken" value="${formToken}">
                    <input type="hidden" name="galleryId" value="${galleryId}">
                    <label>Работа
                        <select name="artworkId" required>
                            <option value="">Выберите работу</option>
                            <c:forEach var="art" items="${userArtworks}">
                                <option value="${art.id}"><c:out value="${art.title}"/></option>
                            </c:forEach>
                        </select>
                    </label>
                    <button type="submit">Добавить</button>
                    <a class="cancel" href="<c:url value='/gallery/${galleryId}'/>">Отмена</a>
                </form>
            </c:if>
        </div>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
