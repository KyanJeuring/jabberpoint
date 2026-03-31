package controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Presentation;
import model.Slide;

/**
 * Unit tests for the PreviousSlideCommand.
 * Tests slide navigation backward.
 */
public class PreviousSlideCommandTest
{
    private Presentation presentation;
    private PreviousSlideCommand command;

    @BeforeEach
    public void setUp()
    {
        presentation = new Presentation();
        command = new PreviousSlideCommand(presentation);
    }

    @Test
    public void testExecuteMovesBackSlide()
    {
        // Arrange
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(1);

        // Act
        command.execute();

        // Assert
        assertEquals(0, presentation.getSlideNumber());
        assertEquals(slide1, presentation.getCurrentSlide());
    }

    @Test
    public void testExecuteAtFirstSlideDoesNotMove()
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
        presentation.setSlideNumber(2);

        // Act
        command.execute(); // 2 -> 1
        assertEquals(1, presentation.getSlideNumber());

        command.execute(); // 1 -> 0
        assertEquals(0, presentation.getSlideNumber());

        command.execute(); // Should not move below 0
        assertEquals(0, presentation.getSlideNumber());

        // Assert
        assertEquals(slide1, presentation.getCurrentSlide());
    }

    @Test
    public void testExecuteWithEmptyPresentation()
    {
        // Arrange - empty presentation

        // Act
        command.execute();

        // Assert
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
        presentation.setSlideNumber(9);

        // Act & Assert
        for (int i = 0; i < 15; i++)
        {
            assertDoesNotThrow(() -> command.execute());
        }
    }

    @Test
    public void testPreviousNextCombination()
    {
        // Arrange
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        Slide slide3 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.append(slide3);
        presentation.setSlideNumber(1);

        NextSlideCommand nextCommand = new NextSlideCommand(presentation);
        PreviousSlideCommand prevCommand = new PreviousSlideCommand(presentation);

        // Act
        nextCommand.execute(); // 1 -> 2
        assertEquals(2, presentation.getSlideNumber());

        prevCommand.execute(); // 2 -> 1
        assertEquals(1, presentation.getSlideNumber());

        prevCommand.execute(); // 1 -> 0
        assertEquals(0, presentation.getSlideNumber());

        nextCommand.execute(); // 0 -> 1
        assertEquals(1, presentation.getSlideNumber());

        // Assert
        assertEquals(slide2, presentation.getCurrentSlide());
    }
}
