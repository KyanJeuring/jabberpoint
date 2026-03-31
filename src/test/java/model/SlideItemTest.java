package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the SlideItem abstract base class.
 * Tests through concrete implementations (TextItem and BitmapItem).
 */
public class SlideItemTest
{
    @Test
    public void testSlideItemCreationWithLevel()
    {
        // Arrange & Act
        TextItem item = new TextItem(2, "Test");

        // Assert
        assertEquals(2, item.getLevel());
    }

    @Test
    public void testSlideItemDefaultLevel()
    {
        // Arrange & Act
        TextItem item = new TextItem();

        // Assert
        assertEquals(0, item.getLevel());
    }

    @Test
    public void testSlideItemWithDifferentLevels()
    {
        // Test items with various levels
        for (int level = 0; level <= 4; level++)
        {
            TextItem item = new TextItem(level, "Level " + level);
            assertEquals(level, item.getLevel());
        }
    }

    @Test
    public void testSlideItemNegativeLevel()
    {
        // Arrange & Act
        TextItem item = new TextItem(-1, "Negative Level");

        // Assert
        assertEquals(-1, item.getLevel());
    }

    @Test
    public void testSlideItemLargeLevel()
    {
        // Arrange & Act
        TextItem item = new TextItem(100, "Large Level");

        // Assert
        assertEquals(100, item.getLevel());
    }

    @Test
    public void testMultipleItemsWithSameLevelDifferentContent()
    {
        // Arrange
        TextItem item1 = new TextItem(1, "Content 1");
        TextItem item2 = new TextItem(1, "Content 2");

        // Assert
        assertEquals(1, item1.getLevel());
        assertEquals(1, item2.getLevel());
        assertNotEquals(item1.getText(), item2.getText());
    }

    @Test
    public void testMultipleItemsWithDifferentLevelsSameContent()
    {
        // Arrange
        String content = "Same Content";
        TextItem item0 = new TextItem(0, content);
        TextItem item1 = new TextItem(1, content);
        TextItem item2 = new TextItem(2, content);

        // Assert
        assertEquals(content, item0.getText());
        assertEquals(content, item1.getText());
        assertEquals(content, item2.getText());

        assertNotEquals(item0.getLevel(), item1.getLevel());
        assertNotEquals(item1.getLevel(), item2.getLevel());
    }

    @Test
    public void testSlideItemInstanceOfSlideItem()
    {
        // Arrange & Act
        TextItem textItem = new TextItem(0, "Text");

        // Assert
        assertEquals(true, textItem instanceof SlideItem);
    }

    @Test
    public void testSlideItemWithZeroLevel()
    {
        // Arrange & Act
        TextItem item = new TextItem(0, "Zero Level");

        // Assert
        assertEquals(0, item.getLevel());
    }

    @Test
    public void testSlideItemLevelBoundary()
    {
        // Test boundary levels
        TextItem minLevel = new TextItem(0, "Min");
        TextItem maxLevel = new TextItem(4, "Max");
        TextItem beyondMax = new TextItem(5, "Beyond");

        assertEquals(0, minLevel.getLevel());
        assertEquals(4, maxLevel.getLevel());
        assertEquals(5, beyondMax.getLevel());
    }

    @Test
    public void testConsistentLevelRetrievalMultipleCalls()
    {
        // Arrange
        TextItem item = new TextItem(3, "Test");

        // Act & Assert - Level should be consistent across multiple calls
        assertEquals(3, item.getLevel());
        assertEquals(3, item.getLevel());
        assertEquals(3, item.getLevel());
    }
}
