<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="О нас"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="page">
    <main class="content">
        <h1>О нас</h1>
        <div class="about-text">
            <p>
                Это галерея для художников и для тех, кто ищет исполнителя. Автор выкладывает
                работы и собирает их в подборки, а заказчик смотрит эти подборки и решает,
                к кому идти.
            </p>
            <p>
                Это минимально рабочая реализация, созданная в рамках работы над семестровкой для ОРИС.
                Но за учебной формой стоит вполне настоящая идея - сделать маленькое, честное и удобное
                пространство для творческих людей.
            </p>
            <p>
                Смотрите, собирайте своё, находите тех, чьи работы нравятся.
            </p>
        </div>
    </main>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
