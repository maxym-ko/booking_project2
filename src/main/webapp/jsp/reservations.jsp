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
            <th scope="col"><fmt:message key="applications.capacity"/></th>
            <th scope="col"><fmt:message key="applications.type"/></th>
            <th scope="col"><fmt:message key="applications.check_in"/></th>
            <th scope="col"><fmt:message key="applications.check_out"/></th>
            <th scope="col"><fmt:message key="applications.price"/></th>
            <th scope="col"><fmt:message key="applications.action"/></th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${requestScope.reservations}" var="reservation" varStatus="loop">
            <tr>
                <th scope="row">${loop.index + 1}</th>
                <td><img class="img-thumbnail" src="${pageContext.request.contextPath}/img/${reservation.room.imgName}">
                </td>
                <td>${reservation.room.capacity}</td>
                <td>${reservation.room.type}</td>
                <td>${reservation.checkInDate}</td>
                <td>${reservation.checkOutDate}</td>
                <td>${reservation.totalPrice}$</td>
                <td>
                    <form action="<c:url value="/controller?command=confirm_payment"/>" method="post">
                        <c:set var="paymentWaiting" value="${reservation.status == 'PAYMENT_WAITING'}"/>
                        <c:if test="${paymentWaiting}">
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="receiptId"><fmt:message key="reservations.action.payment.receipt_id"/>: </label>
                                    <input class="form-control" name="receiptId" type="text"
                                           id="receiptId">
                                </div>
                            </div>
                        </c:if>
                        <button
                                <c:if test="${!paymentWaiting}">disabled</c:if> type="submit" class="btn btn-success">
                            <c:choose>
                                <c:when test="${paymentWaiting}">
                                    <fmt:message key="reservations.action.payment.confirm"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="reservations.action.payment.confirmed"/>
                                </c:otherwise>
                            </c:choose>
                        </button>
                        <input type="hidden" name="reservationId" value="${reservation.id}">
                        <input type="hidden" name="billId" value="${reservation.bill.id}">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="../jspf/page/footer.jspf"/>
</body>
</html>
