<%-- 
    Document   : confirmation
    Created on : Sep 12, 2016, 10:05:56 AM
    Author     : csexton
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Readers Paradise Confirmation</title>
        <link rel="stylesheet" href="css/main.css">
    </head>
    <body>
        <%@ include file="../jspf/header.jspf" %>
        <div id="confirmation-container">
            <div class="order-summary">
                <table class="bgd-clr-white">
                    <caption><fmt:message key='tableHeaderSummary'/></caption>
                    <tbody>
                        <tr>
                            <td class="right-label">
                                <label><fmt:message key='confirmationNumber'/>: </label>
                            </td>
                            <td>
                                <label>${orderRecord.confirmationNumber}</label>
                            </td>
                        </tr>
                        <tr>
                            <td class="right-label">
                                <label><fmt:message key='shippingCost'/>: </label>
                            </td>
                            <td>
                                <label>${initParam.deliverySurcharge}</label>
                            </td>
                        </tr>
                        <tr>
                            <td class="right-label">
                                <label><fmt:message key='orderTotal'/>: </label>
                            </td>
                            <td>
                                <label>${orderRecord.amount}</label>
                            </td>
                        </tr>
                        <tr>
                            <td class="right-label">
                                <label><fmt:message key='orderDate'/>: </label>
                            </td>
                            <td>
                                <label>
                                    <fmt:formatDate value="${orderRecord.dateCreated}" type="both" pattern="MM/dd/yyyy"/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td class="right-label">
                                <label><fmt:message key='creditCardNumber'/>: </label>
                            </td>
                            <td>
                                <label>
                                    <c:out value="${fn:substring(customer.ccNumber, 
                                                    fn:length(customer.ccNumber) - 4, 
                                                    fn:length(customer.ccNumber))}" />
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td class="right-label">
                                <label><fmt:message key='expirationDate'/>: </label>
                            </td>
                            <td>
                                <label>
                                    <c:out value="${orderRecord.customer.ccExpDate}" />
                                </label>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="ordered-books">
                <table class="bgd-clr-white">
                    <caption><fmt:message key='tableHeaderBooks'/></caption>
                    <tbody>
                        <tr>
                            <td><fmt:message key='title'/></td>
                            <td><fmt:message key='price'/></td>
                            <td><fmt:message key='abbrQty'/></td>
                        </tr>
                        <c:forEach var="orderedBook" items="${orderedBooks}" varStatus="iter">
                            <tr>
                                <td class="right-label">
                                    <label>${books[iter.index].title}</label>
                                </td>
                                <td>
                                    <label>${books[iter.index].price}</label>
                                </td>
                                <td>
                                    <label>${orderedBook.quantity}</label>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <%@ include file="../jspf/footer.jspf" %>
    </body>
</html>
