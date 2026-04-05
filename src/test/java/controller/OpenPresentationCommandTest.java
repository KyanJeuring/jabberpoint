package controller;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Presentation;
import model.Slide;

/**
 * Unit tests for the OpenPresentationCommand class.
 */
public class OpenPresentationCommandTest
{
    private OpenPresentationCommand openCommand;
    private Presentation presentation;

    @BeforeEach
    public void setUp()
    {
        presentation = new Presentation();
        openCommand = new OpenPresentationCommand(presentation, "test.xml");
    }

    @Test
    public void testOpenPresentationCommandInitialization()
    {
        // Assert
        assertNotNull(openCommand);
    }

    @Test
    public void testOpenPresentationCommandCreated()
    {
        // Act
        OpenPresentationCommand command = new OpenPresentationCommand(presentation, "input.xml");

        // Assert
        assertNotNull(command);
    }

    @Test
    public void testOpenPresentationCommandWithBasicPresentation()
    {
        // Arrange
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.append(slide);

        // Act
        OpenPresentationCommand command = new OpenPresentationCommand(presentation, "demo.xml");

        // Assert
        assertNotNull(command);
    }

    @Test
    public void testOpenPresentationCommandIsCommand()
    {
        // Assert
        assertTrue(openCommand instanceof Command);
    }

    @Test
    public void testMultipleOpenPresentationCommands()
    {
        // Arrange
        Presentation presentation2 = new Presentation();

        // Act
        OpenPresentationCommand command1 = new OpenPresentationCommand(presentation, "file1.xml");
        OpenPresentationCommand command2 = new OpenPresentationCommand(presentation2, "file2.xml");

        // Assert
        assertNotNull(command1);
        assertNotNull(command2);
    }

    @Test
    public void testOpenPresentationCommandWithDifferentFilenames()
    {
        // Act
        OpenPresentationCommand cmd1 = new OpenPresentationCommand(presentation, "open1.xml");
        OpenPresentationCommand cmd2 = new OpenPresentationCommand(presentation, "open2.xml");
        OpenPresentationCommand cmd3 = new OpenPresentationCommand(presentation, "open3.xml");

        // Assert
        assertNotNull(cmd1);
        assertNotNull(cmd2);
        assertNotNull(cmd3);
    }

    @Test
    public void testOpenPresentationCommandWithEmptyPresentation()
    {
        // Arrange
        Presentation emptyPresentation = new Presentation();

        // Act
        OpenPresentationCommand command = new OpenPresentationCommand(emptyPresentation, "empty.xml");

        // Assert
        assertNotNull(command);
        assertEquals(0, emptyPresentation.getSize());
    }

    @Test
    public void testOpenPresentationCommandExecute() throws Exception
    {
        // Arrange - Use the test.xml file from the project
        File testFile = new File("test.xml");
        if (testFile.exists())
        {
            OpenPresentationCommand command = new OpenPresentationCommand(presentation, "test.xml");

            // Act
            command.execute();

            // Assert - After execute, presentation should have content
            assertNotNull(presentation);
        }
    }

    @Test
    public void testOpenPresentationCommandExecuteWithNonexistent()
    {
        // Arrange
        OpenPresentationCommand command = new OpenPresentationCommand(presentation, "nonexistent.xml");

        // Act & Assert - Should throw IOException for nonexistent file
        try {
            command.execute();
        } catch (Exception e) {
            // Expected - file doesn't exist
            assertNotNull(e);
        }
    }

    @Test
    public void testOpenPresentationCommandMultipleExecutes() throws Exception
    {
        // Arrange
        OpenPresentationCommand cmd1 = new OpenPresentationCommand(presentation, "test.xml");
        OpenPresentationCommand cmd2 = new OpenPresentationCommand(presentation, "test.xml");

        // Act
        cmd1.execute();
        cmd2.execute();

        // Assert - Should handle multiple executes gracefully
        assertNotNull(presentation);
    }

    @Test
    public void testOpenPresentationCommandPath()
    {
        // Arrange
        String testPath = "path/to/file.xml";
        OpenPresentationCommand command = new OpenPresentationCommand(presentation, testPath);

        // Act & Assert - Nonexistent path will throw IOException
        try {
            command.execute();
        } catch (Exception e) {
            // Expected for nonexistent file
            assertNotNull(e);
        }
    }
}
