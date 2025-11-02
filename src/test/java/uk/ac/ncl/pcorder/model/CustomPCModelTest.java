package uk.ac.ncl.pcorder.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link CustomPCModel} class.
 * These tests check that parts can be added and removed correctly,
 * the total price updates as expected, and invalid inputs are rejected.
 */
class CustomPCModelTest {

    private CustomPCModel model;

    @BeforeEach
    void setUp() {
        model = new CustomPCModel("GamerBuild");
    }

    /** Checks that adding parts updates both the list and total price. */
    @Test
    void testAddPartIncreasesPrice() {
        model.addPart("AMD Ryzen 9 7950X CPU", 550.0);
        model.addPart("NVIDIA RTX 4090 GPU", 1800.0);
        model.addPart("32GB DDR5 RAM", 250.0);

        assertEquals(3, model.getParts().size());
        assertEquals(2600.0, model.getPrice(), 0.01);
        assertTrue(model.getParts().contains("AMD Ryzen 9 7950X CPU"));
        assertTrue(model.getParts().contains("NVIDIA RTX 4090 GPU"));
    }

    /** Checks that removing a part decreases the total price accordingly. */
    @Test
    void testRemovePartDecreasesPrice() {
        model.addPart("ASUS ROG Motherboard", 400.0);
        model.addPart("1TB NVMe SSD", 150.0);
        model.addPart("Corsair Liquid Cooler", 200.0);

        boolean removed = model.removePart("ASUS ROG Motherboard", 400.0);

        assertTrue(removed);
        assertEquals(350.0, model.getPrice(), 0.01);
        assertFalse(model.getParts().contains("ASUS ROG Motherboard"));
    }

    /** Makes sure adding or removing blank part names throws an exception. */
    @Test
    void testInvalidPartNameThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> model.addPart("", 100.0));
        assertThrows(IllegalArgumentException.class, () -> model.removePart(" ", 50.0));
    }

    /** Ensures that setting a new list of parts replaces the old one. */
    @Test
    void testSetPartsReplacesList() {
        List<String> newParts = Arrays.asList(
                "NZXT Gaming Case",
                "850W Power Supply",
                "Lian Li RGB Fans"
        );
        model.setParts(newParts);

        assertEquals(3, model.getParts().size());
        assertTrue(model.getParts().containsAll(newParts));
    }

    /** Checks that the parts list returned is unmodifiable from outside. */
    @Test
    void testGetPartsIsUnmodifiable() {
        model.addPart("Corsair DDR5 RAM", 180.0);
        List<String> parts = model.getParts();

        assertThrows(UnsupportedOperationException.class, () -> parts.add("Extra RGB Strip"));
    }

    /** Checks that toString() includes the custom model name. */
    @Test
    void testToStringIncludesModelName() {
        String result = model.toString();
        assertTrue(result.contains("GamerBuild"));
    }
}
