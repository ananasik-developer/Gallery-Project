<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${gallery.title}"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content content-page">
        <div class="gallery-view">
            <c:if test="${not empty artworks}">
                <button type="button" class="back-button">Назад</button>

                <img class="gallery-cover"
                     src="<c:url value='${artworks[0].imagePath}'/>"
                     alt="<c:out value='${gallery.title}'/>">
            </c:if>

            <h1><c:out value="${gallery.title}"/></h1>
            <p class="subtitle">
                Автор:
                <a href="<c:url value='/profile/${gallery.authorId}'/>"><c:out value="${gallery.authorUsername}"/></a>
                ·
                работ: ${artworks.size()}
            </p>

            <c:if test="${not empty gallery.description}">
                <p class="artwork-description"><c:out value="${gallery.description}"/></p>
            </c:if>

            <c:if test="${isOwner}">
                <c:if test="${not empty artworks}">
                    <div class="publication-actions">
                        <form class="inline-form" method="post" action="<c:url value='/gallery/publication'/>">
                            <input type="hidden" name="csrfToken" value="${csrfToken}">
                            <input type="hidden" name="formToken" value="${formToken}">
                            <input type="hidden" name="galleryId" value="${gallery.id}">
                            <input type="hidden" name="available" value="${gallery.availableForCommission}">
                            <c:choose>
                                <c:when test="${gallery.featured}">
                                    <input type="hidden" name="featured" value="false">
                                    <button type="submit">Убрать с главной</button>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="featured" value="true">
                                    <button type="submit">Опубликовать на главной</button>
                                </c:otherwise>
                            </c:choose>
                        </form>

                        <form class="inline-form" method="post" action="<c:url value='/gallery/publication'/>">
                            <input type="hidden" name="csrfToken" value="${csrfToken}">
                            <input type="hidden" name="formToken" value="${formToken}">
                            <input type="hidden" name="galleryId" value="${gallery.id}">
                            <input type="hidden" name="featured" value="${gallery.featured}">
                            <c:choose>
                                <c:when test="${gallery.availableForCommission}">
                                    <input type="hidden" name="available" value="false">
                                    <button type="submit">Убрать из поиска исполнителя</button>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="available" value="true">
                                    <button type="submit">В поиск исполнителя</button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </div>
                </c:if>

                <div class="publication-actions">
                    <a class="button-link" href="<c:url value='/gallery-manage/add-artwork?galleryId=${gallery.id}'/>">
                        Добавить работу
                    </a>

                    <form class="inline-form" method="post" action="<c:url value='/gallery/delete/${gallery.id}'/>"
                          onsubmit="return confirm('Удалить галерею? Работы останутся, удалится только подборка.');">
                        <input type="hidden" name="csrfToken" value="${csrfToken}">
                        <input type="hidden" name="formToken" value="${formToken}">
                        <button type="submit" class="danger">Удалить галерею</button>
                    </form>
                </div>
            </c:if>

            <c:if test="${empty artworks}">
                <p class="empty-note">В этой галерее пока нет работ.</p>
            </c:if>

            <c:if test="${not empty artworks}">
                <h2 class="section-title">Работы в галерее</h2>

                <div class="gallery-thumbs">
                    <c:forEach var="art" items="${artworks}">
                        <a href="<c:url value='/artwork/${art.id}'/>">
                            <img src="<c:url value='${art.imagePath}'/>" alt="<c:out value='${art.title}'/>" loading="lazy">
                            <p><c:out value="${art.title}"/></p>
                        </a>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
