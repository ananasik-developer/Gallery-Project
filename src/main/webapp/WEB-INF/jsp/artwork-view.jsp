<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${artwork.title}"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <div class="artwork-view">
            <h1><c:out value="${artwork.title}"/></h1>

            <p class="artwork-meta">
                Автор: <c:out value="${artwork.authorUsername}"/>
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

            <c:if test="${isOwner && artwork.status == 'DRAFT'}">
                <form class="inline-form" method="post" action="<c:url value='/artwork/publish'/>">
                    <input type="hidden" name="artworkId" value="${artwork.id}">
                    <button type="submit">Опубликовать работу</button>
                </form>
            </c:if>

            <a class="cancel" href="<c:url value='/'/>">На главную</a>
        </div>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
