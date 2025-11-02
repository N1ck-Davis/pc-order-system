package uk.ac.ncl.pcorder.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Order} class.
 * <p>
 * These tests verify that orders are created correctly,
 * transition properly between statuses,
 * and produce the expected equality and string output behaviour.
 * The data used simulates realistic high-end gaming PC builds.
 * </p>
 */
class OrderTest {

    private Customer nick;
    private CreditCard nickCard;
    private PresetPCModel asusBuild;

    /** Creates a valid future expiry date for credit cards. */
    private Date futureDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 2);
        return c.getTime();
    }

    /** Sets up a test customer, credit card, and sample gaming build before each test. */
    @BeforeEach
    void setUp() {
        nick = new Customer("Nick", "Davis");
        nickCard = new CreditCard("12345678", futureDate(), "Nick Davis");

        asusBuild = new PresetPCModel("ROG Strix Ultimate", "ASUS",
                Arrays.asList("Ryzen 9 7950X CPU", "64GB DDR5 RAM", "2TB NVMe SSD", "RTX 4090 GPU"), 4599.99);
    }

    /** Checks that a new order starts with the PLACED status. */
    @Test
    void testOrderStartsWithPlacedStatus() {
        Order order = new Order(nick, asusBuild, nickCard);
        assertEquals(OrderStatus.PLACED, order.getStatus());
    }

    /** Ensures an order can be cancelled before it is fulfilled. */
    @Test
    void testOrderCanBeCancelledBeforeFulfilled() {
        Order order = new Order(nick, asusBuild, nickCard);
        order.cancelOrder();
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    /** Ensures a fulfilled order cannot be cancelled afterwards. */
    @Test
    void testOrderCannotBeCancelledAfterFulfilled() {
        Order order = new Order(nick, asusBuild, nickCard);
        order.fulfilOrder();
        order.cancelOrder(); // should have no effect
        assertEquals(OrderStatus.FULFILLED, order.getStatus());
    }

    /** Checks that fulfilling an order updates its status correctly. */
    @Test
    void testOrderCanBeFulfilled() {
        Order order = new Order(nick, asusBuild, nickCard);
        order.fulfilOrder();
        assertEquals(OrderStatus.FULFILLED, order.getStatus());
    }

    /** Verifies that toString() includes key order details. */
    @Test
    void testToStringShowsImportantDetails() {
        Order order = new Order(nick, asusBuild, nickCard);
        String result = order.toString();
        assertTrue(result.contains("Nick"));
        assertTrue(result.contains("ROG Strix Ultimate"));
        assertTrue(result.contains("PLACED"));
    }

    /** Confirms equality depends on customer, model, and placement date. */
    @Test
    void testEqualityBasedOnCustomerModelAndDate() throws InterruptedException {
        Order order1 = new Order(nick, asusBuild, nickCard);
        Thread.sleep(5); // ensure different timestamp
        Order order2 = new Order(nick, asusBuild, nickCard);

        assertNotEquals(order1, order2);
    }
}
