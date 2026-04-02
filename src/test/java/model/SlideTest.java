package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Slide model class.
 * Tests slide item management and properties.
 */
public class SlideTest
{
    private Slide slide;
    private Graphics2D graphics;

    @BeforeEach
    public void setUp()
    {
        slide = new Slide();
        Style.createStyles();
        BufferedImage image = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
    }

    @Test
    public void testSlideConstants()
    {
        // Assert slide dimensions are as expected
        assertEquals(1200, Slide.WIDTH);
        assertEquals(800, Slide.HEIGHT);
    }

    @Test
    public void testInitialState()
    {
        // Assert initial state
        assertNull(slide.getTitle());
        assertEquals(0, slide.getSize());
        assertNotNull(slide.getSlideItems());
        assertTrue(slide.getSlideItems().isEmpty());
    }

    @Test
    public void testSetTitle()
    {
        // Arrange
        String title = "Test Slide Title";

        // Act
        slide.setTitle(title);

        // Assert
        assertEquals(title, slide.getTitle());
    }

    @Test
    public void testSetTitleNull()
    {
        // Act
        slide.setTitle(null);

        // Assert
        assertNull(slide.getTitle());
    }

    @Test
    public void testAppendSlideItem()
    {
        // Arrange
        TextItem item = new TextItem(0, "Test Item");

        // Act
        slide.append(item);

        // Assert
        assertEquals(1, slide.getSize());
        assertEquals(item, slide.getSlideItem(0));
    }

    @Test
    public void testAppendMultipleItems()
    {
        // Arrange
        TextItem item1 = new TextItem(0, "Item 1");
        TextItem item2 = new TextItem(1, "Item 2");
        TextItem item3 = new TextItem(2, "Item 3");

        // Act
        slide.append(item1);
        slide.append(item2);
        slide.append(item3);

        // Assert
        assertEquals(3, slide.getSize());
        assertEquals(item1, slide.getSlideItem(0));
        assertEquals(item2, slide.getSlideItem(1));
        assertEquals(item3, slide.getSlideItem(2));
    }

    @Test
    public void testAppendTextByLevelAndMessage()
    {
        // Arrange
        int level = 1;
        String message = "Test Message";

        // Act
        slide.append(level, message);

        // Assert
        assertEquals(1, slide.getSize());
        SlideItem item = slide.getSlideItem(0);
        assertNotNull(item);
        assertTrue(item instanceof TextItem);
    }

    @Test
    public void testGetSlideItems()
    {
        // Arrange
        TextItem item1 = new TextItem(0, "Item 1");
        TextItem item2 = new TextItem(1, "Item 2");
        slide.append(item1);
        slide.append(item2);

        // Act
        List<SlideItem> items = slide.getSlideItems();

        // Assert
        assertEquals(2, items.size());
        assertEquals(item1, items.get(0));
        assertEquals(item2, items.get(1));
    }

    @Test
    public void testGetSlideItemsIsUnmodifiable()
    {
        // Arrange
        TextItem item = new TextItem(0, "Item");
        slide.append(item);
        List<SlideItem> items = slide.getSlideItems();

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> items.add(new TextItem(0, "New")));
    }

    @Test
    public void testGetSlideItemOutOfBounds()
    {
        // Arrange
        TextItem item = new TextItem(0, "Item");
        slide.append(item);

        // Act & Assert
        assertThrows(IndexOutOfBoundsException.class, () -> slide.getSlideItem(1));
        assertThrows(IndexOutOfBoundsException.class, () -> slide.getSlideItem(-1));
    }

    @Test
    public void testSlideWithMultipleTextItems()
    {
        // Arrange - Test with multiple text items at different levels
        TextItem item1 = new TextItem(0, "Title");
        TextItem item2 = new TextItem(1, "Bullet 1");
        TextItem item3 = new TextItem(2, "Bullet 2");

        // Act
        slide.append(item1);
        slide.append(item2);
        slide.append(item3);

        // Assert
        assertEquals(3, slide.getSize());
        assertEquals(item1, slide.getSlideItem(0));
        assertEquals(item2, slide.getSlideItem(1));
        assertEquals(item3, slide.getSlideItem(2));
    }

    @Test
    public void testEmptySlideSize()
    {
        // Assert
        assertEquals(0, slide.getSize());
    }

    @Test
    public void testSlideItemsOrderPreserved()
    {
        // Arrange
        TextItem[] items = new TextItem[5];
        for (int i = 0; i < 5; i++)
        {
            items[i] = new TextItem(i, "Item " + i);
            slide.append(items[i]);
        }

        // Act & Assert
        for (int i = 0; i < 5; i++)
        {
            assertEquals(items[i], slide.getSlideItem(i));
        }
    }

    @Test
    public void testMultipleTitles()
    {
        // Act
        slide.setTitle("Title 1");
        assertEquals("Title 1", slide.getTitle());

        slide.setTitle("Title 2");
        assertEquals("Title 2", slide.getTitle());

        // Assert final title
        assertEquals("Title 2", slide.getTitle());
    }

    @Test
    public void testGetSlideItemOperations()
    {
        // Arrange
        TextItem item1 = new TextItem(0, "Item 1");
        TextItem item2 = new TextItem(1, "Item 2");
        TextItem item3 = new TextItem(2, "Item 3");

        // Act
        slide.append(item1);
        slide.append(item2);
        slide.append(item3);

        // Assert - Verify all items can be retrieved
        assertEquals(item1, slide.getSlideItem(0));
        assertEquals(item2, slide.getSlideItem(1));
        assertEquals(item3, slide.getSlideItem(2));
    }

    @Test
    public void testSlideCapacity()
    {
        // Arrange
        int itemCount = 10;
        TextItem[] items = new TextItem[itemCount];

        // Act - Add many items
        for (int i = 0; i < itemCount; i++)
        {
            items[i] = new TextItem(i % 5, "Item " + i);
            slide.append(items[i]);
        }

        // Assert - Size should reflect all items
        assertEquals(itemCount, slide.getSize());

        // Verify each item
        for (int i = 0; i < itemCount; i++)
        {
            assertEquals(items[i], slide.getSlideItem(i));
        }
    }

    @Test
    public void testAppendAtDifferentLevels()
    {
        // Act - Append text items at different levels
        slide.append(0, "Title");
        slide.append(1, "Heading");
        slide.append(2, "Subheading");
        slide.append(3, "Bullet");
        slide.append(4, "Sub-bullet");

        // Assert - Verify size and content
        assertEquals(5, slide.getSize());
        
        SlideItem item = slide.getSlideItem(0);
        assertNotNull(item);
        assertTrue(item instanceof TextItem);
    }

    @Test
    public void testSlideTitle()
    {
        // Arrange
        String title = "Presentation Title";

        // Act
        slide.setTitle(title);

        // Assert
        assertEquals(title, slide.getTitle());
        
        // Change title and verify
        String newTitle = "New Title";
        slide.setTitle(newTitle);
        assertEquals(newTitle, slide.getTitle());
    }

    @Test
    public void testSlideSize()
    {
        // Assert initial size
        assertEquals(0, slide.getSize());

        // Add items and verify size increases
        slide.append(new TextItem(0, "Item 1"));
        assertEquals(1, slide.getSize());

        slide.append(new TextItem(1, "Item 2"));
        assertEquals(2, slide.getSize());

        slide.append(1, "Item 3");
        assertEquals(3, slide.getSize());
    }

    @Test
    public void testBoundaryConditions()
    {
        // Add single item
        TextItem item = new TextItem(0, "Only Item");
        slide.append(item);

        // Test accessing first and last (same in this case)
        assertEquals(item, slide.getSlideItem(0));
        assertEquals(1, slide.getSize());
    }

    @Test
    public void testDrawWithValidTitle()
    {
        // Arrange
        slide.setTitle("Test Slide Title");
        slide.append(new TextItem(1, "Content Item"));
        Rectangle area = new Rectangle(0, 0, 1200, 800);

        // Act
        slide.draw(graphics, area, null);

        // Assert - Just verify no exception
        assertTrue(true);
    }

    @Test
    public void testDrawWithSingleWordTitle()
    {
        // Arrange - Draw with single word title
        slide.setTitle("Title");
        slide.append(new TextItem(1, "Content"));
        Rectangle area = new Rectangle(0, 0, 1200, 800);

        // Act
        slide.draw(graphics, area, null);

        // Assert
        assertTrue(true);
    }

    @Test
    public void testDrawWithMultipleItems()
    {
        // Arrange
        slide.setTitle("Multi-Item Slide");
        slide.append(new TextItem(1, "First Item"));
        slide.append(new TextItem(2, "Second Item"));
        slide.append(new TextItem(3, "Third Item"));
        Rectangle area = new Rectangle(0, 0, 1200, 800);

        // Act
        slide.draw(graphics, area, null);

        // Assert
        assertTrue(true);
    }

    @Test
    public void testDrawWithDifferentAreaSizes()
    {
        // Arrange
        slide.setTitle("Scaled Slide");
        slide.append(new TextItem(1, "Content"));

        // Act & Assert - Draw with different area sizes
        slide.draw(graphics, new Rectangle(0, 0, 600, 400), null);
        slide.draw(graphics, new Rectangle(0, 0, 1200, 800), null);
        slide.draw(graphics, new Rectangle(0, 0, 2400, 1600), null);
    }

    @Test
    public void testDrawWithEmptySlideContent()
    {
        // Arrange - Set a title but no content items
        slide.setTitle("Empty Slide");
        Rectangle area = new Rectangle(0, 0, 1200, 800);

        // Act
        slide.draw(graphics, area, null);

        // Assert
        assertTrue(true);
    }

    @Test
    public void testSlideDimensionsConstants()
    {
        // Assert slide dimensions
        assertEquals(1200, Slide.WIDTH);
        assertEquals(800, Slide.HEIGHT);
        assertTrue(Slide.WIDTH > 0);
        assertTrue(Slide.HEIGHT > 0);
    }

    @Test
    public void testDrawingPreservesSlideState()
    {
        // Arrange
        slide.setTitle("State Test");
        TextItem item = new TextItem(1, "Item");
        slide.append(item);
        int sizeBefore = slide.getSize();

        // Act
        Rectangle area = new Rectangle(0, 0, 1200, 800);
        slide.draw(graphics, area, null);

        // Assert - State should be unchanged
        assertEquals(sizeBefore, slide.getSize());
        assertEquals("State Test", slide.getTitle());
        assertEquals(item, slide.getSlideItem(0));
    }

    @Test
    public void testDrawWithVariousItemLevels()
    {
        // Arrange
        slide.setTitle("Various Levels");
        for (int level = 0; level < 5; level++)
        {
            slide.append(new TextItem(level, "Level " + level));
        }
        Rectangle area = new Rectangle(0, 0, 1200, 800);

        // Act
        slide.draw(graphics, area, null);

        // Assert
        assertEquals(5, slide.getSize());
    }

    @Test
    public void testDrawWithNullArea()
    {
        // Arrange
        slide.setTitle("Null Area Test");

        // Act & Assert - Should handle gracefully or throw specific exception
        try
        {
            slide.draw(graphics, null, null);
        }
        catch (NullPointerException e)
        {
            // Expected behavior
            assertTrue(true);
        }
    }

    @Test
    public void testDrawWithSmallArea()
    {
        // Arrange
        slide.setTitle("Small Area");
        slide.append(new TextItem(1, "Content"));
        Rectangle area = new Rectangle(0, 0, 100, 100);

        // Act
        slide.draw(graphics, area, null);

        // Assert
        assertTrue(true);
    }

    @Test
    public void testDrawWithLargeArea()
    {
        // Arrange
        slide.setTitle("Large Area");
        slide.append(new TextItem(1, "Content"));
        Rectangle area = new Rectangle(0, 0, 4800, 3200);

        // Act
        slide.draw(graphics, area, null);

        // Assert
        assertTrue(true);
    }
}
