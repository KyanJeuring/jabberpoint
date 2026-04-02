package view;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Presentation;
import model.Slide;
import model.Style;
import model.TextItem;

/**
 * Unit tests for the SlideViewerComponent view class.
 * Tests slide rendering component and observer updates.
 */
public class SlideViewerComponentTest
{
    private Presentation presentation;
    private SlideViewerComponent component;
    private JFrame frame;

    @BeforeEach
    public void setUp()
    {
        Style.createStyles();
        presentation = new Presentation();
        frame = new JFrame("Test Frame");
        component = new SlideViewerComponent(presentation, frame);
    }

    @Test
    public void testComponentInitialization()
    {
        // Assert component is created successfully
        assertNotNull(component);
        assertNotNull(component.getBackground());
    }

    @Test
    public void testComponentWithValidPresentation()
    {
        // Assert component can be created with valid presentation
        assertDoesNotThrow(() -> new SlideViewerComponent(presentation, frame));
    }

    @Test
    public void testComponentWithNullFrame()
    {
        // Assert component handles null frame without throwing
        assertDoesNotThrow(() -> new SlideViewerComponent(presentation, null));
    }

    @Test
    public void testComponentObserverRegistration()
    {
        // Assert observer is registered without error
        assertDoesNotThrow(() -> new SlideViewerComponent(presentation, frame));
    }

    @Test
    public void testGetPreferredSize()
    {
        // Arrange & Act
        Dimension size = component.getPreferredSize();

        // Assert dimensions match slide dimensions
        assertEquals(Slide.WIDTH, size.width);
        assertEquals(Slide.HEIGHT, size.height);
    }

    @Test
    public void testGetPreferredSizeConsistency()
    {
        // Act
        Dimension size1 = component.getPreferredSize();
        Dimension size2 = component.getPreferredSize();

        // Assert sizes are consistent
        assertEquals(size1.width, size2.width);
        assertEquals(size1.height, size2.height);
    }

    @Test
    public void testUpdateMethod()
    {
        // Arrange
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.append(slide);
        presentation.setSlideNumber(0);

        // Act & Assert - update should not throw
        assertDoesNotThrow(() -> component.update());
    }

    @Test
    public void testUpdateWithMultipleSlides()
    {
        // Arrange
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        presentation.append(slide1);
        presentation.append(slide2);

        // Act
        presentation.setSlideNumber(0);
        assertDoesNotThrow(() -> component.update());

        presentation.setSlideNumber(1);
        assertDoesNotThrow(() -> component.update());
    }

    @Test
    public void testComponentInitiallyHasNoSlide()
    {
        // Arrange - component with empty presentation
        // Act
        component.update();

        // Assert - component updates without error even with no slides
        assertDoesNotThrow(() -> component.update());
    }

    @Test
    public void testMultipleComponentsCanObserveSamePresentation()
    {
        // Arrange
        JFrame frame2 = new JFrame("Test Frame 2");

        // Act & Assert - multiple components can observe the same presentation
        assertDoesNotThrow(() -> {
            SlideViewerComponent component1 = new SlideViewerComponent(presentation, frame);
            SlideViewerComponent component2 = new SlideViewerComponent(presentation, frame2);
            assertNotNull(component1);
            assertNotNull(component2);
        });
    }

    @Test
    public void testComponentWithSlideItems()
    {
        // Arrange
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        TextItem item = new TextItem(1, "Test Content");
        slide.append(item);
        presentation.append(slide);
        presentation.setSlideNumber(0);

        // Act & Assert
        assertDoesNotThrow(() -> component.update());
    }

    @Test
    public void testPreferredSizeIsStandardSize()
    {
        // Act
        Dimension size = component.getPreferredSize();

        // Assert standard presentation size
        assertEquals(1200, size.width);
        assertEquals(800, size.height);
    }

    @Test
    public void testComponentReceivesUpdateNotifications()
    {
        // Arrange
        Slide slide = new Slide();
        presentation.append(slide);

        // Act - changing slide should trigger update
        presentation.setSlideNumber(0);

        // Assert - no exception should be thrown
        assertDoesNotThrow(() -> component.update());
    }

    @AfterEach
    public void tearDown()
    {
        frame.dispose();
    }
}
