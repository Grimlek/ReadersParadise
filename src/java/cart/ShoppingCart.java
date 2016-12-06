/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import entity.Book;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author Charles Sexton
 */
public class ShoppingCart {

    private List<ShoppingCartItem> items;
    private BigDecimal total;

    public ShoppingCart() {
        items = new ArrayList<>();
        total = new BigDecimal(0.00);
    }

    /**
     * Adds a <code>ShoppingCartItem</code> to the <code>ShoppingCart</code>'s
     * <code>items</code> list. If item of the specified <code>book</code>
     * already exists in shopping cart list, the quantity of that item is
     * incremented.
     *
     * @param book the <code>Book</code> that defines the type of shopping cart
     * item
     * @see ShoppingCartItem
     */
    public synchronized void addItem(Book book) {

        List<ShoppingCartItem> matchingItems = getMatchingBooks(book);

        if (matchingItems.isEmpty()) {
            ShoppingCartItem item = new ShoppingCartItem(book);
            items.add(item);
        } else {
            ShoppingCartItem item = matchingItems.get(0);
            item.incrementQuantity();
        }
    }

    /**
     * Updates the <code>ShoppingCartItem</code> of the specified
     * <code>book</code> to the specified quantity. If '<code>0</code>' is the
     * given quantity, the <code>ShoppingCartItem</code> is removed from the
     * <code>ShoppingCart</code>'s <code>items</code> list.
     *
     * @param book the <code>Book</code> that defines the type of shopping cart
     * item
     * @param quantityString the number which the <code>ShoppingCartItem</code>
     * is updated to
     * @see ShoppingCartItem
     */
    public synchronized void update(Book book, String quantityString) {

        short quantity = Short.parseShort(quantityString);
        if (quantity < 0 || quantity >= 10) {
            return;
        }

        List<ShoppingCartItem> matchingItems = getMatchingBooks(book);
        if (matchingItems.isEmpty()) {
            return;
        }

        ShoppingCartItem item = matchingItems.get(0);
        if (quantity == 0) {
            items.remove(item);
        } else {
            item.setQuantity(quantity);
        }
    }

    /**
     * Returns the list of <code>ShoppingCartItems</code>.
     *
     * @return the <code>items</code> list
     * @see ShoppingCartItem
     */
    public synchronized List<ShoppingCartItem> getItems() {

        return items;
    }

    /**
     * Returns the sum of quantities for all items maintained in shopping cart
     * <code>items</code> list.
     *
     * @return the number of items in shopping cart
     * @see ShoppingCartItem
     */
    public synchronized int getNumberOfItems() {

        return items.stream()
                .mapToInt(ShoppingCartItem::getQuantity)
                .sum();
    }

    /**
     * Increments the selected shopping cart item
     *
     * @param book the book to increment
     * @see ShoppingCartItem
     */
    public synchronized void increment(Book book) {
        List<ShoppingCartItem> matchingItems = getMatchingBooks(book);
        if (matchingItems.isEmpty()) {
            return;
        }

        ShoppingCartItem item = matchingItems.get(0);
        if (item.getQuantity() < 10) {
            item.incrementQuantity();
        }
    }

    /**
     * Decrements the selected shopping cart item
     *
     * @param book the book to decrement
     * @see ShoppingCartItem
     */
    public synchronized void decrement(Book book) {
        List<ShoppingCartItem> matchingItems = getMatchingBooks(book);
        if (matchingItems.isEmpty()) {
            return;
        }

        ShoppingCartItem item = matchingItems.get(0);
        if (item.getQuantity() == 1) {
            items.remove(item);
        } else {
            item.decrementQuantity();
        }
    }

    /**
     * Returns the sum of the product price multiplied by the quantity for all
     * items in shopping cart list. This is the total cost excluding the
     * surcharge.
     *
     * @return the cost of all items times their quantities rounded to the 
     * nearest hundredth place
     * 
     * @see ShoppingCartItem
     */
    public synchronized BigDecimal getSubtotal() {
        double subtotal = items.stream()
                .mapToDouble(ShoppingCartItem::getTotal)
                .sum();
        
        return new BigDecimal(subtotal)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Calculates the total cost of the order. This method adds the subtotal to
     * the designated surcharge and sets the <code>total</code> instance
     * variable with the result.
     *
     * @param surcharge the designated surcharge for all orders
     * @see ShoppingCartItem
     */
    public synchronized void calculateTotal(String surcharge) {

        BigDecimal amount = new BigDecimal(0.00);

        // cast surcharge as double
        BigDecimal s = new BigDecimal(surcharge);

        amount = this.getSubtotal();
        amount = amount.add(s);

        total = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Returns the total cost of the order for the given
     * <code>ShoppingCart</code> instance.
     *
     * @return the cost of all items times their quantities plus surcharge
     */
    public synchronized BigDecimal getTotal() {

        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Empties the shopping cart. All items are removed from the shopping cart
     * <code>items</code> list, <code>numberOfItems</code> and
     * <code>total</code> are reset to '<code>0</code>'.
     *
     * @see ShoppingCartItem
     */
    public synchronized void clear() {
        items.clear();
        total = new BigDecimal(0.00);
    }

    /**
     * Returns A list of matching books Helper method to find the selected book
     * within the shopping cart list items.
     * 
     * @return the <code>matchingBooks</code> list
     * @see ShoppingCartItem
     */
    private synchronized List<ShoppingCartItem> getMatchingBooks(Book book) {
        return items.stream()
                .filter(item -> Objects.equals(item.getBook().getId(), book.getId()))
                .collect(Collectors.toList());
    }
}
