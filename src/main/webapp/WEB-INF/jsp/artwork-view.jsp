<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${artwork.title}"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content content-page">
        <div class="artwork-view">
            <div class="artwork-image-col">
                <img class="artwork-image"
                     src="<c:url value='${artwork.imagePath}'/>"
                     alt="<c:out value='${artwork.title}'/>">
            </div>

            <div class="artwork-info">
                <c:if test="${artwork.status == 'PUBLISHED'}">
                    <button type="button" class="back-button">Назад</button>
                </c:if>

                <h1><c:out value="${artwork.title}"/></h1>

                <p class="artwork-meta">
                    Автор:
                    <a href="<c:url value='/profile/${artwork.authorId}'/>"><c:out value="${artwork.authorUsername}"/></a>
                    ·
                    <c:choose>
                        <c:when test="${artwork.status == 'DRAFT'}">черновик</c:when>
                        <c:otherwise>опубликовано</c:otherwise>
                    </c:choose>
                </p>

                <c:if test="${not empty artwork.description}">
                    <p class="artwork-description"><c:out value="${artwork.description}"/></p>
                </c:if>

                <c:if test="${not empty artworkTags}">
                    <p class="artwork-tags">
                        <c:forEach var="t" items="${artworkTags}">
                            <a class="tag-chip" href="<c:url value='/?tag=${t.id}'/>"><c:out value="${t.name}"/></a>
                        </c:forEach>
                    </p>
                </c:if>

                <c:if test="${artwork.status == 'PUBLISHED'}">
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
                                    <img class="heart" src="<c:url value='/img/heart-dark.svg'/>" alt="">
                                    <img class="heart heart-active" src="<c:url value='/img/heart-red.svg'/>" alt="">
                                    <span class="like-count">${likeCount}</span>
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>

                <c:if test="${isOwner}">
                    <div class="artwork-actions">
                        <c:if test="${artwork.status == 'DRAFT'}">
                            <form class="inline-form" method="post" action="<c:url value='/artwork/publish'/>">
                                <input type="hidden" name="csrfToken" value="${csrfToken}">
                                <input type="hidden" name="formToken" value="${formToken}">
                                <input type="hidden" name="artworkId" value="${artwork.id}">
                                <button type="submit">Опубликовать</button>
                            </form>

                            <a class="button-link" href="<c:url value='/artwork/edit/${artwork.id}'/>">Редактировать</a>
                        </c:if>

                        <form class="inline-form" method="post" action="<c:url value='/artwork/delete/${artwork.id}'/>"
                              onsubmit="return confirm('Удалить работу? Действие необратимо.');">
                            <input type="hidden" name="csrfToken" value="${csrfToken}">
                            <input type="hidden" name="formToken" value="${formToken}">
                            <button type="submit" class="danger">Удалить</button>
                        </form>
                    </div>
                </c:if>
            </div>
        </div>
    </main>
</div>

<script src="<c:url value='/js/likes.js'/>"></script>
<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
