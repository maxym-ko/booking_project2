<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE HTML>
<html>

<c:set var="title" value="Error" scope="page"/>
<%@ include file="../jspf/page/head.jspf" %>

<body>
<%@ include file="../jspf/page/navbar.jspf" %>


<div class="container mt-5">
    <c:choose>
        <c:when test="${empty requestScope.message}">
            <h1>Error 404</h1>
            <h3>Cannot find such a resource</h3>
        </c:when>
        <c:otherwise>
            <h1>Error</h1>
            <h3>${requestScope.message}</h3>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
