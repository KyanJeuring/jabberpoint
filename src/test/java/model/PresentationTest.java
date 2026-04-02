package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the Presentation model class.
 * Tests the observer pattern, slide navigation, and slide management.
 */
public class PresentationTest
{
    private Presentation presentation;

    @Mock
    private Observer mockObserver;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        presentation = new Presentation();
    }

    @Test
    public void testInitialState()
    {
        // Assert initial state
        assertEquals(0, presentation.getSize());
        assertNull(presentation.getTitle());
        assertEquals(0, presentation.getSlideNumber());
        assertNull(presentation.getCurrentSlide());
    }

    @Test
    public void testSetTitle()
    {
        // Arrange
        String expectedTitle = "Test Presentation";
        presentation.addObserver(mockObserver);

        // Act
        presentation.setTitle(expectedTitle);

        // Assert
        assertEquals(expectedTitle, presentation.getTitle());
        verify(mockObserver).update();
    }

    @Test
    public void testAppendSlide()
    {
        // Arrange
        Slide slide = new Slide();
        slide.setTitle("Slide 1");
        presentation.addObserver(mockObserver);

        // Act
        presentation.append(slide);

        // Assert
        assertEquals(1, presentation.getSize());
        assertNotNull(presentation.getCurrentSlide());
        assertEquals(slide, presentation.getSlide(0));
        verify(mockObserver).update();
    }

    @Test
    public void testAppendMultipleSlides()
    {
        // Arrange
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        Slide slide3 = new Slide();

        // Act
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.append(slide3);

        // Assert
        assertEquals(3, presentation.getSize());
    }

    @Test
    public void testGetSlideOutOfBounds()
    {
        // Arrange
        Slide slide = new Slide();
        presentation.append(slide);

        // Act & Assert
        assertNull(presentation.getSlide(-1));
        assertNull(presentation.getSlide(1));
        assertNull(presentation.getSlide(100));
    }

    @Test
    public void testSetSlideNumber()
    {
        // Arrange
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.addObserver(mockObserver);
        reset(mockObserver); // Reset after previous notification

        // Act
        presentation.setSlideNumber(1);

        // Assert
        assertEquals(1, presentation.getSlideNumber());
        assertEquals(slide2, presentation.getCurrentSlide());
        verify(mockObserver).update();
    }

    @Test
    public void testSetSlideNumberOutOfBounds()
    {
        // Arrange
        Slide slide = new Slide();
        presentation.append(slide);
        presentation.addObserver(mockObserver);
        reset(mockObserver);

        // Act
        presentation.setSlideNumber(100);

        // Assert
        assertEquals(0, presentation.getSlideNumber()); // Should not change
        verify(mockObserver, never()).update();
    }

    @Test
    public void testSetSlideNumberNegative()
    {
        // Arrange
        Slide slide = new Slide();
        presentation.append(slide);
        presentation.addObserver(mockObserver);
        reset(mockObserver);

        // Act
        presentation.setSlideNumber(-1);

        // Assert
        assertEquals(0, presentation.getSlideNumber()); // Should not change
        verify(mockObserver, never()).update();
    }

    @Test
    public void testNextSlide()
    {
        // Arrange
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.addObserver(mockObserver);
        reset(mockObserver);

        // Act
        presentation.nextSlide();

        // Assert
        assertEquals(1, presentation.getSlideNumber());
        verify(mockObserver).update();
    }

    @Test
    public void testNextSlideAtEnd()
    {
        // Arrange
        Slide slide = new Slide();
        presentation.append(slide);
        presentation.setSlideNumber(0);
        presentation.addObserver(mockObserver);
        reset(mockObserver);

        // Act
        presentation.nextSlide();

        // Assert
        assertEquals(0, presentation.getSlideNumber()); // Should not advance
        verify(mockObserver, never()).update();
    }

    @Test
    public void testPrevSlide()
    {
        // Arrange
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(1);
        presentation.addObserver(mockObserver);
        reset(mockObserver);

        // Act
        presentation.prevSlide();

        // Assert
        assertEquals(0, presentation.getSlideNumber());
        verify(mockObserver).update();
    }

    @Test
    public void testPrevSlideAtStart()
    {
        // Arrange
        Slide slide = new Slide();
        presentation.append(slide);
        presentation.setSlideNumber(0);
        presentation.addObserver(mockObserver);
        reset(mockObserver);

        // Act
        presentation.prevSlide();

        // Assert
        assertEquals(0, presentation.getSlideNumber()); // Should not go back
        verify(mockObserver, never()).update();
    }

    @Test
    public void testClear()
    {
        // Arrange
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.addObserver(mockObserver);
        reset(mockObserver);

        // Act
        presentation.clear();

        // Assert
        assertEquals(0, presentation.getSize());
        assertNull(presentation.getCurrentSlide());
        verify(mockObserver).update();
    }

    @Test
    public void testAddObserver()
    {
        // Arrange
        Observer observer1 = mock(Observer.class);
        Observer observer2 = mock(Observer.class);
        Slide slide = new Slide();

        // Act
        presentation.addObserver(observer1);
        presentation.addObserver(observer2);
        presentation.append(slide);

        // Assert
        verify(observer1).update();
        verify(observer2).update();
    }

    @Test
    public void testRemoveObserver()
    {
        // Arrange
        Observer observer = mock(Observer.class);
        Slide slide = new Slide();
        presentation.addObserver(observer);
        reset(observer);

        // Act
        presentation.removeObserver(observer);
        presentation.append(slide);

        // Assert
        verify(observer, never()).update();
    }

    @Test
    public void testMultipleObserverNotifications()
    {
        // Arrange
        presentation.addObserver(mockObserver);

        // Act & Assert
        presentation.setTitle("Title 1");
        verify(mockObserver, times(1)).update();

        presentation.setTitle("Title 2");
        verify(mockObserver, times(2)).update();

        Slide slide = new Slide();
        presentation.append(slide);
        verify(mockObserver, times(3)).update();
    }
}
