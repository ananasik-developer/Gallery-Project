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

        <c:if test="${empty searchQuery && not empty featuredGalleries}">
            <section class="strip">
                <div class="strip-track" id="galleryStrip">
                    <c:forEach var="g" items="${featuredGalleries}">
                        <a class="strip-slide" href="<c:url value='/gallery/${g.id}'/>">
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
                <button class="strip-prev" type="button" aria-label="Назад">&#8249;</button>
                <button class="strip-next" type="button" aria-label="Вперёд">&#8250;</button>
            </section>
        </c:if>

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
        <h2>Популярные галереи</h2>

        <c:if test="${empty popularGalleries}">
            <p class="empty-note">Пока нет галерей</p>
        </c:if>

        <c:forEach var="g" items="${popularGalleries}">
            <a class="popular-item" href="<c:url value='/gallery/${g.id}'/>">
                <c:choose>
                    <c:when test="${not empty g.artworks}">
                        <img src="<c:url value='${g.artworks[0].imagePath}'/>" alt="<c:out value='${g.title}'/>">
                    </c:when>
                    <c:otherwise>
                        <span class="popular-empty"></span>
                    </c:otherwise>
                </c:choose>
                <span class="popular-title"><c:out value="${g.title}"/></span>
                <span class="popular-likes">
                    <svg viewBox="0 0 24 24" fill="currentColor">
                        <path d="M12 20.5c-1-0.7-6.4-4.2-8.2-7.1C2.1 10.6 3.2 7 6.4 6c2-0.6 3.9 0.3 5 1.9 1.1-1.6 3-2.5 5-1.9 3.2 1 4.3 4.6 2.6 7.4-1.8 2.9-7.2 6.4-8.2 7.1z"/>
                    </svg>
                    ${g.likeCount}
                </span>
            </a>
        </c:forEach>
    </aside>
</div>

<script src="<c:url value='/js/slider.js'/>"></script>
<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
