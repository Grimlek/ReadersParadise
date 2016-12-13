<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<fmt:setLocale value="${sessionScope.language}" scope="session" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Readers Paradise</title>
        <link rel="stylesheet" type="text/css" href="css/main.css">
    </head>
    <body>
        <%@ include file="../jspf/header.jspf" %>
        <main class="inline-block xls-pad-bot">
            <section id="welcome" class="left bgd-clr-white center sml-pad-top width-4">
                <h2 class="width-8"><fmt:message key='welcome'/></h2>
                <div>
                    <img src="${initParam.imagePath}welcome-image.png" alt="Welcome Book Image"/>
                    <span class="inline-block"><fmt:message key='introMessage'/></span>
                </div>
                <p class="width-8"><fmt:message key='introText'/></p>
            </section>
            <section id="featured-categories" class="center right bgd-clr-white sml-pad-top">
                <h2 class="xls-pad-bot"><fmt:message key='containerTitle'/></h2>
                <c:forEach var="category" items="${featuredCategories}" varStatus="iter">
                    <div class="category inline-block xls-pad-bot xls-pad-top sml-pad-left sml-pad-right">
                        <a class="txt-dec-none" title="${category[1]} Category" 
                           href="category?category=${category[1]}">
                            <img src="${initParam.categoryImagePath}${category[1]}.png" 
                                 alt="${category[1]}"/>
                            <h4 class="txt-dec-none"><fmt:message key='${category[1]}'/></h4>
                        </a>
                    </div>
                </c:forEach>
            </section>
        </main>
        <%@ include file="../jspf/footer.jspf" %>
    </body>
</html>
