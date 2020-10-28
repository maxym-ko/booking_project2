<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>

<c:set var="title" value="Applications" scope="page"/>
<%@ include file="../jspf/page/head.jspf" %>

<body>
<%@ include file="../jspf/page/navbar.jspf" %>

<div class="container mt-5">
    <a class="btn btn-primary mb-3" data-toggle="collapse" href="#newApplicationsId" role="button"
       aria-expanded="false"
       aria-controls="collapseExample">
        Create new application
    </a>
    <div class="collapse" id="newApplicationsId">
        <div class="form-group">
            <form action="<c:url value="/controller?command=create_application"/>" method="post">
                <div class="form-row">
                    <div class="form-group col-md">
                        <input class="form-control" type="number" min="0" name="capacity" placeholder="capacity">
                    </div>
                    <div class="form-group col-md">
                        <select class="custom-select" name="type">
                            <option disabled selected>Select room type</option>
                            <option value="ECONOMY">Economy</option>
                            <option value="STANDARD">Standard</option>
                            <option value="LUXURY">Luxury</option>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="check-in">Check in</label>
                        <input class="form-control" name="checkInDate" type="date"
                               id="check-in">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="check-out">Check out</label>
                        <input class="form-control" name="checkOutDate" type="date"
                               id="check-out">
                    </div>
                </div>
                <div class="form-group">
                    <button class="btn btn-success" type="submit">Create</button>
                </div>
            </form>
        </div>
    </div>
</div>

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
            <tr>
                <c:if test="${application.status == 'LOOKING_FOR' or application.status == 'ACCEPT_WAITING'}">

                    <th scope="row">${counter}</th>
                    <c:choose>
                        <c:when test="${not empty application.room}">

                            <td><img class="img-thumbnail" src="${pageContext.request.contextPath}/img/${room.imgName}">
                            </td>
                            <td>${application.room.capacity}</td>
                            <td>${application.room.type}</td>
                            <td>${application.checkInDate}</td>
                            <td>${application.checkOutDate}</td>
                            <td>${application.status}</td>
                            <td>${application.totalPrice}$</td>
                        </c:when>
                        <c:otherwise>

                            <td>Wait for manager to pick you a room</td>
                            <td>${application.requirementCapacity}</td>
                            <td>${application.requirementType}</td>
                            <td>${application.checkInDate}</td>
                            <td>${application.checkOutDate}</td>
                            <td>${application.status}</td>
                            <td><c:if test="${not empty application.bill}">${application.bill.totalPrice}$</c:if>
                                <c:if test="${empty application.bill}">?</c:if></td>
                        </c:otherwise>
                    </c:choose>

                    <td>
                        <c:if test="${application.status == 'ACCEPT_WAITING'}">
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <form action="<c:url value="/controller?command=accept_application"/>" method="post">
                                        <button type="submit" class="btn btn-success">Accept</button>
                                        <input type="hidden" name="id" value="${application.id}">
                                    </form>
                                </div>
                                <div class="form-group col-md-6">
                                    <form action="<c:url value="/controller?command=reject_application"/>" method="post">
                                        <button type="submit" class="btn btn-warning">Reject</button>
                                        <input type="hidden" name="id" value="${application.id}">
                                    </form>
                                </div>
                            </div>
                        </c:if>
                        <form action="<c:url value="/controller?command=remove_application"/>" method="post">
                            <button type="submit" class="btn btn-danger">Remove</button>
                            <input type="hidden" name="id" value="${application.id}">
                        </form>
                    </td>
                    <c:set var="count" value="${counter + 1}"/>

                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
