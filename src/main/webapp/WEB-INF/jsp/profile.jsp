<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${profileUser.username}"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content content-page">
        <div class="profile-head">
            <c:choose>
                <c:when test="${not empty profileUser.avatarPath}">
                    <img class="profile-avatar"
                         src="<c:url value='${profileUser.avatarPath}'/>"
                         alt="<c:out value='${profileUser.username}'/>">
                </c:when>
                <c:otherwise>
                    <span class="profile-avatar"></span>
                </c:otherwise>
            </c:choose>

            <div class="profile-info">
                <h1><c:out value="${profileUser.username}"/></h1>

                <c:if test="${not empty profileUser.bio}">
                    <p class="profile-bio"><c:out value="${profileUser.bio}"/></p>
                </c:if>

                <c:if test="${not empty profileUser.tgLink}">
                    <p class="profile-tg">
                        <img class="icon" src="<c:url value='/img/plane.svg'/>" alt="">
                        <c:out value="${profileUser.tgLink}"/>
                    </p>
                </c:if>

                <p class="profile-stats">
                    Подписчиков: ${followers} · подписок: ${following}
                </p>

                <c:if test="${not isOwner && not empty currentUser}">
                    <form class="inline-form" method="post" action="<c:url value='/subscribe'/>">
                        <input type="hidden" name="csrfToken" value="${csrfToken}">
                        <input type="hidden" name="formToken" value="${formToken}">
                        <input type="hidden" name="userId" value="${profileUser.id}">
                        <button type="submit">${subscribed ? 'Отписаться' : 'Подписаться'}</button>
                    </form>
                </c:if>

                <c:if test="${isOwner}">
                    <p class="profile-actions">
                        <a class="cancel" href="<c:url value='/profile/edit'/>">Редактировать профиль</a>
                        <a class="cancel" href="<c:url value='/gallery/create'/>">Новая галерея</a>
                    </p>
                </c:if>
            </div>
        </div>

        <h2 class="section-title">Галереи</h2>

        <c:if test="${empty galleries}">
            <p class="empty-note">Галерей пока нет.</p>
        </c:if>

        <div class="profile-galleries">
            <c:forEach var="g" items="${galleries}">
                <div class="profile-gallery">
                    <a class="profile-gallery-title" href="<c:url value='/gallery/${g.id}'/>"><c:out value="${g.title}"/></a>
                    <span class="gallery-meta-compact">
                        работ: ${g.artworks.size()}<c:if test="${g.featured}"> · на главной</c:if><c:if test="${g.availableForCommission}"> · в поиске</c:if>
                    </span>

                    <c:if test="${isOwner && empty g.artworks}">
                        <span class="gallery-hint">Сначала добавьте работы</span>
                    </c:if>

                    <c:if test="${isOwner && not empty g.artworks}">
                        <div class="profile-gallery-actions">
                            <form method="post" action="<c:url value='/gallery/publication'/>">
                                <input type="hidden" name="csrfToken" value="${csrfToken}">
                                <input type="hidden" name="formToken" value="${formToken}">
                                <input type="hidden" name="galleryId" value="${g.id}">
                                <input type="hidden" name="back" value="profile">
                                <input type="hidden" name="available" value="${g.availableForCommission}">
                                <c:choose>
                                    <c:when test="${g.featured}">
                                        <input type="hidden" name="featured" value="false">
                                        <button type="submit">Убрать с главной</button>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="featured" value="true">
                                        <button type="submit">На главную</button>
                                    </c:otherwise>
                                </c:choose>
                            </form>

                            <form method="post" action="<c:url value='/gallery/publication'/>">
                                <input type="hidden" name="csrfToken" value="${csrfToken}">
                                <input type="hidden" name="formToken" value="${formToken}">
                                <input type="hidden" name="galleryId" value="${g.id}">
                                <input type="hidden" name="back" value="profile">
                                <input type="hidden" name="featured" value="${g.featured}">
                                <c:choose>
                                    <c:when test="${g.availableForCommission}">
                                        <input type="hidden" name="available" value="false">
                                        <button type="submit">Убрать из поиска</button>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="available" value="true">
                                        <button type="submit">В поиск</button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>

        <h2 class="section-title">Работы</h2>

        <c:if test="${empty published}">
            <p class="empty-note">Опубликованных работ пока нет.</p>
        </c:if>

        <div class="artwork-grid">
            <c:forEach var="art" items="${published}">
                <a class="artwork-card" href="<c:url value='/artwork/${art.id}'/>">
                    <img src="<c:url value='${art.imagePath}'/>" alt="<c:out value='${art.title}'/>">
                </a>
            </c:forEach>
        </div>

        <c:if test="${pages > 1}">
            <div class="pager">
                <c:if test="${page > 1}">
                    <a class="pager-step" href="<c:url value='/profile/${profileUser.id}?page=${page - 1}'/>">Назад</a>
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
                            <a class="pager-item" href="<c:url value='/profile/${profileUser.id}?page=${number}'/>">${number}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${page < pages}">
                    <a class="pager-step" href="<c:url value='/profile/${profileUser.id}?page=${page + 1}'/>">Вперёд</a>
                </c:if>
            </div>
        </c:if>

        <c:if test="${isOwner}">
            <h2 class="section-title">Черновики</h2>

            <c:if test="${empty drafts}">
                <p class="empty-note">Черновиков нет.</p>
            </c:if>

            <div class="draft-list">
                <c:forEach var="art" items="${drafts}">
                    <div class="draft-item">
                        <a href="<c:url value='/artwork/${art.id}'/>">
                            <img src="<c:url value='${art.imagePath}'/>" alt="<c:out value='${art.title}'/>">
                        </a>
                        <span class="draft-title"><c:out value="${art.title}"/></span>

                        <div class="draft-actions">
                            <a class="button-link" href="<c:url value='/artwork/edit/${art.id}'/>">Редактировать</a>
                            <form method="post" action="<c:url value='/artwork/publish'/>">
                                <input type="hidden" name="csrfToken" value="${csrfToken}">
                                <input type="hidden" name="formToken" value="${formToken}">
                                <input type="hidden" name="artworkId" value="${art.id}">
                                <button type="submit">Опубликовать</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
