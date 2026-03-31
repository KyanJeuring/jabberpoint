package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the TextItem class.
 * Tests text item creation, text retrieval, and attribute string generation.
 */
public class TextItemTest
{
    private TextItem textItem;
    private Graphics2D graphics;

    @BeforeEach
    public void setUp()
    {
        Style.createStyles();
        BufferedImage image = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
    }

    @Test
    public void testTextItemWithLevelAndString()
    {
        // Arrange
        int level = 1;
        String text = "Test Text";

        // Act
        textItem = new TextItem(level, text);

        // Assert
        assertEquals(text, textItem.getText());
        assertEquals(level, textItem.getLevel());
    }

    @Test
    public void testDefaultTextItem()
    {
        // Act
        textItem = new TextItem();

        // Assert
        assertNotNull(textItem.getText());
        assertEquals("No Text Given", textItem.getText());
        assertEquals(0, textItem.getLevel());
    }

    @Test
    public void testGetTextWithNormalText()
    {
        // Act
        textItem = new TextItem(0, "Hello World");

        // Assert
        assertEquals("Hello World", textItem.getText());
    }

    @Test
    public void testGetTextWithNullText()
    {
        // Arrange
        textItem = new TextItem(0, null);

        // Act & Assert
        assertEquals("", textItem.getText());
    }

    @Test
    public void testGetTextWithEmptyString()
    {
        // Arrange
        textItem = new TextItem(0, "");

        // Act & Assert
        assertEquals("", textItem.getText());
    }

    @Test
    public void testTextItemWithDifferentLevels()
    {
        // Test different level values
        for (int level = 0; level < 5; level++)
        {
            textItem = new TextItem(level, "Level " + level);
            assertEquals(level, textItem.getLevel());
        }
    }

    @Test
    public void testGetAttributedString()
    {
        // Arrange
        textItem = new TextItem(0, "Formatted Text");
        Style style = Style.getStyle(0);
        float scale = 1.0f;

        // Act
        AttributedString attrStr = textItem.getAttributedString(style, scale);

        // Assert
        assertNotNull(attrStr);
    }

    @Test
    public void testGetAttributedStringWithDifferentScales()
    {
        // Arrange
        textItem = new TextItem(0, "Scalable Text");
        Style style = Style.getStyle(0);

        // Act & Assert
        assertNotNull(textItem.getAttributedString(style, 0.5f));
        assertNotNull(textItem.getAttributedString(style, 1.0f));
        assertNotNull(textItem.getAttributedString(style, 2.0f));
    }

    @Test
    public void testTextItemMultipleLevels()
    {
        // Test level 0 (title)
        textItem = new TextItem(0, "Title");
        assertEquals(0, textItem.getLevel());

        // Test level 1
        textItem = new TextItem(1, "Heading 1");
        assertEquals(1, textItem.getLevel());

        // Test level 4
        textItem = new TextItem(4, "Detail");
        assertEquals(4, textItem.getLevel());
    }

    @Test
    public void testTextItemWithSpecialCharacters()
    {
        // Arrange
        String specialText = "Special @#$%^&*() Characters!";

        // Act
        textItem = new TextItem(0, specialText);

        // Assert
        assertEquals(specialText, textItem.getText());
    }

    @Test
    public void testTextItemWithUnicodeCharacters() {
        // Arrange
        String unicodeText = "Unicode: \u03B1\u03B2\u03B3 (Greek letters)";

        // Act
        textItem = new TextItem(0, unicodeText);

        // Assert
        assertEquals(unicodeText, textItem.getText());
    }

    @Test
    public void testGetAttributedStringLength() {
        // Arrange
        String testText = "Test Length";
        textItem = new TextItem(0, testText);
        Style style = Style.getStyle(0);

        // Act
        AttributedString attrStr = textItem.getAttributedString(style, 1.0f);

        // Assert
        assertNotNull(attrStr);
        assertEquals(testText.length(), attrStr.getIterator().getEndIndex());
    }

    @Test
    public void testGetAttributedStringWithScales() {
        // Arrange
        textItem = new TextItem(0, "Scaled Text");
        Style style = Style.getStyle(0);

        // Act
        AttributedString small = textItem.getAttributedString(style, 0.5f);
        AttributedString normal = textItem.getAttributedString(style, 1.0f);
        AttributedString large = textItem.getAttributedString(style, 2.0f);

        // Assert
        assertNotNull(small);
        assertNotNull(normal);
        assertNotNull(large);
    }

    @Test
    public void testTextItemLevel0() {
        // Arrange & Act
        textItem = new TextItem(0, "Title");

        // Assert
        assertEquals(0, textItem.getLevel());
        assertEquals("Title", textItem.getText());
    }

    @Test
    public void testTextItemLevel4() {
        // Arrange & Act
        textItem = new TextItem(4, "Detail");

        // Assert
        assertEquals(4, textItem.getLevel());
        assertEquals("Detail", textItem.getText());
    }

    @Test
    public void testTextItemEmptyConstruction() {
        // Act
        TextItem item1 = new TextItem();
        TextItem item2 = new TextItem();

        // Assert
        assertEquals(item1.getText(), item2.getText());
        assertEquals("No Text Given", item1.getText());
    }

    @Test
    public void testTextItemWithLongerText() {
        // Arrange
        String longText = "This is a much longer text that might span multiple lines when rendered";

        // Act
        textItem = new TextItem(1, longText);

        // Assert
        assertEquals(longText, textItem.getText());
        assertEquals(1, textItem.getLevel());
    }

    @Test
    public void testGetAttributedStringMultipleLevels() {
        // Arrange
        for (int level = 0; level < 5; level++) {
            textItem = new TextItem(level, "Level " + level);
            Style style = Style.getStyle(level);

            // Act
            AttributedString attrStr = textItem.getAttributedString(style, 1.0f);

            // Assert
            assertNotNull(attrStr);
        }
    }

    @Test
    public void testTextItemWithLongText()
    {
        // Arrange
        String longText = "A".repeat(1000);

        // Act
        textItem = new TextItem(0, longText);

        // Assert
        assertEquals(longText, textItem.getText());
    }

    @Test
    public void testTextItemLevelVsContent()
    {
        // Arrange
        String content = "Test Content";

        // Act & Assert - Verify level and content are independent
        for (int level = 0; level <= 4; level++)
        {
            textItem = new TextItem(level, content);
            assertEquals(level, textItem.getLevel());
            assertEquals(content, textItem.getText());
        }
    }

    @Test
    public void testAttributedStringHasText()
    {
        // Arrange
        String testText = "Attributed Text Test";
        textItem = new TextItem(0, testText);
        Style style = Style.getStyle(0);

        // Act
        AttributedString attrStr = textItem.getAttributedString(style, 1.0f);

        // Assert
        assertNotNull(attrStr);
        // The AttributedString should have been created with the text
    }

    @Test
    public void testTextItemWithoutSettingText()
    {
        // Arrange
        textItem = new TextItem(2, null);

        // Act
        String retrieved = textItem.getText();

        // Assert
        assertEquals("", retrieved);
    }

    @Test
    public void testGetBoundingBoxWithValidGraphics()
    {
        // Arrange
        textItem = new TextItem(0, "Bounding Box Test");
        Style style = Style.getStyle(0);
        float scale = 1.0f;

        // Act
        Rectangle bbox = textItem.getBoundingBox(graphics, null, scale, style);

        // Assert
        assertNotNull(bbox);
        assertTrue(bbox.width >= 0);
        assertTrue(bbox.height >= 0);
    }

    @Test
    public void testGetBoundingBoxWithDifferentScales()
    {
        // Arrange
        textItem = new TextItem(0, "Scaled Bounding Box");
        Style style = Style.getStyle(0);

        // Act & Assert
        Rectangle bbox1 = textItem.getBoundingBox(graphics, null, 0.5f, style);
        Rectangle bbox2 = textItem.getBoundingBox(graphics, null, 1.0f, style);
        Rectangle bbox3 = textItem.getBoundingBox(graphics, null, 2.0f, style);

        assertNotNull(bbox1);
        assertNotNull(bbox2);
        assertNotNull(bbox3);
        assertTrue(bbox1.height > 0);
        assertTrue(bbox2.height > 0);
        assertTrue(bbox3.height > 0);
    }

    @Test
    public void testGetBoundingBoxMultipleLevels()
    {
        // Test bounding box for text items at different levels
        for (int level = 0; level < 5; level++)
        {
            textItem = new TextItem(level, "Level " + level + " Text");
            Style style = Style.getStyle(level);

            // Act
            Rectangle bbox = textItem.getBoundingBox(graphics, null, 1.0f, style);

            // Assert
            assertNotNull(bbox);
            assertTrue(bbox.height >= 0);
        }
    }

    @Test
    public void testDrawWithValidGraphics()
    {
        // Arrange
        textItem = new TextItem(0, "Draw Test");
        Style style = Style.getStyle(0);

        // Act - Should not throw exception
        textItem.draw(0, 0, 1.0f, graphics, style, null);

        // Assert - Just verify no exception was thrown
        assertTrue(true);
    }

    @Test
    public void testDrawWithDifferentPositions()
    {
        // Arrange
        textItem = new TextItem(1, "Position Test");
        Style style = Style.getStyle(1);

        // Act & Assert - Draw at different positions
        textItem.draw(0, 0, 1.0f, graphics, style, null);
        textItem.draw(100, 100, 1.0f, graphics, style, null);
        textItem.draw(500, 300, 1.0f, graphics, style, null);
    }

    @Test
    public void testDrawWithDifferentScales()
    {
        // Arrange
        textItem = new TextItem(2, "Scale Test");
        Style style = Style.getStyle(2);

        // Act & Assert - Draw with different scales
        textItem.draw(10, 10, 0.5f, graphics, style, null);
        textItem.draw(10, 10, 1.0f, graphics, style, null);
        textItem.draw(10, 10, 2.0f, graphics, style, null);
    }

    @Test
    public void testDrawWithEmptyText()
    {
        // Arrange
        textItem = new TextItem(0, "");
        Style style = Style.getStyle(0);

        // Act - Should handle empty text gracefully
        textItem.draw(0, 0, 1.0f, graphics, style, null);

        // Assert
        assertTrue(true);
    }

    @Test
    public void testDrawWithNullText()
    {
        // Arrange
        textItem = new TextItem(0, null);
        Style style = Style.getStyle(0);

        // Act - Should handle null text gracefully
        textItem.draw(0, 0, 1.0f, graphics, style, null);

        // Assert
        assertTrue(true);
    }

    @Test
    public void testBoundingBoxAndDrawConsistency()
    {
        // Arrange
        textItem = new TextItem(1, "Consistency Test");
        Style style = Style.getStyle(1);

        // Act
        Rectangle bbox = textItem.getBoundingBox(graphics, null, 1.0f, style);
        textItem.draw(10, 20, 1.0f, graphics, style, null);

        // Assert - Bounding box should be valid after drawing
        assertNotNull(bbox);
        assertTrue(bbox.width >= 0);
        assertTrue(bbox.height >= 0);
    }

    @Test
    public void testGetBoundingBoxWithLongText()
    {
        // Arrange
        String longText = "This is a very long text that should have a larger bounding box width";
        textItem = new TextItem(0, longText);
        Style style = Style.getStyle(0);

        // Act
        Rectangle bbox = textItem.getBoundingBox(graphics, null, 1.0f, style);

        // Assert
        assertNotNull(bbox);
        assertTrue(bbox.width > 0);
        assertTrue(bbox.height > 0);
    }
}
