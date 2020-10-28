<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>

<c:set var="title" value="Error" scope="page"/>
<%@ include file="../jspf/page/head.jspf" %>

<body>
<%@ include file="../jspf/page/navbar.jspf" %>


<div class="container mt-5">
    <h1>Error</h1>
    <h3>${requestScope.message}</h3>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
