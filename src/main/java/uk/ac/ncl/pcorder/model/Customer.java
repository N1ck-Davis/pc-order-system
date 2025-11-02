package uk.ac.ncl.pcorder.model;

import java.util.Objects;

/**
 * Represents a customer with a first and last name.
 * <p>
 * The class is immutable – once created, a customer's name cannot change.
 * Two customers are considered the same if their first and last names
 * match, ignoring letter case.
 * </p>
 */
public final class Customer {

    /** The customer's first name */
    private final String firstName;

    /** The customer's last name */
    private final String lastName;

    /**
     * Creates a new {@code Customer} object with the given first and last name.
     *
     * @param firstName the customer's first name
     * @param lastName  the customer's last name
     * @throws IllegalArgumentException if either name is null or blank
     */
    public Customer(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name must not be empty");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name must not be empty");
        }

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    /**
     * Gets the customer's first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the customer's last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the customer's full name in the format "FirstName LastName".
     *
     * @return full name as a single string
     */
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    /**
     * Checks whether two customers are equal.
     * <p>
     * Equality is case-insensitive and based on both first and last name.
     * </p>
     *
     * @param o the object to compare
     * @return true if both customers have the same name, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer that = (Customer) o;
        return firstName.equalsIgnoreCase(that.firstName)
                && lastName.equalsIgnoreCase(that.lastName);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return hash code based on lowercase name values
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName.toLowerCase(), lastName.toLowerCase());
    }
}
