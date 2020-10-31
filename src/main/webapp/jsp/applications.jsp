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

    <c:if test="${role == 'USER'}">
        <div class="container">
            <a class="btn btn-primary mb-3" data-toggle="collapse" href="#newApplicationsId" role="button"
               aria-expanded="false"
               aria-controls="collapseExample">
                <fmt:message key="applications.create"/>
            </a>
            <div class="collapse" id="newApplicationsId">
                <div class="form-group">
                    <form action="<c:url value="/controller?command=create_application"/>" method="post">
                        <div class="form-row">
                            <div class="form-group col-md">
                                <input class="form-control" type="number" min="0" name="capacity"
                                       placeholder="<fmt:message key="room.add.capacity"/>">
                            </div>
                            <div class="form-group col-md">
                                <select class="custom-select" name="type">
                                    <option disabled selected><fmt:message key="applications.type_select"/></option>
                                    <option value="ECONOMY"><fmt:message key="room.type.economy"/></option>
                                    <option value="STANDARD"><fmt:message key="room.type.standard"/></option>
                                    <option value="LUXURY"><fmt:message key="room.type.luxury"/></option>
                                </select>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="check-in"><fmt:message key="room.check_in"/></label>
                                <input class="form-control" name="checkInDate" type="date"
                                       id="check-in">
                            </div>
                            <div class="form-group col-md-6">
                                <label for="check-out"><fmt:message key="room.check_out"/></label>
                                <input class="form-control" name="checkOutDate" type="date"
                                       id="check-out">
                            </div>
                        </div>
                        <div class="form-group">
                            <button class="btn btn-success" type="submit"><fmt:message
                                    key="applications.action.create"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:if>

    <div class="container">
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

            <c:forEach items="${requestScope.applications}" var="application" varStatus="loop">
                <tr>
                    <th scope="row">${loop.count + (empty param.page ? 0 : param.page - 1) * requestScope.recordsPerPage}</th>
                    <c:choose>
                        <c:when test="${not empty application.room}">
                            <td><img class="img-thumbnail"
                                     src="${pageContext.request.contextPath}/img/${application.room.imgName}">
                            </td>
                            <td>${application.room.capacity}</td>
                            <td>${application.room.type}</td>
                            <td>${application.checkInDate}</td>
                            <td>${application.checkOutDate}</td>
                            <td>${application.totalPrice}$</td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <c:choose>
                                    <c:when test="${role == 'USER'}">
                                        <fmt:message key="applications.image_waiting"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="applications.image_not_selected"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${application.requirementCapacity}</td>
                            <td>${application.requirementType}</td>
                            <td>${application.checkInDate}</td>
                            <td>${application.checkOutDate}</td>
                            <td>${role == 'ADMIN' and application.status != 'LOOKING_FOR' ? application.bill.totalPrice : '?'}</td>
                        </c:otherwise>
                    </c:choose>

                    <td>
                        <c:choose>
                            <c:when test="${role == 'USER'}">
                                <c:if test="${application.status == 'ACCEPT_WAITING'}">
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <form action="<c:url value="/controller?command=accept_application"/>"
                                                  method="post">
                                                <button type="submit" class="btn btn-success"><fmt:message
                                                        key="applications.action.accept"/></button>
                                                <input type="hidden" name="id" value="${application.id}">
                                            </form>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <form action="<c:url value="/controller?command=reject_application"/>"
                                                  method="post">
                                                <button type="submit" class="btn btn-warning"><fmt:message
                                                        key="applications.action.reject"/></button>
                                                <input type="hidden" name="id" value="${application.id}">
                                            </form>
                                        </div>
                                    </div>
                                </c:if>
                            </c:when>

                            <c:otherwise>
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
                                        <form action="<c:url value="/controller?command=remove_application"/>"
                                              method="post">
                                            <button type="submit" class="btn btn-danger"><fmt:message
                                                    key="applications.action.remove"/></button>
                                            <input type="hidden" name="id" value="${application.id}">
                                        </form>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>

                        <c:if test="${role == 'USER'}">
                            <form action="<c:url value="/controller?command=remove_application"/>"
                                  method="post">
                                <button type="submit" class="btn btn-danger"><fmt:message
                                        key="applications.action.remove"/></button>
                                <input type="hidden" name="id" value="${application.id}">
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@ include file="../jspf/pagination.jspf" %>
    </div>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
