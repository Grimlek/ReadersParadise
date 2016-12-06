<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" 
         import="java.time.format.TextStyle,java.util.Locale"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Readers Paradise Checkout</title>
        <link rel="stylesheet" href="css/main.css">
    </head>
    <body>
        <%@include file="../jspf/header.jspf" %>
        <div class="sml-pad-top center">
            <h1 id="checkout-header">Checkout</h1>
            <p id="checkout-headline">In order to purchase the items in the shopping cart, please provide the following information.</p>
            <div class="checkout-container bgd-clr-white inline-block">
                <form id="checkout-form" action="purchase" method="post">
                    <ul id="errors" class="<c:if test="${!errorFlag}">hide</c:if>">
                        <c:if test="${!empty nameError}">
                            <li><label>Please enter a valid name.</label></li>
                        </c:if>
                        <c:if test="${!empty emailError}">
                            <li><label>Please enter a valid email address.</label></li>
                        </c:if>
                        <c:if test="${!empty phoneError}">
                            <li><label>Please enter a valid phone number.</label></li>
                        </c:if>
                        <c:if test="${!empty addressError}">
                            <li><label>Please enter a valid address.</label></li>
                        </c:if>
                        <c:if test="${!empty expirationeDateError}">
                            <li><label>Please enter a valid expiration date.</label></li>
                        </c:if>
                        <c:if test="${!empty ccNumError}">
                            <li><label>Please enter a valid credit card number.</label></li>
                        </c:if>
                        <c:if test="${!empty cartError}">
                            <li><label>The shopping cart doesn't contain any items.</label></li>
                        </c:if>
                    </ul>
                    <table>
                        <tbody>
                            <tr>
                                <td>
                                    <label for="name">Name: </label>
                                </td>
                                <td>
                                    <input id="name" name="name" type="text" data-msg-required="Please enter your name."/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="address">Address: </label>
                                </td>
                                <td>
                                    <input id="address" name="address" type="text" data-msg-required="Please enter your address."/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="email">Email: </label>
                                </td>
                                <td>
                                    <input type="email" id="email" name="email"/>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <label for="phone-num">Phone Number: </label>
                                </td>
                                <td>
                                    <input id="phone-num" name="phone-num" type="text" data-msg-required="Please enter a phone number."/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="cc-num">Card Number: </label>
                                </td>
                                <td>
                                    <input id="cc-num" name="cc-num" type="text" data-msg-required="Please enter a credit card number."/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="exp-date">Expiration Date: </label>
                                </td>
                                <td>
                                    <select class="drop-down" name="month" data-msg-required="Please enter an expiration date.">
                                        <option disabled selected value></option>
                                        <c:forEach var="month" items="${months}">
                                            <option value="${month}">${month}</option>
                                        </c:forEach>
                                    </select>
                                    <select class="drop-down" name="year" data-msg-required="Please enter an expiration date.">
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
        <jsp:include page="../jspf/footer.jspf" />
        <script type="text/javascript">
            var currentMonth = ${currentMonth};
            var currentYear = ${currentYear};
        </script>
        <script src="js/app.js"></script>
    </body>
</html>
