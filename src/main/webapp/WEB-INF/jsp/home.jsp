<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Главная"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <aside class="sidebar">
        <h2>Теги</h2>

        <c:if test="${not empty tagGroups['GENERAL']}">
            <div class="tag-group">
                <h3>Общее</h3>
                <c:forEach var="t" items="${tagGroups['GENERAL']}">
                    <a class="tag-link ${activeTagId == t.id ? 'active' : ''}" href="<c:url value='/?tag=${t.id}'/>">
                        <span class="tag-dot"></span><c:out value="${t.name}"/>
                        <c:if test="${t.usageCount > 0}">
                            <span class="tag-count">${t.usageCount}</span>
                        </c:if>
                    </a>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${not empty tagGroups['META']}">
            <div class="tag-group">
                <h3>Стиль</h3>
                <c:forEach var="t" items="${tagGroups['META']}">
                    <a class="tag-link ${activeTagId == t.id ? 'active' : ''}" href="<c:url value='/?tag=${t.id}'/>">
                        <span class="tag-dot"></span><c:out value="${t.name}"/>
                        <c:if test="${t.usageCount > 0}">
                            <span class="tag-count">${t.usageCount}</span>
                        </c:if>
                    </a>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${not empty tagGroups['CHARACTER']}">
            <div class="tag-group">
                <h3>Персонажи</h3>
                <c:forEach var="t" items="${tagGroups['CHARACTER']}">
                    <a class="tag-link ${activeTagId == t.id ? 'active' : ''}" href="<c:url value='/?tag=${t.id}'/>">
                        <span class="tag-dot"></span><c:out value="${t.name}"/>
                        <c:if test="${t.usageCount > 0}">
                            <span class="tag-count">${t.usageCount}</span>
                        </c:if>
                    </a>
                </c:forEach>
            </div>
        </c:if>
    </aside>

    <main class="content">
        <c:if test="${empty searchQuery && empty activeTagId && not empty featuredGalleries}">
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
                <button class="strip-prev" type="button" aria-label="Назад">
                    <img src="<c:url value='/img/chevron-left.svg'/>" alt="">
                </button>
                <button class="strip-next" type="button" aria-label="Вперёд">
                    <img src="<c:url value='/img/chevron-right.svg'/>" alt="">
                </button>
            </section>
        </c:if>

        <form class="search" method="get" action="<c:url value='/'/>">
            <div class="search-box">
                <img class="search-icon" src="<c:url value='/img/search.svg'/>" alt="">
                <input type="text" name="q" value="<c:out value='${param.q}'/>" placeholder="название, тег или автор">
            </div>
            <button type="submit">поиск</button>
        </form>

        <c:if test="${not empty searchQuery}">
            <p class="search-note">Результаты по запросу «<c:out value="${searchQuery}"/>»</p>
        </c:if>

        <c:if test="${not empty activeTag}">
            <p class="search-note">
                Работы с тегом «<c:out value="${activeTag.name}"/>»
                <a href="<c:url value='/'/>">сбросить</a>
            </p>
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

        <c:if test="${empty searchQuery && empty activeTagId && pages > 1}">
            <div class="pager">
                <c:if test="${page > 1}">
                    <a class="pager-step" href="<c:url value='/?page=${page - 1}'/>">Назад</a>
                </c:if>

                <c:forEach var="number" items="${pageList}">
                    <c:choose>
                        <c:when test="${empty number}">
                            <span class="pager-gap">…</span>
                        </c:when>
                        <c:when test="${number == page}">
                            <span class="pager-item active">${number}</span>
                        </c:when>
                        <c:otherwise>
                            <a class="pager-item" href="<c:url value='/?page=${number}'/>">${number}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${page < pages}">
                    <a class="pager-step" href="<c:url value='/?page=${page + 1}'/>">Вперёд</a>
                </c:if>
            </div>
        </c:if>
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
