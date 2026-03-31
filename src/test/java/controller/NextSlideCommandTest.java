package controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Presentation;
import model.Slide;

/**
 * Unit tests for the NextSlideCommand.
 * Tests slide navigation forward.
 */
public class NextSlideCommandTest
{
    private Presentation presentation;
    private NextSlideCommand command;

    @BeforeEach
    public void setUp()
    {
        presentation = new Presentation();
        command = new NextSlideCommand(presentation);
    }

    @Test
    public void testExecuteAdvancesSlide()
    {
        // Arrange
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(0);

        // Act
        command.execute();

        // Assert
        assertEquals(1, presentation.getSlideNumber());
        assertEquals(slide2, presentation.getCurrentSlide());
    }

    @Test
    public void testExecuteAtLastSlideDoesNotAdvance()
    {
        // Arrange
        Slide slide = new Slide();
        presentation.append(slide);
        presentation.setSlideNumber(0);

        // Act
        command.execute();

        // Assert
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void testExecuteMultipleTimes()
    {
        // Arrange
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        Slide slide3 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.append(slide3);

        // Act
        command.execute(); // 0 -> 1
        assertEquals(1, presentation.getSlideNumber());

        command.execute(); // 1 -> 2
        assertEquals(2, presentation.getSlideNumber());

        command.execute(); // Should not advance beyond 2
        assertEquals(2, presentation.getSlideNumber());

        // Assert
        assertEquals(slide3, presentation.getCurrentSlide());
    }

    @Test
    public void testExecuteWithEmptyPresentation()
    {
        // Arrange - empty presentation

        // Act
        command.execute();

        // Assert - slide number should remain 0 or valid
        assertNull(presentation.getCurrentSlide());
    }

    @Test
    public void testCommandCanBeExecutedMultipleTimesWithoutError()
    {
        // Arrange
        for (int i = 0; i < 10; i++)
        {
            presentation.append(new Slide());
        }
        
        // Act & Assert
        for (int i = 0; i < 15; i++)
        {
            assertDoesNotThrow(() -> command.execute());
        }
    }
}
