package uk.ac.ncl.pcorder.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a preset PC model supplied by a third-party manufacturer.
 * <p>
 * Preset models have a fixed list of parts and a fixed price.
 * Customers cannot modify them, as they are provided directly
 * by external manufacturers.
 * </p>
 */
public final class PresetPCModel implements PCModel {

    /** The model name */
    private final String name;

    /** The third-party manufacturer providing this model */
    private final String manufacturer;

    /** The list of parts included in the preset model (cannot be changed) */
    private final List<String> parts;

    /** The fixed total price of this model */
    private final double price;

    /**
     * Creates a new preset PC model.
     *
     * @param name         the model name
     * @param manufacturer the name of the manufacturer
     * @param parts        list of computer parts included in the model
     * @param price        total price of the model
     * @throws IllegalArgumentException if any argument is missing or invalid
     */
    public PresetPCModel(String name, String manufacturer, List<String> parts, double price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Model name must not be empty");
        }
        if (manufacturer == null || manufacturer.isBlank()) {
            throw new IllegalArgumentException("Manufacturer must not be empty");
        }
        if (parts == null || parts.isEmpty()) {
            throw new IllegalArgumentException("Parts list must not be empty");
        }

        for (String p : parts) {
            if (p == null || p.isBlank()) {
                throw new IllegalArgumentException("Part names must not be empty");
            }
        }

        this.name = name.trim();
        this.manufacturer = manufacturer.trim();
        this.parts = Collections.unmodifiableList(List.copyOf(parts));
        this.price = price;
    }

    /**
     * Gets the name of the preset model.
     *
     * @return the model name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the manufacturer of this preset model.
     *
     * @return the manufacturer's name
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Gets the total fixed price of this model.
     *
     * @return the price
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * Gets the list of all parts that come with this model.
     * The list cannot be modified.
     *
     * @return an unmodifiable list of parts
     */
    @Override
    public List<String> getParts() {
        return parts;
    }

    /**
     * Returns a readable description of this preset model.
     *
     * @return formatted string with name, manufacturer, price, and parts
     */
    @Override
    public String toString() {
        return String.format(
                "PresetPCModel{name='%s', manufacturer='%s', price=%.2f, parts=%s}",
                name, manufacturer, price, parts
        );
    }

    /**
     * Checks if two preset models are equal.
     * <p>
     * Equality is based on both model name and manufacturer name,
     * compared without case sensitivity.
     * </p>
     *
     * @param o the object to compare
     * @return true if both model and manufacturer names match
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PresetPCModel)) return false;
        PresetPCModel that = (PresetPCModel) o;
        return name.equalsIgnoreCase(that.name)
                && manufacturer.equalsIgnoreCase(that.manufacturer);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return hash code based on lowercase name and manufacturer
     */
    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(), manufacturer.toLowerCase());
    }
}
