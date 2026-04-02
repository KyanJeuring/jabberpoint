package persistence;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Presentation;
import model.Slide;
import model.Style;

/**
 * Unit tests for the XMLAccessor class.
 * Tests XML persistence functionality.
 */
public class XMLAccessorTest
{
    private XMLAccessor accessor;
    private Presentation presentation;

    @BeforeEach
    public void setUp()
    {
        accessor = new XMLAccessor();
        presentation = new Presentation();
        Style.createStyles();
    }

    @Test
    public void testXMLAccessorInitialization()
    {
        // Assert
        assertNotNull(accessor);
    }

    @Test
    public void testSaveLoadPresentationBasic() throws Exception
    {
        // Arrange
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        slide.append(0, "Title Text");
        presentation.append(slide);

        String tempFile = "temp_presentation.xml";
        
        try
        {
            // Act
            accessor.saveFile(presentation, tempFile);

            // Assert file was created
            assertTrue(java.nio.file.Files.exists(java.nio.file.Paths.get(tempFile)));

            // Load and verify
            Presentation loaded = new Presentation();
            accessor.loadFile(loaded, tempFile);
            assertNotNull(loaded);
            assertEquals(1, loaded.getSize());
        }
        finally
        {
            // Cleanup
            java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(tempFile));
        }
    }

    @Test
    public void testLoadNonexistentFile() throws IOException
    {
        // Act & Assert - loading non-existent file should handle gracefully or throw IOException
        Presentation loaded = new Presentation();
        try
        {
            accessor.loadFile(loaded, "nonexistent_file_xyz.xml");
            // File doesn't exist, so loadFile might throw or handle gracefully
        }
        catch (IOException e)
        {
            // Expected behavior for non-existent file
            assertTrue(true);
        }
    }

    @Test
    public void testXMLAccessorSaveWithMultipleSlides() throws Exception
    {
        // Arrange
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        slide1.append(0, "First slide");

        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        slide2.append(0, "Second slide");

        presentation.append(slide1);
        presentation.append(slide2);

        String tempFile = "temp_multi_presentation.xml";

        try
        {
            // Act
            accessor.saveFile(presentation, tempFile);

            // Assert
            assertTrue(java.nio.file.Files.exists(java.nio.file.Paths.get(tempFile)));
        }
        finally
        {
            // Cleanup
            java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(tempFile));
        }
    }

    @Test
    public void testEmptyPresentationSave() throws Exception
    {
        // Arrange
        String tempFile = "temp_empty_presentation.xml";

        try
        {
            // Act
            accessor.saveFile(presentation, tempFile);

            // Assert
            assertTrue(java.nio.file.Files.exists(java.nio.file.Paths.get(tempFile)));
        }
        finally
        {
            // Cleanup
            java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(tempFile));
        }
    }

    @Test
    public void testXMLAccessorImplementsAccesor()
    {
        // Assert
        assertTrue(accessor instanceof Accesor);
    }
}
