package uk.ac.ncl.pcorder.model;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a single PC order placed by a customer.
 * <p>
 * Each order stores the customer's details, the chosen PC model,
 * the credit card used for payment, the date when the order was created,
 * and the current status of the order (PLACED, FULFILLED, or CANCELLED).
 * </p>
 * <p>
 * This class is responsible only for handling one individual order —
 * it doesn’t manage multiple orders. That is handled by {@link OrderManager}.
 * </p>
 */
public class Order {

    /** The customer who placed the order. */
    private final Customer customer;

    /** The PC model that was ordered. */
    private final PCModel model;

    /** The credit card used for payment. */
    private final CreditCard card;

    /** The date and time when the order was created. */
    private final Date datePlaced;

    /** The current status of the order (PLACED, FULFILLED, or CANCELLED). */
    private OrderStatus status;

    /**
     * Creates a new Order for a given customer and PC model using a valid credit card.
     * <p>
     * When an order is created, its status automatically starts as {@link OrderStatus#PLACED},
     * and the creation date is recorded immediately.
     * </p>
     *
     * @param customer the customer who is placing the order
     * @param model    the PC model that is being ordered
     * @param card     the credit card used for payment
     * @throws IllegalArgumentException if any argument is {@code null}
     */
    public Order(Customer customer, PCModel model, CreditCard card) {
        if (customer == null || model == null || card == null) {
            throw new IllegalArgumentException("Customer, model, and card cannot be null");
        }
        this.customer = customer;
        this.model = model;
        this.card = card;
        this.datePlaced = new Date(); // automatically record when order is created
        this.status = OrderStatus.PLACED;
    }

    /** @return the customer who placed this order */
    public Customer getCustomer() { return customer; }

    /** @return the PC model associated with this order */
    public PCModel getModel() { return model; }

    /** @return the credit card used for this order */
    public CreditCard getCard() { return card; }

    /**
     * Returns the date the order was placed.
     * A defensive copy is returned to protect immutability.
     *
     * @return the order creation date
     */
    public Date getDatePlaced() { return new Date(datePlaced.getTime()); }

    /** @return the current order status */
    public OrderStatus getStatus() { return status; }

    /**
     * Cancels the order if it is still in the PLACED state.
     * Once fulfilled, an order can no longer be cancelled.
     */
    public void cancelOrder() {
        if (status == OrderStatus.PLACED) {
            status = OrderStatus.CANCELLED;
        }
    }

    /**
     * Marks the order as fulfilled (successfully completed).
     * Fulfilled orders cannot later be cancelled.
     */
    public void fulfilOrder() {
        if (status == OrderStatus.PLACED) {
            status = OrderStatus.FULFILLED;
        }
    }

    /**
     * Returns a readable string with the main order details.
     * Example:
     * <pre>
     * Order[customer=Nick Davis, model=GamingPC, status=PLACED, date=Sun Oct 12 20:35:00 BST 2025]
     * </pre>
     *
     * @return a summary string for this order
     */
    @Override
    public String toString() {
        return String.format("Order[customer=%s, model=%s, status=%s, date=%s]",
                customer, model.getName(), status, datePlaced);
    }

    /**
     * Two orders are considered equal if they have the same customer,
     * the same PC model, and were created at the same date and time.
     *
     * @param o another object to compare with
     * @return true if both orders are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return customer.equals(order.customer)
                && model.equals(order.model)
                && datePlaced.equals(order.datePlaced);
    }

    /**
     * Generates a hash code based on the customer, model, and order date.
     *
     * @return hash code for this order
     */
    @Override
    public int hashCode() {
        return Objects.hash(customer, model, datePlaced);
    }
}
