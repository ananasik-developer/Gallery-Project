<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Ошибка"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <h1>Что-то пошло не так</h1>

        <p class="subtitle">
            <c:choose>
                <c:when test="${pageContext.errorData.statusCode == 404}">
                    Такой страницы нет. Возможно, ссылка устарела или в адресе опечатка.
                </c:when>
                <c:when test="${pageContext.errorData.statusCode == 403}">
                    Здесь нужны права, которых у вас нет. Войдите под другим аккаунтом.
                </c:when>
                <c:otherwise>
                    На сервере произошла ошибка. Попробуйте позже.
                </c:otherwise>
            </c:choose>
        </p>

        <p>
            <a class="cancel" href="<c:url value='/'/>">На главную</a>
        </p>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
