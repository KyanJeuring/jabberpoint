package persistence;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Presentation;

/**
 * Unit tests for the DemoPresentation class.
 * Tests demo presentation creation and initialization.
 */
public class DemoPresentationTest
{
    private DemoPresentation demoPresentation;

    @BeforeEach
    public void setUp() throws IOException
    {
        demoPresentation = new DemoPresentation();
    }

    @Test
    public void testDemoPresentationInitialization()
    {
        // Assert
        assertNotNull(demoPresentation);
    }

    @Test
    public void testDemoPresentationCreatesValidPresentation() throws IOException
    {
        // Act
        Presentation presentation = new Presentation();
        demoPresentation.loadFile(presentation, "");

        // Assert
        assertNotNull(presentation);
        assertTrue(presentation.getSize() > 0, "Demo presentation should have slides");
    }

    @Test
    public void testDemoPresentationIsAccesor()
    {
        // Assert
        assertTrue(demoPresentation instanceof Accesor);
    }

    @Test
    public void testDemoPresentationSlideCount() throws IOException
    {
        // Arrange
        Presentation presentation = new Presentation();

        // Act
        demoPresentation.loadFile(presentation, "");

        // Assert - Demo should have multiple slides
        assertTrue(presentation.getSize() >= 1, "Demo presentation should have at least 1 slide");
    }

    @Test
    public void testDemoPresentationAllSlidesHaveContent() throws IOException
    {
        // Arrange
        Presentation presentation = new Presentation();

        // Act
        demoPresentation.loadFile(presentation, "");

        // Assert - All demo slides should have content
        for (int i = 0; i < presentation.getSize(); i++)
        {
            var slide = presentation.getSlide(i);
            assertNotNull(slide);
        }
    }

    @Test
    public void testMultipleDemoPresentationInstances() throws IOException
    {
        // Act
        DemoPresentation demo1 = new DemoPresentation();
        DemoPresentation demo2 = new DemoPresentation();

        Presentation pres1 = new Presentation();
        Presentation pres2 = new Presentation();

        demo1.loadFile(pres1, "");
        demo2.loadFile(pres2, "");

        // Assert
        assertNotNull(demo1);
        assertNotNull(demo2);
        assertEquals(pres1.getSize(), pres2.getSize());
    }
}
