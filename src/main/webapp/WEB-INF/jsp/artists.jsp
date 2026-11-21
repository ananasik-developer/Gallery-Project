<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Поиск исполнителя"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <h1>Поиск исполнителя</h1>
        <p class="subtitle">
            Галереи авторов, которые готовы взять заказ. Откройте галерею, чтобы посмотреть
            работы целиком, и зайдите в профиль автора.
        </p>

        <c:if test="${empty galleries}">
            <p class="empty-note">Пока никто не выставил свою галерею.</p>
        </c:if>

        <div class="gallery-cards">
            <c:forEach var="g" items="${galleries}">
                <a class="gallery-card" href="<c:url value='/gallery/${g.id}'/>">
                    <span class="collage">
                        <c:forEach var="art" items="${g.artworks}" end="2">
                            <img src="<c:url value='${art.imagePath}'/>" alt="<c:out value='${art.title}'/>">
                        </c:forEach>
                        <c:if test="${empty g.artworks}">
                            <span class="collage-empty"></span>
                        </c:if>
                    </span>
                    <span class="gallery-caption">
                        <strong><c:out value="${g.title}"/></strong>
                        <span class="gallery-meta-compact">
                            Автор: <c:out value="${g.authorUsername}"/> · работ: ${g.artworks.size()}
                        </span>
                    </span>
                </a>
            </c:forEach>
        </div>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
