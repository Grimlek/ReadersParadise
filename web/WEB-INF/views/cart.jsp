<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<fmt:setLocale value="${sessionScope.language}" scope="session" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Readers Paradise Shopping Cart</title>
        <link rel="stylesheet" type="text/css" href="../css/main.css" />
        <c:if test="${!empty language}">
            <fmt:setLocale value="${language}" scope="session" />
        </c:if>
    </head>
    <body>
        <%@ include file="../jspf/header.jspf" %>
        <main id="cart-container" class="sml-pad-top center">
            <c:choose>
                <c:when test="${empty cart || cart.numberOfItems == 0}">
                    <div id="no-items-container" class="inline-block center bgd-clr-white width-7">
                        <h2 class="xls-pad-bot xls-pad-top">
                            <fmt:message key='noItems'/>
                        </h2>
                        <a id="continue-shopping" class="blue-btn txt-dec-none press-effect" 
                           title="Continue Shopping" href="../category?category=${selectedCategory.name}">
                            <fmt:message key='continueShopping'/>
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="cart-actions-container">
                        <a id="continue-shopping" class="blue-btn txt-dec-none press-effect" 
                           title="Continue Shopping" href="../category?category=${selectedCategory.name}">
                            <fmt:message key='continueShopping'/>
                        </a>
                        <a id="proceed-to-checkout" class="blue-btn txt-dec-none press-effect"
                           title="Proceed to Checkout" href="../checkout">
                            <fmt:message key='proceedToCheckout'/>
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
                                            <span class="item-price"><fmt:message key='price'/>: $${item.book.price}</span>
                                        </div>
                                        <div class="item-qty-container">
                                            <p id="${item.book.id}-item-qty" class="item-qty xls-pad-bot"><fmt:message key='quantity'/>: ${item.quantity}</p>
                                            <form class="increment-form" action="increment" method="post">
                                                <input type="hidden" name="bookId" value="${item.book.id}" />
                                                <button class="increment remove-default-button" type="submit">
                                                    <img class="xls-pad-top press-effect" src="${initParam.imagePath}plus-button.png" 
                                                         alt="Increment Quantity" />
                                                </button>
                                            </form>
                                            <form class="decrement-form <c:if test="${item.quantity < 2}">hide</c:if>" 
                                                  action="decrement" method="post">
                                                <input type="hidden" name="bookId" value="${item.book.id}" />
                                                <button class="decrement remove-default-button" type="submit">
                                                    <img class="xls-pad-top press-effect" src="${initParam.imagePath}minus-button.png" 
                                                         alt="Decrement Quantity" />
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                    <form class="remove-book" action="remove" method="post">
                                        <input type="hidden" id="bookId" name="bookId" value="${item.book.id}" />
                                        <button class="remove remove-default-button" type="submit" name="action" value="remove">
                                            <img class="xls-pad-top press-effect" src="${initParam.imagePath}delete-button.png" 
                                                 alt="Remove Book" />
                                        </button>
                                    </form>
                                </div>
                            </c:forEach>
                            <p id="cart-subtotal"><fmt:message key='subtotal'/> (${cart.numberOfItems} <fmt:message key='items'/>): $<span id="subtotal">${cart.subtotal}</span></p>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>
        <%@ include file="../jspf/footer.jspf" %>
        <%@ include file="../jspf/messages_js.jspf" %>
        <script src="/ReadersParadise/js/app.js"></script>
    </body>
</html>