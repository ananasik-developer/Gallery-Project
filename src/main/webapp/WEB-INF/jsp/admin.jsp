<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Администрирование"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <h1>Пользователи</h1>
        <p class="subtitle">Заблокированный пользователь не сможет войти в аккаунт.</p>

        <table class="admin-table">
            <tr>
                <th>ID</th>
                <th>Логин</th>
                <th>Email</th>
                <th>Роль</th>
                <th>Статус</th>
                <th></th>
            </tr>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td>${u.id}</td>
                    <td><c:out value="${u.username}"/></td>
                    <td><c:out value="${u.email}"/></td>
                    <td><c:out value="${u.role}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${u.active}">активен</c:when>
                            <c:otherwise>заблокирован</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${u.id != currentUser.id}">
                            <form class="inline-form" method="post" action="<c:url value='/admin'/>">
                                <input type="hidden" name="csrfToken" value="${csrfToken}">
                                <input type="hidden" name="formToken" value="${formToken}">
                                <input type="hidden" name="userId" value="${u.id}">
                                <c:choose>
                                    <c:when test="${u.active}">
                                        <input type="hidden" name="active" value="false">
                                        <button type="submit" class="danger">Заблокировать</button>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="active" value="true">
                                        <button type="submit">Разблокировать</button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
