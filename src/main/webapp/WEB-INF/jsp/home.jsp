<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Главная"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <aside class="sidebar">
        <h2>Tags</h2>
        <p class="empty-note">Пока нет тегов</p>
    </aside>

    <main class="content">
        <form class="search" method="get" action="<c:url value='/'/>">
            <div class="search-box">
                <svg class="search-icon" viewBox="0 0 24 24" fill="none" stroke="#1a1a1a" stroke-width="2">
                    <circle cx="10" cy="10" r="7"/>
                    <line x1="15" y1="15" x2="21" y2="21"/>
                </svg>
                <input type="text" name="q" value="<c:out value='${param.q}'/>" placeholder="поиск">
            </div>
            <button type="submit">поиск</button>
        </form>

        <c:if test="${not empty searchQuery}">
            <p class="search-note">Результаты по запросу «<c:out value="${searchQuery}"/>»</p>
        </c:if>

        <c:if test="${empty feed}">
            <p class="empty-note">Пока нет опубликованных работ.</p>
        </c:if>

        <div class="artwork-grid">
            <c:forEach var="art" items="${feed}">
                <a class="artwork-card" href="<c:url value='/artwork/${art.id}'/>">
                    <img src="<c:url value='${art.imagePath}'/>" alt="<c:out value='${art.title}'/>">
                </a>
            </c:forEach>
        </div>
    </main>

    <aside class="aside">
        <h2>Compilation</h2>
        <p class="empty-note">Пока нет работ</p>
    </aside>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
