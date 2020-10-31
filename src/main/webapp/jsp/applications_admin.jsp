<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

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
            <th style="width: 20%" scope="col"><fmt:message key="applications.image"/></th>
            <th scope="col"><fmt:message key="applications.required_capacity"/></th>
            <th scope="col"><fmt:message key="applications.required_type"/></th>
            <th scope="col"><fmt:message key="applications.check_in"/></th>
            <th scope="col"><fmt:message key="applications.check_out"/></th>
            <th scope="col"><fmt:message key="applications.price"/></th>
            <th scope="col"><fmt:message key="applications.action"/></th>
        </tr>
        </thead>
        <tbody>

        <c:set var="counter" value="1"/>
        <c:forEach items="${requestScope.applications}" var="application">
            <c:if test="${application.status == 'LOOKING_FOR' or application.status == 'ACCEPT_WAITING'}">
                <tr>
                    <c:choose>
                        <c:when test="${application.status != 'LOOKING_FOR'}">
                            <th scope="row">${counter}</th>
                            <td><img class="img-thumbnail"
                                     src="${pageContext.request.contextPath}/img/${application.room.imgName}">
                            </td>
                            <td>${application.requirementCapacity}</td>
                            <td>${application.requirementType}</td>
                            <td>${application.checkInDate}</td>
                            <td>${application.checkOutDate}</td>
                            <td>${application.totalPrice}$</td>
                        </c:when>
                        <c:otherwise>
                            <th scope="row">${counter}</th>
                            <td><fmt:message key="applications.image_not_selected"/></td>
                            <td>${application.requirementCapacity}</td>
                            <td>${application.requirementType}</td>
                            <td>${application.checkInDate}</td>
                            <td>${application.checkOutDate}</td>
                            <td>?</td>
                        </c:otherwise>
                    </c:choose>

                    <td>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <a href="<c:url value="/controller?command=find_room&id=${application.id}"/>"
                                   type="submit" class="btn btn-success">
                                    <c:choose>
                                        <c:when test="${application.status == 'ACCEPT_WAITING'}">
                                            <fmt:message key="applications.action.change"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="applications.action.find"/>
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </div>
                            <div class="form-group col-md-6">
                                <form action="<c:url value="/controller?command=remove_application_admin"/>"
                                      method="post">
                                    <button type="submit" class="btn btn-danger"><fmt:message
                                            key="applications.action.remove"/></button>
                                    <input type="hidden" name="id" value="${application.id}">
                                </form>
                            </div>
                        </div>

                    </td>
                </tr>

                <c:set var="counter" value="${counter + 1}"/>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
