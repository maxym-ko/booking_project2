<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>

<c:set var="title" value="Login" scope="page"/>
<%@ include file="../jspf/page/head.jspf" %>

<body>
<%@ include file="../jspf/page/navbar.jspf" %>

<div class="container mt-5">
    <form action="<c:url value="/controller?command=login"/>" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Username: </label>
            <div class="col-sm-5">
                <input class="form-control" type="text" name="username" placeholder="username"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password: </label>
            <div class="col-sm-5">
                <input class="form-control" type="password" name="password" placeholder="password"/>
            </div>
        </div>
        <button class="btn btn-primary mr-2" type="submit">Login</button>
        <a href="<c:url value="/controller?command=show_registration"/>">Add new user</a>
    </form>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
