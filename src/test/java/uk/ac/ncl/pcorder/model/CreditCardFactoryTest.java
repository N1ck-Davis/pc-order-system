package uk.ac.ncl.pcorder.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic tests for the {@link CreditCardFactory} class.
 * These tests make sure that card numbers stay unique,
 * that the factory resets correctly, and that card creation works as expected.
 */
class CreditCardFactoryTest {

    /** Creates a date one year in the future so cards are valid in tests. */
    private Date futureDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        return c.getTime();
    }

    /** Reset between tests so numbers don’t leak across tests. */
    @BeforeEach
    void setUp() {
        CreditCardFactory.resetFactory();
    }

    /** Checks that creating a new credit card works normally. */
    @Test
    void createCardShouldWorkNormally() {
        CreditCard card = CreditCardFactory.createCard("12345678", futureDate(), "Nick Davis");

        assertNotNull(card);
        assertEquals("12345678", card.getNumber());
        assertTrue(card.isValid());
    }

    /** Ensures that using the same card number twice throws an error. */
    @Test
    void duplicateNumberShouldThrowError() {
        CreditCardFactory.createCard("87654321", futureDate(), "Maria Kolesnichenko");

        // creating another card with same number should fail
        assertThrows(IllegalArgumentException.class, () -> {
            CreditCardFactory.createCard("87654321", futureDate(), "Zhongli Morax");
        });
    }

    /** Verifies that the factory tracks the total number of created cards. */
    @Test
    void factoryShouldTrackTotalCards() {
        CreditCardFactory.createCard("11112222", futureDate(), "Kamisato Ayaka");
        CreditCardFactory.createCard("33334444", futureDate(), "Raiden Ei");

        assertEquals(2, CreditCardFactory.getTotalCards());
    }

    /** Checks that resetting the factory clears all stored card numbers. */
    @Test
    void resetFactoryShouldClearAllData() {
        CreditCardFactory.createCard("99998888", futureDate(), "Yae Miko");
        CreditCardFactory.resetFactory();

        // should allow same number again after reset
        CreditCard newCard = CreditCardFactory.createCard("99998888", futureDate(), "Yae Miko");
        assertNotNull(newCard);
    }
}
