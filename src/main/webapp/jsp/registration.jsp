<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>

<c:set var="title" value="Login" scope="page"/>
<%@ include file="../jspf/head.jspf" %>

<body>
<%@ include file="../jspf/navbar.jspf" %>

<div class="container mt-5">
    <form action="<c:url value="/controller?command=registration"/>" method="post">
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
        <div class="form-group row">
            <label for="role" class="col-sm-2 col-form-label">Role: </label>
            <div class="col-sm-5">
                <select class="custom-select" id="role" name="role">
                    <option disabled selected>Role</option>
                    <option value="USER">User</option>
                    <option value="ADMIN">Admin</option>
                </select>
            </div>
        </div>
        <button class="btn btn-primary mr-2" type="submit">Create</button>
    </form>
</div>

<jsp:include page="../jspf/footer.jspf"/>
</body>
</html>
