<%-- 
    Document   : cart
    Created on : Sep 12, 2016, 10:04:53 AM
    Author     : csexton
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Readers Paradise Shopping Cart</title>
        <link rel="stylesheet" type="text/css" href="css/main.css">
    </head>
    <body>
        <%@include file="../jspf/header.jspf" %>
        <main id="cart-container" class="sml-pad-top center">
            <c:choose>
                <c:when test="${empty cart || cart.numberOfItems == 0}">
                    <div id="no-items-container" class="inline-block center bgd-clr-white width-7">
                        <h2 class="xls-pad-bot xls-pad-top">
                            You don't contain any items within your cart
                        </h2>
                        <a id="continue-shopping" class="blue-btn txt-dec-none press-effect" 
                           title="Continue Shopping" href="category?category=${selectedCategory.name}">
                            Continue Shopping
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="cart-actions-container">
                        <a id="continue-shopping" class="blue-btn txt-dec-none press-effect" 
                           title="Continue Shopping" href="category?category=${selectedCategory.name}">
                            Continue Shopping
                        </a>
                        <a id="proceed-to-checkout" class="blue-btn txt-dec-none press-effect"
                           title="Proceed to Checkout" href="checkout">
                            Proceed to Checkout
                        </a>
                    </div>
                    <div id="items-container" class="inline-block center bgd-clr-white width-7">
                        <div id="cart-content">
                            <c:forEach var="item" items="${cart.items}">
                                <div class="item-content">
                                    <div class="left-item-content">
                                        <a class="xls-pad-left press-effect" href="#" 
                                           title="${item.book.title}">
                                            <img src="${initParam.productImagePath}${item.book.title.toLowerCase()}.png" 
                                                 alt="${item.book.title}" height="150" width="100" />
                                        </a>
                                        <div class="item-description width-7">
                                            <div>
                                                <a href="#" class="txt-dec-none press-effect" 
                                                   title="${item.book.title}">
                                                    <h6 class="item-title xls-pad-left">${item.book.title}</h6>
                                                </a>
                                            </div>
                                            <div class="item-author-container xls-pad-top xls-pad-left">
                                                <span>by ${item.book.author}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="purchase-details">
                                        <div class="item-price-container">
                                            <span class="item-price">Price: $${item.book.price}</span>
                                        </div>
                                        <div class="item-qty-container">
                                            <form id="update-quantity" action="updateCart" method="post">
                                                <input type="hidden" name="bookId" value="${item.book.id}">
                                                <c:choose>
                                                    <c:when test="${item.quantity > 1}">
                                                        <p id="${item.book.id}-item-qty" class="item-qty xls-pad-bot">Quantity: ${item.quantity}</p>
                                                        <button class="decrement" type="submit">
                                                            <img class="xls-pad-top press-effect" src="img/minus-button.png" 
                                                                 alt="Increment Quantity" />
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p id="${item.book.id}-item-qty" class="hide item-qty xls-pad-bot">Quantity: ${item.quantity}</p>
                                                        <button class="decrement hide" type="submit">
                                                            <img class="xls-pad-top press-effect" src="img/minus-button.png" 
                                                                 alt="Increment Quantity" />
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                                <button class="increment remove-default-button" type="submit" name="action" value="increment">
                                                    <img class="xls-pad-top press-effect" src="img/plus-button.png" 
                                                         alt="Increment Quantity" />
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                    <form id="remove-book" action="updateCart" method="post">
                                        <input type="hidden" name="bookId" value="${item.book.id}">
                                        <button class="remove remove-default-button" type="submit" name="action" value="remove">
                                            <img class="xls-pad-top press-effect" src="img/delete-button.png" 
                                                 alt="Remove Book" />
                                        </button>
                                    </form>
                                </div>
                            </c:forEach>
                            <p id="cart-subtotal">Subtotal (${cart.numberOfItems} items): $${cart.subtotal}</p>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>
        <jsp:include page="../jspf/footer.jspf" />
    </body>
</html>