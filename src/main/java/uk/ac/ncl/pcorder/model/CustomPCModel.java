package uk.ac.ncl.pcorder.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a custom PC model created by a customer.
 * <p>
 * A custom model has a unique name and a flexible list of parts.
 * Customers can freely add or remove parts, and the total price
 * changes depending on the parts included.
 * </p>
 */
public final class CustomPCModel implements PCModel {

    /** Unique name for this custom model */
    private final String name;

    /** List of parts added by the customer */
    private final List<String> parts;

    /** Total price of the custom model */
    private double price;

    /**
     * Creates a new custom PC model with the given name.
     *
     * @param name the unique name for the model
     * @throws IllegalArgumentException if the name is null or blank
     */
    public CustomPCModel(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Model name must not be empty");
        }
        this.name = name.trim();
        this.parts = new ArrayList<>();
        this.price = 0.0;
    }

    /**
     * Gets the name of this custom PC model.
     *
     * @return the model name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the current price of this custom model.
     *
     * @return the total price
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * Gets an unmodifiable list of all parts currently added to the model.
     *
     * @return a list of part names
     */
    @Override
    public List<String> getParts() {
        return Collections.unmodifiableList(parts);
    }

    /**
     * Adds a new part to the model and increases the total price.
     *
     * @param part      the name of the part to add
     * @param partPrice the price of the part being added
     * @throws IllegalArgumentException if the part name is empty or null
     */
    public void addPart(String part, double partPrice) {
        if (part == null || part.isBlank()) {
            throw new IllegalArgumentException("Part must not be empty");
        }
        parts.add(part.trim());
        price += partPrice;
    }

    /**
     * Removes a part from the model and decreases the total price.
     *
     * @param part      the name of the part to remove
     * @param partPrice the price of that part (used to update the total)
     * @return true if the part was found and removed, false otherwise
     * @throws IllegalArgumentException if the part name is empty or null
     */
    public boolean removePart(String part, double partPrice) {
        if (part == null || part.isBlank()) {
            throw new IllegalArgumentException("Part must not be empty");
        }
        boolean removed = parts.remove(part.trim());
        if (removed) {
            price -= partPrice;
        }
        return removed;
    }

    /**
     * Replaces all parts in this custom model with a new list.
     * <p>
     * This does not automatically update the price.
     * The price can be recalculated separately if required.
     * </p>
     *
     * @param newParts the new list of parts
     * @throws IllegalArgumentException if the list or any part name is invalid
     */
    public void setParts(List<String> newParts) {
        if (newParts == null) {
            throw new IllegalArgumentException("Parts cannot be null");
        }
        parts.clear();
        for (String p : newParts) {
            if (p == null || p.isBlank()) {
                throw new IllegalArgumentException("Part names must not be empty");
            }
            parts.add(p.trim());
        }
    }

    /**
     * Returns a simple text version of this custom model.
     *
     * @return a string with the model name
     */
    @Override
    public String toString() {
        return "Custom Model: " + name;
    }
}
