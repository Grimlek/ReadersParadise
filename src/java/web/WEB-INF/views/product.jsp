<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Readers Paradise Product</title>
        <link rel="stylesheet" type="text/css" href="css/main.css">
    </head>
    <body>
        <%@include file="../jspf/header.jspf" %>
        <main class="inline-block">
            <section id="product" class="bgd-clr-white sml-pad-bot sml-pad-top">
                <div class="inline-block sml-pad-left">
                    <img class="product-image" src="images/products/book1.png" alt="Product Image" />
                </div>
                <div class="sml-pad-left inline-block xls-pad-left align-top">
                    <h1>Harry Potter and The Sorcerers Stone</h1>
                    <div class="xls-pad-top">
                        <span>by J. K. Rowling</span>
                        <a title="Read Now" role="button" class="right blue-btn press-effect usr-select-no">
                            Read Now
                        </a>
                    </div>
                    <div class="sml-pad-top">
                        <span>Price: $7.00</span>
                    </div>
                    <div class="sml-pad-top">
                        <img src="images/stars/five_stars.png" alt="Review Stars" />
                        <span class="align-top xls-pad-left">107 Reviews</span>
                    </div>
                    <a title="Add to Cart" role="button" id="add-to-cart" 
                       class="inline-block press-effect drk-red-btn usr-select-no">
                        Add to Cart
                    </a>
                </div>
            </section>
            <section id="write-review" class="bgd-clr-white xls-pad-bot xls-pad-top">
                <form onsubmit="">
                    <div class="xls-pad-bot">
                        <h2 class="inline xls-pad-right">Write a Review</h2>
                        <img class="xls-pad-left" src="images/stars/no_stars.png" alt="Review Stars" />
                    </div>
                    <textarea rows="8" cols="60" placeholder="Review"></textarea>
                    <input class="blue-btn" type="submit" />
                </form>
            </section>
            <section id="reviews" class="bgd-clr-white xls-pad-top xls-pad-bot">
                <div class="sml-pad-left sml-pad-right">
                    <h2>Customer Reviews</h2>
                    <div id="customer-review" class="xls-pad-top">
                        <div class="xls-pad-left xls-pad-right">
                            <span>cust_user_1234</span>
                            <img class="sml-pad-left" src="images/stars/four_and_half_stars.png" />
                            <p>One of the best books I have ever bought. Definitely a must read
                                if you love fantasy.</p>
                        </div>
                    </div>
                    <div id="customer-review" class="xls-pad-top">
                        <div class="xls-pad-left xls-pad-right">
                            <span>book_worm_987</span>
                            <img class="sml-pad-left" src="images/stars/four_and_half_stars.png" />
                            <p>One of the best books I have ever bought. Definitely a must read
                                if you love fantasy.</p>
                        </div>
                    </div>
                </div>
                <a title="Read More" role="button" class="blue-btn press-effect center usr-select-no">
                    Read More
                </a>
            </section>
        </main>
        <%@include file="../jspf/footer.jspf" %>
    </body>
</html>
