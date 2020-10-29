<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>

<c:set var="title" value="Find room" scope="page"/>
<%@ include file="../jspf/page/head.jspf" %>

<body>
<%@ include file="../jspf/page/navbar.jspf" %>

<div class="container mt-5">
    <div class="card-columns">
        <c:forEach items="${rooms}" var="room">
            <c:if test="${room.status != 'UNAVAILABLE'}">
                <div class="card border-dark bg-light mb-3">
                <c:if test="${not empty room.imgName}">
                    <img class="card-img-top" src="${pageContext.request.contextPath}/img/${room.imgName}"></c:if>

                <div class="card-body">
                    <p class="card-text">Capacity: ${room.capacity}</p>
                    <p class="card-text">Type: <em>${room.type}</em></p>
                    <p class="card-text">Price: <strong>${room.price}$</strong>(per night)</p>

                    <form action="<c:url value="/controller?command=select_room&id=${param.id}"/>" method="post">
                        <button class="btn btn-primary" type="submit">Select</button>
                        <input type="hidden" name="roomId" value="${room.id}">
                    </form>

                </div>
            </c:if>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
