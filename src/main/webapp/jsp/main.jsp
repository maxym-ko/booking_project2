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
    <c:if test="${role == 'ADMIN'}">
        <%@ include file="../jspf/room/addRoom.jspf" %>
        <hr class="mt-2 mb-3"/>
    </c:if>

    <%@ include file="../jspf/sort.jspf" %>
    <div class="card-columns">
        <c:forEach items="${requestScope.rooms}" var="room">
            <c:if test="${room.status != 'UNAVAILABLE'}">
                <div class="card border-dark bg-light mb-3">
                    <c:if test="${not empty room.imgName}">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/img/${room.imgName}">
                    </c:if>

                    <div class="card-body">
                        <c:if test="${role != 'ADMIN'}">
                            <p class="card-text"><fmt:message key="room.capacity"/>: ${room.capacity}</p>
                            <p class="card-text"><fmt:message key="room.type"/>: <em>${room.type}</em></p>
                            <p class="card-text"><fmt:message key="room.price"/>: <strong>${room.price}$</strong> (<fmt:message key="room.price.per_night"/>)</p>
                        </c:if>
                        <c:if test="${role == 'USER'}">
                            <%@ include file="../jspf/room/bookRoom.jspf" %>
                        </c:if>
                        <c:if test="${role == 'ADMIN'}">
                            <form action="<c:url value="/controller?command=change_room"/>" method="post">
                                <input type="hidden" name="id" value="${room.id}">
                                <div class="form-group row">
                                    <label for="capacity" class="col-sm-6 col-form-label"><fmt:message key="room.capacity"/>: </label>
                                    <div class="col-sm-6">
                                        <input class="form-control" id="capacity" type="number" min="1"
                                               name="capacity"
                                               value="${room.capacity}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="type" class="col-sm-6 col-form-label"><fmt:message key="room.type"/>: </label>
                                    <div class="col-sm-6">
                                        <select class="custom-select" id="type" name="type">
                                            <option <c:if test="${room.type == 'ECONOMY'}"> selected </c:if>
                                                    value="ECONOMY"><fmt:message key="room.type.economy"/>
                                            </option>
                                            <option <c:if test="${room.type == 'STANDARD'}"> selected </c:if>
                                                    value="STANDARD"><fmt:message key="room.type.standard"/>
                                            </option>
                                            <option <c:if test="${room.type == 'LUXURY'}"> selected </c:if>
                                                    value="LUXURY"><fmt:message key="room.type.luxury"/>
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="price" class="col-sm-6 col-form-label"><fmt:message key="room.price"/> ($ <fmt:message key="room.price.per_night"/>): </label>
                                    <div class="col-sm-6">
                                        <input class="form-control" id="price" type="number" min="0" name="price"
                                               value="${room.price}">
                                    </div>
                                </div>
                                <div class="form-row ml-1">
                                    <div class="form-group mr-2">
                                        <form action="<c:url value="/controller?command=change_room"/>" method="post">
                                            <button class="btn btn-secondary" type="submit"><fmt:message key="room.change"/></button>
                                        </form>
                                    </div>
                                </div>
                            </form>

                            <div class="form-row ml-1">
                                <div class="form-group mr-2">
                                    <form action="<c:url value="/controller?command=remove_room"/>" method="post">
                                        <button class="btn btn-danger" type="submit"><fmt:message key="room.remove"/></button>
                                        <input type="hidden" name="id" value="${room.id}">
                                    </form>
                                </div>
                            </div>

                        </c:if>
                    </div>

                </div>
            </c:if>
        </c:forEach>
    </div>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
