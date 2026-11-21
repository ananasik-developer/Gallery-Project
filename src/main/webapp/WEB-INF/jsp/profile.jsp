<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${profileUser.username}"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
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
                <p class="user-id">id: ${profileUser.id}</p>

                <c:if test="${not empty profileUser.bio}">
                    <p class="profile-bio"><c:out value="${profileUser.bio}"/></p>
                </c:if>

                <c:if test="${not empty profileUser.tgLink}">
                    <p class="profile-tg"><c:out value="${profileUser.tgLink}"/></p>
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

        <div class="gallery-rows">
            <c:forEach var="g" items="${galleries}">
                <div class="gallery-row">
                    <a href="<c:url value='/gallery/${g.id}'/>"><c:out value="${g.title}"/></a>

                    <c:if test="${isOwner}">
                        <form method="post" action="<c:url value='/gallery/featured'/>">
                            <input type="hidden" name="galleryId" value="${g.id}">
                            <input type="hidden" name="back" value="profile">
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

                        <form method="post" action="<c:url value='/gallery/commission'/>">
                            <input type="hidden" name="galleryId" value="${g.id}">
                            <input type="hidden" name="back" value="profile">
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

        <c:if test="${isOwner}">
            <h2 class="section-title">Черновики</h2>

            <c:if test="${empty drafts}">
                <p class="empty-note">Черновиков нет.</p>
            </c:if>

            <div class="draft-list">
                <c:forEach var="art" items="${drafts}">
                    <div class="draft-item">
                        <img src="<c:url value='${art.imagePath}'/>" alt="<c:out value='${art.title}'/>">
                        <span class="draft-title"><c:out value="${art.title}"/></span>
                        <form method="post" action="<c:url value='/artwork/publish'/>">
                            <input type="hidden" name="artworkId" value="${art.id}">
                            <button type="submit">Опубликовать</button>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
