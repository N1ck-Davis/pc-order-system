package uk.ac.ncl.pcorder.model;

/**
 * Represents the current status of an order.
 * <p>
 * An order can be in one of three states:
 * <ul>
 *   <li><b>PLACED</b> – when the order has been created but not yet fulfilled or cancelled.</li>
 *   <li><b>FULFILLED</b> – when the company has successfully completed the order.</li>
 *   <li><b>CANCELLED</b> – when the order has been cancelled by the customer or system.</li>
 * </ul>
 * </p>
 */
public enum OrderStatus {
    /** Order has been placed but not yet processed */
    PLACED,

    /** Order has been fulfilled and cannot be changed */
    FULFILLED,

    /** Order has been cancelled before being fulfilled */
    CANCELLED
}
