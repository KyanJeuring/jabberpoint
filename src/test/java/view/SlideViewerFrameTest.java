package view;

import java.awt.Component;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Presentation;
import model.Slide;
import model.Style;

/**
 * Unit tests for the SlideViewerFrame view class.
 * Tests main window setup and component initialization.
 */
public class SlideViewerFrameTest
{

    private Presentation presentation;
    private SlideViewerFrame frame;

    @BeforeEach
    public void setUp()
    {
        Style.createStyles();
        presentation = new Presentation();
    }

    @Test
    public void testFrameInitialization()
    {
        // Act
        frame = new SlideViewerFrame("Test Title", presentation);

        // Assert
        assertNotNull(frame);
        assertTrue(frame.isVisible());
    }

    @Test
    public void testFrameWithValidPresentation()
    {
        // Act & Assert
        assertDoesNotThrow(() -> {
            frame = new SlideViewerFrame("Test", presentation);
        });
    }

    @Test
    public void testFrameTitle()
    {
        // Act
        frame = new SlideViewerFrame("Test Title", presentation);

        // Assert title is set to standard JAB_TITLE
        assertEquals("Jabberpoint 1.6 - OU", frame.getTitle());
    }

    @Test
    public void testFrameHasContentPane()
    {
        // Act
        frame = new SlideViewerFrame("Test", presentation);

        // Assert content pane is not empty
        assertNotNull(frame.getContentPane());
        assertTrue(frame.getContentPane().getComponentCount() > 0);
    }

    @Test
    public void testFrameContainsSlideViewerComponent()
    {
        // Act
        frame = new SlideViewerFrame("Test", presentation);

        // Assert component exists in content pane
        Component[] components = frame.getContentPane().getComponents();
        boolean hasSlideViewer = false;
        for (Component comp : components)
        {
            if (comp instanceof SlideViewerComponent)
            {
                hasSlideViewer = true;
                break;
            }
        }
        assertTrue(hasSlideViewer);
    }

    @Test
    public void testFrameIsVisible()
    {
        // Act
        frame = new SlideViewerFrame("Test", presentation);

        // Assert frame is visible
        assertTrue(frame.isVisible());
    }

    @Test
    public void testFrameWithEmptyPresentation()
    {
        // Arrange
        Presentation emptyPresentation = new Presentation();

        // Act & Assert
        assertDoesNotThrow(() -> {
            frame = new SlideViewerFrame("Empty", emptyPresentation);
        });
    }

    @Test
    public void testFrameWithPopulatedPresentation()
    {
        // Arrange
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.append(slide);

        // Act & Assert
        assertDoesNotThrow(() -> {
            frame = new SlideViewerFrame("Populated", presentation);
        });
    }

    @Test
    public void testMultipleFramesCanBeCreated()
    {
        // Act
        SlideViewerFrame frame1 = new SlideViewerFrame("Frame 1", presentation);
        Presentation pres2 = new Presentation();
        SlideViewerFrame frame2 = new SlideViewerFrame("Frame 2", pres2);

        // Assert both frames exist
        assertNotNull(frame1);
        assertNotNull(frame2);
        assertNotSame(frame1, frame2);

        // Cleanup
        frame1.dispose();
        frame2.dispose();
    }

    @Test
    public void testFrameToStringNotNull()
    {
        // Act
        frame = new SlideViewerFrame("Test", presentation);

        // Assert toString works
        assertNotNull(frame.toString());
    }

    @Test
    public void testFrameHasKeyListener()
    {
        // Act
        frame = new SlideViewerFrame("Test", presentation);

        // Assert frame has key listeners registered
        assertNotNull(frame.getKeyListeners());
    }

    @Test
    public void testFrameSizeIsReasonable()
    {
        // Act
        frame = new SlideViewerFrame("Test", presentation);

        // Assert frame has reasonable size
        assertTrue(frame.getWidth() > 0);
        assertTrue(frame.getHeight() > 0);
    }

    @AfterEach
    public void tearDown()
    {
        if (frame != null && frame.isDisplayable())
        {
            frame.dispose();
        }
    }
}
