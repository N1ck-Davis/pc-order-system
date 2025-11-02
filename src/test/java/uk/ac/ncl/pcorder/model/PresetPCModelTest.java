package uk.ac.ncl.pcorder.model;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PresetPCModel} class.
 * These tests check that preset models correctly return their details,
 * remain immutable, and compare equality based on name and manufacturer.
 */
class PresetPCModelTest {

    /** Checks that all getter methods return the correct values. */
    @Test
    void testGettersWorkCorrectly() {
        List<String> parts = Arrays.asList("Intel i9 CPU", "32GB DDR5 RAM", "2TB NVMe SSD");
        PresetPCModel model = new PresetPCModel("Alienware Aurora R15", "Dell", parts, 2999.99);

        assertEquals("Alienware Aurora R15", model.getName());
        assertEquals("Dell", model.getManufacturer());
        assertEquals(2999.99, model.getPrice());
        assertEquals(parts, model.getParts());
    }

    /** Ensures that the parts list cannot be modified from outside the class. */
    @Test
    void testPartsListIsImmutable() {
        List<String> parts = Arrays.asList("NVIDIA RTX 4080 GPU", "1000W PSU");
        PresetPCModel model = new PresetPCModel("ROG Strix Tower", "ASUS", parts, 3499.00);

        // trying to add a part should throw an exception
        List<String> retrieved = model.getParts();
        assertThrows(UnsupportedOperationException.class, () -> retrieved.add("Extra RGB Fan"));
    }

    /**
     * Checks that equality depends only on the model name and manufacturer,
     * not on the price or list of parts.
     */
    @Test
    void testEqualityBasedOnNameAndManufacturer() {
        List<String> parts = Arrays.asList("Ryzen 7 CPU", "16GB RAM");
        PresetPCModel model1 = new PresetPCModel("Predator Orion 7000", "Acer", parts, 2799.0);
        PresetPCModel model2 = new PresetPCModel("Predator Orion 7000", "Acer", parts, 2899.0);

        assertEquals(model1, model2);
        assertEquals(model1.hashCode(), model2.hashCode());
    }

    /** Makes sure the string version of the model contains the main details. */
    @Test
    void testToStringContainsDetails() {
        List<String> parts = Arrays.asList("Intel i7 CPU", "16GB RAM");
        PresetPCModel model = new PresetPCModel("Legion Tower 7i", "Lenovo", parts, 2199.0);

        String result = model.toString();

        // the string should include name, manufacturer, and price
        assertTrue(result.contains("Legion Tower 7i"));
        assertTrue(result.contains("Lenovo"));
        assertTrue(result.contains("2199.0"));
    }
}
