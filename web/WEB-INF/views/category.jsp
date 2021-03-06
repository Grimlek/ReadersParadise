<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<fmt:setLocale value="${sessionScope.language}" scope="session" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Readers Paradise</title>
        <link rel="stylesheet" type="text/css" href="css/main.css" />
        <c:if test="${!empty language}">
            <fmt:setLocale value="${language}" scope="session" />
        </c:if>
    </head>
    <body>
        <%@ include file="../jspf/header.jspf" %>
        <aside id="sidebar" class="left center bgd-clr-white">
            <h1>Categories</h1>
            <ul id="category-list">
                <c:forEach var="category" items="${categories}">
                    <c:choose>
                        <c:when test='${category.name.toLowerCase() == selectedCategory.name.toLowerCase()}'>
                            <li class="xls-pad-top">
                                <a href="category?category=${category.name}" 
                                   class="press-effect usr-select-no selected txt-dec-none" 
                                   type="button" role="button" title="${category.name}">
                                    <fmt:message key='${category.name}'/>
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="xls-pad-top">
                                <a href= "category?category=${category.name}" 
                                   class="press-effect usr-select-no txt-dec-none" 
                                   type="button" role="button" title="${category.name}">
                                    <fmt:message key='${category.name}'/>
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </aside>
        <main id="category-content" class="inline-block xls-pad-bot">
            <section class="inline-block center bgd-clr-white">
                <h1 id="category-title"><fmt:message key='${selectedCategory.name}'/></h1>
                <c:forEach var="book" items="${categoryBooks}">
                    <div class="prod-container inline-block">
                        <a class="xls-pad-left" href="product?id=${book.id}" title="${book.title}">
                            <img src="${initParam.productImagePath}${book.title.toLowerCase()}.png" 
                                 alt="${book.title}"/>
                        </a>
                        <div class="prod-details xls-pad-left">
                            <a id="title" class="xls-pad-top" href="product?id=${book.id}" title="${book.title}">
                                <c:choose>
                                    <c:when test="${fn:length(book.title) gt 30}">
                                        <c:set var="words" value="${fn:split(book.title, ' ')}" />
                                        <c:forEach var="word" items="${words}">
                                            <c:choose>
                                                <c:when test="${word == words[fn:length(words) / 2]}">
                                                    ${word} <br /> 
                                                </c:when>
                                                <c:otherwise>
                                                    ${word} 
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:when>    
                                    <c:otherwise>
                                        ${book.title}
                                    </c:otherwise>
                                </c:choose>
                            </a>
                            <div>
                                <p>by ${book.author}</p>
                                <p id="price"><fmt:message key='price'/>: \$${book.price}</p>
                            </div>
                            <div>
                                <img src="${initParam.starImagePath}four-and-half-stars.png" 
                                     alt="Review Stars" />
                            </div>
                            <form class="add-to-cart-form inline" action="cart/add" method="post">
                                <input type="hidden" name="bookId" value="${book.id}" />
                                <button id="add-to-cart" 
                                   class="inline-block press-effect drk-red-btn usr-select-no center" 
                                   type="submit" name="action" value="addToCart"
                                   onmousedown="return false;" onselectstart="return false;">
                                    <fmt:message key='addToCart'/>
                                </button>
                            </form>
                            <c:choose>
                                <c:when test="${book.isPublic == true}">
                                    <a id="read-now" class="xls-pad-left txt-dec-none press-effect" 
                                       title="Read Now" role="button" href="#">
                                        <img src="${initParam.imagePath}read-now.png" alt="Read Now">
                                    </a>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </section>
        </main>
        <%@ include file="../jspf/footer.jspf" %>
        <%@ include file="../jspf/messages_js.jspf" %>
        <script src="/ReadersParadise/js/app.js"></script>
    </body>
</html>
