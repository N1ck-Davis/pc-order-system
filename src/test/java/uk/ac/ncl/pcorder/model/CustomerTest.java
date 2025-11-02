package uk.ac.ncl.pcorder.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple unit tests for the {@link Customer} class.
 * These tests check input validation, equality rules,
 * and the output format of the customer's name.
 */
class CustomerTest {

    /** Makes sure the constructor rejects blank or null names. */
    @Test
    void rejectsBlankNames() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("", "Kolesnichenko"));
        assertThrows(IllegalArgumentException.class, () -> new Customer("Maria", " "));
        assertThrows(IllegalArgumentException.class, () -> new Customer(null, "Davis"));
        assertThrows(IllegalArgumentException.class, () -> new Customer("Nick", null));
    }

    /** Checks that equality is case-insensitive and consistent with hashCode(). */
    @Test
    void equalityIsCaseInsensitive() {
        Customer c1 = new Customer("Nick", "Davis");
        Customer c2 = new Customer("nick", "DAVIS");

        // both should be considered equal regardless of letter case
        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    /** Verifies that toString() returns the full name correctly. */
    @Test
    void toStringShowsFullName() {
        Customer c = new Customer("Maria", "Kolesnichenko");
        assertEquals("Maria Kolesnichenko", c.toString());
    }
}
