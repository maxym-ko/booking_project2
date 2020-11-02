<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE HTML>
<html>

<c:set var="title" value="Login" scope="page"/>
<%@ include file="../jspf/page/head.jspf" %>

<body>
<%@ include file="../jspf/page/navbar.jspf" %>

<div class="container mt-5">
    <form action="<c:url value="/controller?command=login"/>" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="login.username"/>: </label>
            <div class="col-sm-5">
                <input class="form-control ${requestScope.usernameValid}" value="${requestScope.usernameNotFound}"
                       type="text" name="username" placeholder="username" required>
                <div class="invalid-feedback">
                    Couldn't find such a username
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="login.password"/>: </label>
            <div class="col-sm-5">
                <input class="form-control ${requestScope.passwordValid}" type="password" name="password"
                       placeholder="password" required>
                <div class="invalid-feedback">
                    Wrong password
                </div>
            </div>
        </div>
        <button class="btn btn-primary mr-2" type="submit"><fmt:message key="login.sign_in"/></button>
        <a href="<c:url value="/controller?command=show_registration"/>"><fmt:message key="login.add_new_user"/></a>
    </form>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
