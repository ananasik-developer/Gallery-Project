<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${artwork.title}"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <div class="artwork-view">
            <h1><c:out value="${artwork.title}"/></h1>

            <p class="artwork-meta">
                Автор:
                <a href="<c:url value='/profile/${artwork.authorId}'/>"><c:out value="${artwork.authorUsername}"/></a>
                |
                <c:choose>
                    <c:when test="${artwork.status == 'DRAFT'}">черновик</c:when>
                    <c:otherwise>опубликовано</c:otherwise>
                </c:choose>
            </p>

            <img class="artwork-image"
                 src="<c:url value='${artwork.imagePath}'/>"
                 alt="<c:out value='${artwork.title}'/>">

            <p class="artwork-description"><c:out value="${artwork.description}"/></p>

            <c:if test="${not empty artworkTags}">
                <p class="artwork-tags">
                    <c:forEach var="t" items="${artworkTags}">
                        <a class="tag-chip" href="<c:url value='/?tag=${t.id}'/>"><c:out value="${t.name}"/></a>
                    </c:forEach>
                </p>
            </c:if>

            <div class="like-row">
                <c:choose>
                    <c:when test="${empty currentUser}">
                        <a class="like-login" href="<c:url value='/login'/>">Войдите, чтобы оценить работу</a>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="like-button ${liked ? 'active' : ''}"
                                data-artwork="${artwork.id}"
                                data-token="${csrfToken}"
                                data-url="<c:url value='/like'/>">
                            <svg class="heart" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M12 20.5c-1-0.7-6.4-4.2-8.2-7.1C2.1 10.6 3.2 7 6.4 6c2-0.6 3.9 0.3 5 1.9 1.1-1.6 3-2.5 5-1.9 3.2 1 4.3 4.6 2.6 7.4-1.8 2.9-7.2 6.4-8.2 7.1z"/>
                            </svg>
                            <span class="like-count">${likeCount}</span>
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${isOwner && artwork.status == 'DRAFT'}">
                <form class="inline-form" method="post" action="<c:url value='/artwork/publish'/>">
                    <input type="hidden" name="csrfToken" value="${csrfToken}">
                    <input type="hidden" name="formToken" value="${formToken}">
                    <input type="hidden" name="artworkId" value="${artwork.id}">
                    <button type="submit">Опубликовать работу</button>
                </form>
            </c:if>

            <c:if test="${isOwner}">
                <div class="action-row">
                    <a class="cancel" href="<c:url value='/artwork/edit/${artwork.id}'/>">Редактировать</a>
                    <form class="inline-form" method="post" action="<c:url value='/artwork/delete/${artwork.id}'/>"
                          onsubmit="return confirm('Удалить работу? Действие необратимо.');">
                        <input type="hidden" name="csrfToken" value="${csrfToken}">
                        <input type="hidden" name="formToken" value="${formToken}">
                        <button type="submit" class="danger">Удалить</button>
                    </form>
                </div>
            </c:if>

            <a class="cancel" href="<c:url value='/'/>">На главную</a>
        </div>
    </main>
</div>

<script src="<c:url value='/js/likes.js'/>"></script>
<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
