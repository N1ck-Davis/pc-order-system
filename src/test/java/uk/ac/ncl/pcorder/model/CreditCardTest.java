package uk.ac.ncl.pcorder.model;

import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple unit tests for the {@link CreditCard} class.
 * These tests check card creation, validation rules,
 * and equality between cards.
 */
class CreditCardTest {

    /** Creates a future expiry date (2 years ahead). */
    private Date futureDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 2);
        return c.getTime();
    }

    /** Creates a past expiry date (2 years ago). */
    private Date pastDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -2);
        return c.getTime();
    }

    /** Checks that a valid credit card is recognised as valid. */
    @Test
    void validCardShouldBeRecognized() {
        CreditCard card = new CreditCard("12345678", futureDate(), "Nick Davis");
        assertTrue(card.isValid(), "A valid card should return true");
    }

    /** Checks that an expired card is marked as invalid. */
    @Test
    void expiredCardShouldBeInvalid() {
        CreditCard card = new CreditCard("87654321", pastDate(), "Maria Kolesnichenko");
        assertFalse(card.isValid(), "An expired card should return false");
    }

    /** Ensures that an invalid card number throws an error. */
    @Test
    void invalidNumberShouldThrowError() {
        assertThrows(IllegalArgumentException.class, () ->
                new CreditCard("1234", futureDate(), "Kamisato Ayaka"));
    }

    /** Ensures that an empty card holder name is rejected. */
    @Test
    void invalidHolderNameShouldThrowError() {
        assertThrows(IllegalArgumentException.class, () ->
                new CreditCard("11112222", futureDate(), ""));
    }

    /** Checks that the string output includes key information. */
    @Test
    void toStringShouldContainImportantInfo() {
        CreditCard card = new CreditCard("22223333", futureDate(), "Raiden Ei");
        String result = card.toString();

        // should contain both number and name
        assertTrue(result.contains("22223333"));
        assertTrue(result.contains("Raiden Ei"));
    }

    /** Verifies that equals() and hashCode() are consistent. */
    @Test
    void testEqualsAndHashCode() {
        CreditCard c1 = new CreditCard("33334444", futureDate(), "Yae Miko");
        CreditCard c2 = new CreditCard("33334444", futureDate(), "Yae Miko");

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }
}
