<%-- 
    Document   : confirmation
    Created on : Sep 12, 2016, 10:05:56 AM
    Author     : csexton
--%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Readers Paradise Confirmation</title>
        <link rel="stylesheet" href="css/main.css">
    </head>
    <body>
        <%@include file="../jspf/header.jspf" %>
        <div id="confirmation-container">
            <div class="order-summary">
                <table class="bgd-clr-white">
                    <caption>Order Summary</caption>
                    <tbody>
                        <tr>
                            <td>
                                <label>Confirmation Number: </label>
                            </td>
                            <td>
                                <label>${orderRecord.confirmationNumber}</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Shipping Cost: </label>
                            </td>
                            <td>
                                <label>${initParam.deliverySurcharge}</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Order Total: </label>
                            </td>
                            <td>
                                <label>${orderRecord.amount}</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Order Date: </label>
                            </td>
                            <td>
                                <label>
                                    <fmt:formatDate value="${orderRecord.dateCreated}" type="both" pattern="MM/dd/yyyy"/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Credit Card Number: </label>
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
                            <td>
                                <label>Expiration Date: </label>
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
                    <caption>Ordered Books</caption>
                    <tbody>
                        <tr>
                            <td>Title</td>
                            <td>Price</td>
                            <td>Qty</td>
                        </tr>
                        <c:forEach var="orderedBook" items="${orderedBooks}" varStatus="iter">
                            <tr>
                                <td>
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
        <jsp:include page="../jspf/footer.jspf" />
    </body>
</html>
