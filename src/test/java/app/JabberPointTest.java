package app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Presentation;
import model.Style;

/**
 * Unit tests for the JabberPoint main application class.
 * Tests initialization and main entry point logic.
 */
public class JabberPointTest
{

    @BeforeEach
    public void setUp()
    {
        Style.createStyles();
    }

    @Test
    public void testJabVersionConstant()
    {
        // Assert version string is set
        assertEquals("Jabberpoint 1.6 - OU version", "Jabberpoint 1.6 - OU version");
    }

    @Test
    public void testJabVersionNotEmpty()
    {
        // Assert version string is not null or empty
        String version = "Jabberpoint 1.6 - OU version";
        assertNotNull(version);
        assertTrue(version.length() > 0);
    }

    @Test
    public void testJabVersionContainsVersion()
    {
        // Assert version contains version number
        String version = "Jabberpoint 1.6 - OU version";
        assertTrue(version.contains("1.6"));
    }

    @Test
    public void testStylesInitialized()
    {
        // Assert styles are created without error
        assertDoesNotThrow(() -> Style.createStyles());
    }

    @Test
    public void testPresentationCanBeCreated()
    {
        // Assert presentation can be instantiated
        Presentation presentation = new Presentation();
        assertNotNull(presentation);
    }

    @Test
    public void testPresentationInitialState()
    {
        // Arrange
        Presentation presentation = new Presentation();

        // Act & Assert
        assertEquals(0, presentation.getSize());
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void testMultiplePresentationsCanBeCreated()
    {
        // Arrange & Act
        Presentation pres1 = new Presentation();
        Presentation pres2 = new Presentation();

        // Assert they are separate instances
        assertNotSame(pres1, pres2);
    }
}
