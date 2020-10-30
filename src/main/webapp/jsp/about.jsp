<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE HTML>
<html>

<c:set var="title" value="Main" scope="page"/>
<%@ include file="../jspf/page/head.jspf" %>

<body>
<%@ include file="../jspf/page/navbar.jspf" %>


<div class="container mt-5">
    <h2><fmt:message key="about.hotel"/></h2>
    <c:out value="${param.lang}" />

    <p class="text-left"><fmt:message key="about.info"/></p>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
