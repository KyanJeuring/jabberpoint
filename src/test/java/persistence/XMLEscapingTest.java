package persistence;

import model.Presentation;
import model.Slide;
import model.Style;
import model.TextItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

public class XMLEscapingTest {

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() throws IOException {
        Style.createStyles();
        
        // Copy DTD file to temp directory so XML parser can find it
        File sourceDtd = new File("jabberpoint.dtd");
        if (sourceDtd.exists()) {
            Files.copy(sourceDtd.toPath(), 
                      tempDir.resolve("jabberpoint.dtd"), 
                      StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Test
    public void testXMLSpecialCharactersInTitle() throws IOException {
        // Arrange
        Presentation presentation = new Presentation();
        presentation.setTitle("Tom & Jerry <Best Friends>");
        
        Slide slide = new Slide();
        slide.setTitle("Episode \"The Cat's Meow\"");
        slide.append(new TextItem(1, "Jerry said: <Run!>"));
        presentation.append(slide);

        Path testFile = tempDir.resolve("test-escaping.xml");
        XMLAccessor accessor = new XMLAccessor();

        // Act - Save in temp directory where DTD is available
        accessor.saveFile(presentation, testFile.toAbsolutePath().toString());

        // Assert - verify file contains escaped entities
        String content = Files.readString(testFile);
        assertTrue(content.contains("Tom &amp; Jerry &lt;Best Friends&gt;"), 
                "Title should have escaped XML entities");
        assertTrue(content.contains("&quot;The Cat&apos;s Meow&quot;"), 
                "Slide title should have escaped quotes");
        assertTrue(content.contains("&lt;Run!&gt;"), 
                "Text content should have escaped angle brackets");
        
        // Verify it can be loaded back
        Presentation loaded = new Presentation();
        
        // Change working directory to temp for DTD resolution
        String originalDir = System.getProperty("user.dir");
        try {
            System.setProperty("user.dir", tempDir.toAbsolutePath().toString());
            accessor.loadFile(loaded, testFile.toAbsolutePath().toString());
        } finally {
            System.setProperty("user.dir", originalDir);
        }
        
        assertEquals("Tom & Jerry <Best Friends>", loaded.getTitle(), 
                "Title should be unescaped when loaded");
        assertEquals("Episode \"The Cat's Meow\"", loaded.getSlide(0).getTitle(), 
                "Slide title should preserve quotes");
    }

    @Test
    public void testAllXMLEntities() throws IOException {
        // Arrange
        Presentation presentation = new Presentation();
        presentation.setTitle("Test: & < > \" '");
        
        Path testFile = tempDir.resolve("test-entities.xml");
        XMLAccessor accessor = new XMLAccessor();

        // Act
        accessor.saveFile(presentation, testFile.toAbsolutePath().toString());

        // Assert
        String content = Files.readString(testFile);
        assertTrue(content.contains("&amp;"), "Should escape ampersand");
        assertTrue(content.contains("&lt;"), "Should escape less-than");
        assertTrue(content.contains("&gt;"), "Should escape greater-than");
        assertTrue(content.contains("&quot;"), "Should escape double quote");
        assertTrue(content.contains("&apos;"), "Should escape apostrophe");
    }

    @Test
    public void testNullTextHandling() throws IOException {
        // Arrange
        Presentation presentation = new Presentation();
        presentation.setTitle(null);
        
        Path testFile = tempDir.resolve("test-null.xml");
        XMLAccessor accessor = new XMLAccessor();

        // Act & Assert - should not throw NPE
        assertDoesNotThrow(() -> accessor.saveFile(presentation, testFile.toAbsolutePath().toString()));
    }
}
