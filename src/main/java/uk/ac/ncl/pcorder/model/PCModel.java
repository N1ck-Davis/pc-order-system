package uk.ac.ncl.pcorder.model;

import java.util.List;

/**
 * Represents a general PC model in the ordering system.
 * <p>
 * Both preset and custom PC models share these basic properties:
 * a name, a total price, and a list of computer parts.
 * </p>
 * <p>
 * This interface allows the system to treat all PC models in a
 * consistent way, regardless of how they were created.
 * </p>
 */
public interface PCModel {

    /**
     * Gets the name of the PC model.
     *
     * @return the model name
     */
    String getName();

    /**
     * Gets the total price of this PC model.
     *
     * @return the total price
     */
    double getPrice();

    /**
     * Gets the list of all parts included in this PC model.
     *
     * @return a list of part names
     */
    List<String> getParts();
}
