package uk.ac.ncl.pcorder.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link OrderManager} class.
 * <p>
 * These tests check placing, fulfilling, and cancelling orders,
 * as well as identifying the largest customer, most ordered model, and most ordered part.
 * </p>
 */
class OrderManagerTest {

    private OrderManager manager;
    private Customer nick;
    private Customer maria;
    private CreditCard nickCard;
    private CreditCard mariaCard;
    private PresetPCModel dellModel;
    private PresetPCModel asusModel;
    private CustomPCModel gamerBuild;

    /** Creates a date two years in the future (used for valid cards). */
    private Date futureDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 2);
        return c.getTime();
    }

    /** Reset and set up base data before each test. */
    @BeforeEach
    void setUp() {
        CreditCardFactory.resetFactory();
        manager = new OrderManager();

        nick = new Customer("Nick", "Davis");
        maria = new Customer("Maria", "Kolesnichenko");

        nickCard = CreditCardFactory.createCard("11112222", futureDate(), "Nick Davis");
        mariaCard = CreditCardFactory.createCard("33334444", futureDate(), "Maria Kolesnichenko");

        dellModel = new PresetPCModel("ProStation X1", "Dell",
                Arrays.asList("Intel i7 CPU", "16GB RAM", "1TB SSD"), 1499.99);

        asusModel = new PresetPCModel("ROG Strix Tower", "ASUS",
                Arrays.asList("Ryzen 9 CPU", "32GB RAM", "RTX 4070 GPU"), 1999.99);

        gamerBuild = new CustomPCModel("Custom Gamer Build");
        gamerBuild.addPart("Ryzen 9 CPU", 550.0);
        gamerBuild.addPart("RTX 4070 GPU", 850.0);
        gamerBuild.addPart("32GB RAM", 250.0);
    }

    /** Checks that placing an order works normally. */
    @Test
    void testPlaceOrder() {
        Order order = manager.placeOrder(nick, dellModel, nickCard);
        assertNotNull(order);
        assertEquals(OrderStatus.PLACED, order.getStatus());
        assertEquals(nick, order.getCustomer());
    }

    /** Ensures an order can be fulfilled correctly. */
    @Test
    void testFulfilOrder() {
        Order order = manager.placeOrder(maria, asusModel, mariaCard);
        assertTrue(manager.fulfilOrder(order));
        assertEquals(OrderStatus.FULFILLED, order.getStatus());
    }

    /** Ensures an order can be cancelled before fulfilment. */
    @Test
    void testCancelOrder() {
        Order order = manager.placeOrder(nick, dellModel, nickCard);
        assertTrue(manager.cancelOrder(order));
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    /** Ensures a fulfilled order cannot be cancelled later. */
    @Test
    void testCannotCancelFulfilledOrder() {
        Order order = manager.placeOrder(nick, dellModel, nickCard);
        manager.fulfilOrder(order);
        assertFalse(manager.cancelOrder(order));
    }

    /** Checks getLargestCustomer() returns correct customer and count. */
    @Test
    void testGetLargestCustomer() {
        Order o1 = manager.placeOrder(maria, dellModel, mariaCard);
        manager.fulfilOrder(o1);

        Order o2 = manager.placeOrder(maria, asusModel, mariaCard);
        manager.fulfilOrder(o2);

        Order o3 = manager.placeOrder(nick, dellModel, nickCard);
        manager.fulfilOrder(o3);

        Map.Entry<Customer, Integer> result = manager.getLargestCustomer();
        assertNotNull(result);
        assertEquals("Maria", result.getKey().getFirstName());
        assertEquals(2, result.getValue());
    }

    /** Checks getMostOrderedModel() finds the correct preset PC model. */
    @Test
    void testGetMostOrderedModel() {
        Order o1 = manager.placeOrder(maria, dellModel, mariaCard);
        manager.fulfilOrder(o1);

        Order o2 = manager.placeOrder(nick, dellModel, nickCard);
        manager.fulfilOrder(o2);

        Map.Entry<String, Integer> result = manager.getMostOrderedModel();
        assertNotNull(result);
        assertEquals("Dell - ProStation X1", result.getKey());
        assertEquals(2, result.getValue());
    }

    /** Checks getMostOrderedPart() correctly identifies the top custom part. */
    @Test
    void testGetMostOrderedPart() {
        CustomPCModel gamer1 = new CustomPCModel("GamerOne");
        gamer1.addPart("RTX 4070 GPU", 850.0);
        gamer1.addPart("32GB RAM", 250.0);

        CustomPCModel gamer2 = new CustomPCModel("GamerTwo");
        gamer2.addPart("32GB RAM", 250.0);
        gamer2.addPart("1TB SSD", 150.0);

        Order o1 = manager.placeOrder(nick, gamer1, nickCard);
        manager.fulfilOrder(o1);

        Order o2 = manager.placeOrder(maria, gamer2, mariaCard);
        manager.fulfilOrder(o2);

        Map.Entry<String, Integer> result = manager.getMostOrderedPart();
        assertNotNull(result);
        assertEquals("32GB RAM", result.getKey());
        assertEquals(2, result.getValue());
    }

    /** Ensures the system handles empty data safely for all summary methods. */
    @Test
    void testSystemHandlesEmptyHistoryGracefully() {
        assertNull(manager.getLargestCustomer());
        assertNull(manager.getMostOrderedModel());
        assertNull(manager.getMostOrderedPart());
    }
}
