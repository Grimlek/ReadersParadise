package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.Book;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderedBook;
import entity.OrderedBookPK;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

/**
 *
 * @author csexton
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrderManager {
    
    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;
    
    @Resource
    private SessionContext context;
    
    @EJB
    private CustomerOrderFacade customerOrderFacade;
    
    @EJB
    private BookFacade bookFacade;
    
    @EJB
    private OrderedBookFacade orderedBookFacade;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int placeOrder(String name, String email, String phone, String address, YearMonth expirationDate, String ccNumber, ShoppingCart cart) {
        try {
            Customer customer = addCustomer(name, email, phone, address, expirationDate, ccNumber);
            CustomerOrder order = addOrder(customer, cart);
            addOrderedItems(order, cart);
            return order.getId();
        }
        catch(Exception e) {
            context.setRollbackOnly();
            return 0;
        } 
    }
    
    private CustomerOrder addOrder(Customer customer, ShoppingCart cart) {

        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setAmount(cart.getTotal());
        order.setDateCreated(new Date());
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmationNumber(i);
        em.persist(order);
        return order;
    }
    
    private void addOrderedItems(CustomerOrder order, ShoppingCart cart) {
        em.flush();
        List<ShoppingCartItem> items = cart.getItems();

        for (ShoppingCartItem scItem : items) {

            int bookId = scItem.getBook().getId();
            OrderedBookPK orderedBookPK = new OrderedBookPK();
            orderedBookPK.setCustomerOrderId(order.getId());
            orderedBookPK.setBookId(bookId);
            OrderedBook orderedItem = new OrderedBook(orderedBookPK);
            orderedItem.setQuantity(scItem.getQuantity());
            em.persist(orderedItem);
        }
    }
    
    public Map getOrderDetails(int orderId) {

        Map orderMap = new HashMap();
        CustomerOrder order = customerOrderFacade.find(orderId);
        Customer customer = order.getCustomer();
        List<OrderedBook> orderedBooks = orderedBookFacade.findByOrderId(orderId);
        List<Book> books = new ArrayList<>();

        for (OrderedBook op : orderedBooks) {
            Book book = (Book) bookFacade.find(op.getOrderedBookPK().getBookId());
            books.add(book);
        }

        orderMap.put("orderRecord", order);
        orderMap.put("customer", customer);
        orderMap.put("orderedBooks", orderedBooks);
        orderMap.put("books", books);
        return orderMap;
    }
    
    private Customer addCustomer(String name, String email, String phone, String address, 
            YearMonth expirationDate, String ccNumber)
    {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCcExpDate(expirationDate);
        customer.setCcNumber(ccNumber);
        em.persist(customer);

        return customer;
    }
    
}
