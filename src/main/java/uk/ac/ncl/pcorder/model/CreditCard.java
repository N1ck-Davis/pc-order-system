package uk.ac.ncl.pcorder.model;

import java.util.Date;

/**
 * Represents a basic credit card used for placing PC orders.
 * <p>
 * Each credit card has an 8-digit number, an expiry date, and a cardholder name.
 * The card is considered valid if it has not expired and all its fields are set properly.
 * </p>
 */
public class CreditCard {

    /** 8-digit unique card number */
    private final String number;

    /** Expiry date of the card */
    private final Date expiry;

    /** Full name of the card holder */
    private final String holderName;

    /**
     * Creates a new CreditCard object with the given details.
     *
     * @param number     the 8-digit card number
     * @param expiry     the expiry date of the card
     * @param holderName the full name of the card holder
     * @throws IllegalArgumentException if any field is missing or invalid
     */
    public CreditCard(String number, Date expiry, String holderName) {
        // validate input fields
        if (number == null || !number.matches("\\d{8}")) {
            throw new IllegalArgumentException("Card number must be exactly 8 digits.");
        }
        if (expiry == null) {
            throw new IllegalArgumentException("Expiry date cannot be null.");
        }
        if (holderName == null || holderName.isBlank()) {
            throw new IllegalArgumentException("Card holder name cannot be empty.");
        }

        this.number = number;
        // defensive copy so the date cannot be changed from outside
        this.expiry = new Date(expiry.getTime());
        this.holderName = holderName.trim();
    }

    /**
     * Gets the 8-digit card number.
     *
     * @return the card number as a String
     */
    public String getNumber() {
        return number;
    }

    /**
     * Gets the expiry date of this card.
     * Returns a defensive copy to keep the class immutable.
     *
     * @return the expiry date
     */
    public Date getExpiry() {
        return new Date(expiry.getTime());
    }

    /**
     * Gets the name of the card holder.
     *
     * @return the holder's full name
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * Checks if the credit card is valid.
     * <p>
     * A card is valid if:
     * <ul>
     *   <li>the number has exactly 8 digits,</li>
     *   <li>the holder name is not empty, and</li>
     *   <li>the expiry date is in the future.</li>
     * </ul>
     * </p>
     *
     * @return true if the card is valid, false otherwise
     */
    public boolean isValid() {
        Date now = new Date();
        return number.matches("\\d{8}")
                && !holderName.isBlank()
                && expiry.after(now);
    }

    /**
     * Returns a readable string version of the card.
     * Example: "12345678 - Nick Davis (exp: Fri Dec 31 00:00:00 GMT 2027)"
     *
     * @return string representation of the credit card
     */
    @Override
    public String toString() {
        return number + " - " + holderName + " (exp: " + expiry + ")";
    }

    /**
     * Two cards are equal if they share the same card number.
     *
     * @param obj the object to compare
     * @return true if the card numbers are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CreditCard)) return false;
        CreditCard other = (CreditCard) obj;
        return number.equals(other.number);
    }

    /**
     * Hash code based on the card number.
     *
     * @return hash code value
     */
    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
