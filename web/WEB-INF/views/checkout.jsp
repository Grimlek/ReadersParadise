<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" 
         import="java.time.format.TextStyle,java.util.Locale"%>

<fmt:setLocale value="${sessionScope.language}" scope="session" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Readers Paradise Checkout</title>
        <link rel="stylesheet" href="css/main.css">
        <c:if test="${!empty language}">
            <fmt:setLocale value="${language}" scope="session" />
        </c:if>
    </head>
    <body>
        <%@ include file="../jspf/header.jspf" %>
        <div class="sml-pad-top center content">
            <h1 id="checkout-header"><fmt:message key='checkoutHeader'/></h1>
            <p id="checkout-headline"><fmt:message key='instructions'/></p>
            <div class="checkout-container bgd-clr-white inline-block">
                <form id="checkout-form" action="purchase" method="post">
                    <ul id="errors" class="<c:if test="${!errorFlag}">hide</c:if>">
                        <c:if test="${!empty nameError}">
                            <li><label><fmt:message key='nameError'/></label></li>
                        </c:if>
                        <c:if test="${!empty emailError}">
                            <li><label><fmt:message key='emailError'/></label></li>
                        </c:if>
                        <c:if test="${!empty phoneError}">
                            <li><label><fmt:message key='phoneError'/></label></li>
                        </c:if>
                        <c:if test="${!empty addressError}">
                            <li><label><fmt:message key='addressError'/></label></li>
                        </c:if>
                        <c:if test="${!empty expirationeDateError}">
                            <li><label><fmt:message key='expirationDateError'/></label></li>
                        </c:if>
                        <c:if test="${!empty ccNumError}">
                            <li><label><fmt:message key='ccNumError'/></label></li>
                        </c:if>
                        <c:if test="${!empty cartError}">
                            <li><label><fmt:message key='cartError'/></label></li>
                        </c:if>
                    </ul>
                    <table>
                        <tbody>
                            <tr>
                                <td>
                                    <label for="name"><fmt:message key='name'/>: </label>
                                </td>
                                <td>
                                    <input id="name" name="name" type="text" data-msg-required="<fmt:message key='nameRequired'/>"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="address"><fmt:message key='address'/>: </label>
                                </td>
                                <td>
                                    <input id="address" name="address" type="text" data-msg-required="<fmt:message key='addressRequired'/>"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="email"><fmt:message key='email'/>: </label>
                                </td>
                                <td>
                                    <input type="text" id="email" name="email"/>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <label for="phone-num"><fmt:message key='phoneNumber'/>: </label>
                                </td>
                                <td>
                                    <input id="phone-num" name="phone-num" type="text" data-msg-required="<fmt:message key='phoneNumRequired'/>"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="cc-num"><fmt:message key='creditCardNumber'/>: </label>
                                </td>
                                <td>
                                    <input id="cc-num" name="cc-num" type="text" data-msg-required="<fmt:message key='ccNumRequired'/>"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="exp-date"><fmt:message key='expirationDate'/>: </label>
                                </td>
                                <td>
                                    <select class="drop-down" name="month" data-msg-required="<fmt:message key='expirationDateRequired'/>">
                                        <option disabled selected value></option>
                                        <c:forEach var="month" items="${months}">
                                            <option value="${month}">${month}</option>
                                        </c:forEach>
                                    </select>
                                    <select class="drop-down" name="year" data-msg-required="<fmt:message key='expirationDateRequired'/>">
                                        <option disabled selected value></option>
                                        <c:forEach varStatus="loop" begin="0" end="12">
                                            <c:set var="year" value="${currentYear + loop.index}" scope="page"/>
                                            <option value="${year}">${year}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <input class="width-4 blue-btn" type="submit" value="Purchase"/>
                </form>
            </div>
            <div class="order-summary-total bgd-clr-white inline-block">
                <div class="sml-pad-top xls-pad-bot">
                    <label>Subtotal: </label><span>$${cart.subtotal}</span>
                </div>
                <div class="xls-pad-top xls-pad-bot">
                    <label>Shipping Cost: </label><span>$${initParam.deliverySurcharge}</span>
                </div>
                <div class="xls-pad-top sml-pad-bot">
                    <label>Total: </label><span>$${cart.total}</span>
                </div>
            </div>
        </div>
        <%@ include file="../jspf/footer.jspf" %>
        <script type="text/javascript">
            var currentMonth = ${currentMonth};
            var currentYear = ${currentYear};
        </script>
        <%@ include file="../jspf/messages_js.jspf" %>
        <script src="js/app.js"></script>
    </body>
</html>