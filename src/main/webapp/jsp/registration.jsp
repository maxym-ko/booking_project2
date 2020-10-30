<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE HTML>
<html>

<c:set var="title" value="Registration" scope="page"/>
<%@ include file="../jspf/page/head.jspf" %>

<body>
<%@ include file="../jspf/page/navbar.jspf" %>

<div class="container mt-5">
    <form action="<c:url value="/controller?command=registration"/>" method="post">
        <div class="form-group row">
            <label for="username" class="col-sm-2 col-form-label"><fmt:message key="login.username"/>: </label>
            <div class="col-sm-5">
                <input class="form-control" type="text" id="username" name="username" placeholder="username"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label"><fmt:message key="login.password"/>: </label>
            <div class="col-sm-5">
                <input class="form-control" type="password" id="password" name="password" placeholder="password"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="role" class="col-sm-2 col-form-label"><fmt:message key="login.role"/>: </label>
            <div class="col-sm-5">
                <select class="custom-select" id="role" name="role">
                    <option disabled selected><fmt:message key="login.role"/></option>
                    <option value="USER"><fmt:message key="login.role.user"/></option>
                    <option value="ADMIN"><fmt:message key="login.role.admin"/></option>
                </select>
            </div>
        </div>
        <button class="btn btn-primary mr-2" type="submit"><fmt:message key="login.create"/></button>
    </form>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
