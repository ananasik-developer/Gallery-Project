<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${gallery.title}"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <h1><c:out value="${gallery.title}"/></h1>
        <p class="subtitle">
            Автор: <c:out value="${gallery.authorUsername}"/>
            |
            работ: ${artworks.size()}
        </p>

        <p class="artwork-description"><c:out value="${gallery.description}"/></p>

        <c:if test="${isOwner}">
            <a class="cancel" href="<c:url value='/gallery-manage/add-artwork?galleryId=${gallery.id}'/>">
                Добавить работу в галерею
            </a>
        </c:if>

        <c:if test="${empty artworks}">
            <p class="empty-note">В этой галерее пока нет работ.</p>
        </c:if>

        <div class="gallery-thumbs">
            <c:forEach var="art" items="${artworks}">
                <a href="<c:url value='/artwork/${art.id}'/>">
                    <img src="<c:url value='${art.imagePath}'/>" alt="<c:out value='${art.title}'/>" loading="lazy">
                    <p><c:out value="${art.title}"/></p>
                </a>
            </c:forEach>
        </div>

        <p style="margin-top: 22px;">
            <a class="cancel" href="<c:url value='/'/>">На главную</a>
        </p>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
