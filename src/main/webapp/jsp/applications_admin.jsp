<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>

<c:set var="title" value="Applications" scope="page"/>
<%@ include file="../jspf/page/head.jspf" %>

<body>
<%@ include file="../jspf/page/navbar.jspf" %>

<div class="container mt-5">

    <table class="table">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th style="width: 20%" scope="col">Room photo</th>
            <th scope="col">Required capacity</th>
            <th scope="col">Required type</th>
            <th scope="col">Check in</th>
            <th scope="col">Check out</th>
            <th scope="col">Status</th>
            <th scope="col">Price</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>

        <c:set var="counter" value="1"/>
        <c:forEach items="${applications}" var="application">
            <c:if test="${application.status == 'LOOKING_FOR' or application.status == 'ACCEPT_WAITING'}">
                <tr>
                    <c:choose>
                        <c:when test="${application.status != 'LOOKING_FOR'}">
                            <th scope="row">${counter}</th>
                            <td><img class="img-thumbnail"
                                     src=""${pageContext.request.contextPath}/img/${application.room.imgName}"">
                            </td>
                            <td>${application.requirementCapacity}</td>
                            <td>${application.requirementType}</td>
                            <td>${application.checkInDate}</td>
                            <td>${application.checkOutDate}</td>
                            <td>${application.status}</td>
                            <td>${application.totalPrice}$</td>
                        </c:when>
                        <c:otherwise>
                            <th scope="row">${counter}</th>
                            <td><@spring.message "applications.image_not_selected"/></td>
                            <td>${application.requirementCapacity}</td>
                            <td>${application.requirementType}</td>
                            <td>${application.checkInDate}</td>
                            <td>${application.checkOutDate}</td>
                            <td>${application.status}</td>
                            <td>?</td>
                        </c:otherwise>
                    </c:choose>

                    <td>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <a href="<c:url value="/controller?command=find_room?id=${application.id}"/>"
                                   type="submit" class="btn btn-success">
                                    <c:choose>
                                        <c:when test="${application.status == 'ACCEPT_WAITING'}">
                                            Change
                                        </c:when>
                                        <c:otherwise>
                                            Find
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </div>
                            <div class="form-group col-md-6">
                                <form action="<c:url value="/controller?command=remove_application_admin"/>"
                                      method="post">
                                    <button type="submit" class="btn btn-danger">Remove</button>
                                    <input type="hidden" name="id" value="${application.id}">
                                </form>
                            </div>
                        </div>

                    </td>
                </tr>

                <c:set var="count" value="${counter + 1}"/>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
