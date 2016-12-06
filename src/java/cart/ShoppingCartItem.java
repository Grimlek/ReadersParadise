/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cart;

import entity.Book;

/**
 *
 * @author tgiunipero
 */
public class ShoppingCartItem {

    private Book book;
    private short quantity;

    public ShoppingCartItem(Book book) {
        this.book = book;
        quantity = 1;
    }

    public Book getBook() {
        return book;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    public double getTotal() {
        double amount = 0;
        amount = (this.getQuantity() * book.getPrice().doubleValue());
        return amount;
    }

}