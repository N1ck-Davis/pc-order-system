package uk.ac.ncl.pcorder.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple factory class used to create {@link CreditCard} objects.
 * <p>
 * This factory guarantees that each card number is unique across the system.
 * Once a card is created with a number, that same number cannot be reused
 * until the factory is reset (mainly for testing).
 * </p>
 */
public class CreditCardFactory {

    /** Keeps track of all card numbers that have already been used */
    private static final Set<String> usedNumbers = new HashSet<>();

    /**
     * Private constructor to prevent creating instances of this utility class.
     * All methods here are static.
     */
    private CreditCardFactory() {
        // prevents instantiation
    }

    /**
     * Creates a new {@link CreditCard} if the number has not been used before.
     * <p>
     * If the same number already exists in the system, an exception is thrown.
     * This helps guarantee that each card number remains unique.
     * </p>
     *
     * @param number     the 8-digit credit card number
     * @param expiry     the expiry date of the card
     * @param holderName the name of the card holder
     * @return a new, unique {@code CreditCard} object
     * @throws IllegalArgumentException if the number has already been used
     */
    public static CreditCard createCard(String number, Date expiry, String holderName) {
        if (usedNumbers.contains(number)) {
            throw new IllegalArgumentException("Card number already used: " + number);
        }

        CreditCard card = new CreditCard(number, expiry, holderName);
        usedNumbers.add(number);
        return card;
    }

    /**
     * Removes all stored card numbers from the factory.
     * <p>
     * This method is mostly used for testing or resetting
     * the system between runs.
     * </p>
     */
    public static void resetFactory() {
        usedNumbers.clear();
    }

    /**
     * Gets the total number of unique cards that have been created so far.
     *
     * @return number of unique credit cards
     */
    public static int getTotalCards() {
        return usedNumbers.size();
    }
}
