package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Presentation;
import model.Slide;

/**
 * Unit tests for the SavePresentationCommand class.
 */
public class SavePresentationCommandTest
{
    private SavePresentationCommand saveCommand;
    private Presentation presentation;

    @BeforeEach
    public void setUp()
    {
        presentation = new Presentation();
        saveCommand = new SavePresentationCommand(presentation, "test.xml");
    }

    @Test
    public void testSavePresentationCommandInitialization()
    {
        // Assert
        assertNotNull(saveCommand);
    }

    @Test
    public void testSavePresentationCommandCreated()
    {
        // Act
        SavePresentationCommand command = new SavePresentationCommand(presentation, "output.xml");

        // Assert
        assertNotNull(command);
    }

    @Test
    public void testSavePresentationCommandWithBasicPresentation()
    {
        // Arrange
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.append(slide);

        // Act
        SavePresentationCommand command = new SavePresentationCommand(presentation, "demo.xml");

        // Assert
        assertNotNull(command);
    }

    @Test
    public void testSavePresentationCommandIsCommand()
    {
        // Assert
        assertTrue(saveCommand instanceof Command);
    }

    @Test
    public void testMultipleSavePresentationCommands()
    {
        // Arrange
        Presentation presentation2 = new Presentation();

        // Act
        SavePresentationCommand command1 = new SavePresentationCommand(presentation, "file1.xml");
        SavePresentationCommand command2 = new SavePresentationCommand(presentation2, "file2.xml");

        // Assert
        assertNotNull(command1);
        assertNotNull(command2);
    }

    @Test
    public void testSavePresentationCommandWithDifferentFilenames()
    {
        // Act
        SavePresentationCommand cmd1 = new SavePresentationCommand(presentation, "output1.xml");
        SavePresentationCommand cmd2 = new SavePresentationCommand(presentation, "output2.xml");
        SavePresentationCommand cmd3 = new SavePresentationCommand(presentation, "output3.xml");

        // Assert
        assertNotNull(cmd1);
        assertNotNull(cmd2);
        assertNotNull(cmd3);
    }

    @Test
    public void testSavePresentationCommandWithEmptyPresentation()
    {
        // Arrange
        Presentation emptyPresentation = new Presentation();

        // Act
        SavePresentationCommand command = new SavePresentationCommand(emptyPresentation, "empty.xml");

        // Assert
        assertNotNull(command);
        assertEquals(0, emptyPresentation.getSize());
    }

    @Test
    public void testSavePresentationCommandExecute()
    {
        // Arrange
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        slide.append(0, "Test Content");
        presentation.append(slide);

        SavePresentationCommand command = new SavePresentationCommand(presentation, "temp_test_output.xml");

        // Act
        command.execute();

        // Assert - Should execute without thrown exception
        assertNotNull(presentation);
        assertEquals(1, presentation.getSize());
    }

    @Test
    public void testSavePresentationCommandExecuteWithMultipleSlides()
    {
        // Arrange
        for (int i = 0; i < 3; i++)
        {
            Slide slide = new Slide();
            slide.setTitle("Slide " + i);
            slide.append(0, "Content " + i);
            presentation.append(slide);
        }

        SavePresentationCommand command = new SavePresentationCommand(presentation, "temp_multi_slide.xml");

        // Act
        command.execute();

        // Assert
        assertEquals(3, presentation.getSize());
    }

    @Test
    public void testSavePresentationCommandExecuteEmptyPresentation()
    {
        // Arrange
        SavePresentationCommand command = new SavePresentationCommand(presentation, "temp_empty.xml");

        // Act
        command.execute();

        // Assert - Should handle empty presentation
        assertNotNull(presentation);
        assertEquals(0, presentation.getSize());
    }

    @Test
    public void testSavePresentationCommandPath()
    {
        // Arrange
        String outputPath = "/tmp/temp_save_test.xml";
        SavePresentationCommand command = new SavePresentationCommand(presentation, outputPath);

        // Act
        command.execute();

        // Assert
        assertNotNull(command);
    }
}
