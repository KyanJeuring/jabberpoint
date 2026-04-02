package model;

import java.awt.Color;
import java.awt.Font;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Style class.
 * Tests style creation, retrieval, and font scaling.
 */
public class StyleTest
{

    @BeforeEach
    public void setUp()
    {
        Style.createStyles();
    }

    @Test
    public void testStyleCreation()
    {
        // Arrange
        int indent = 0;
        Color color = Color.red;
        int points = 48;
        int leading = 20;

        // Act
        Style style = new Style(indent, color, points, leading);

        // Assert
        assertEquals(indent, style.getIndent());
        assertEquals(color, style.getColor());
        assertEquals(points, style.getFontSize());
        assertEquals(leading, style.getLeading());
    }

    @Test
    public void testStyleProperties()
    {
        // Arrange
        Style style = new Style(20, Color.blue, 40, 10);

        // Act & Assert
        assertEquals(20, style.getIndent());
        assertEquals(Color.blue, style.getColor());
        assertEquals(40, style.getFontSize());
        assertEquals(10, style.getLeading());
    }

    @Test
    public void testGetStyleLevel0()
    {
        // Act
        Style style = Style.getStyle(0);

        // Assert
        assertNotNull(style);
        assertEquals(Color.red, style.getColor());
        assertEquals(48, style.getFontSize());
    }

    @Test
    public void testGetStyleLevel1()
    {
        // Act
        Style style = Style.getStyle(1);

        // Assert
        assertNotNull(style);
        assertEquals(Color.blue, style.getColor());
        assertEquals(40, style.getFontSize());
    }

    @Test
    public void testGetStyleLevel2()
    {
        // Act
        Style style = Style.getStyle(2);

        // Assert
        assertNotNull(style);
        assertEquals(Color.black, style.getColor());
        assertEquals(36, style.getFontSize());
    }

    @Test
    public void testGetStyleLevel3()
    {
        // Act
        Style style = Style.getStyle(3);

        // Assert
        assertNotNull(style);
        assertEquals(Color.black, style.getColor());
        assertEquals(30, style.getFontSize());
    }

    @Test
    public void testGetStyleLevel4()
    {
        // Act
        Style style = Style.getStyle(4);

        // Assert
        assertNotNull(style);
        assertEquals(Color.black, style.getColor());
        assertEquals(24, style.getFontSize());
    }

    @Test
    public void testGetStyleOutOfBoundsHigh()
    {
        // Act - Request level higher than available
        Style style = Style.getStyle(100);

        // Assert - Should return the highest level (level 4)
        assertNotNull(style);
        assertEquals(24, style.getFontSize()); // Level 4 font size
    }

    @Test
    public void testGetStyleNegativeLevel()
    {
        // Act & Assert - Negative level should throw ArrayIndexOutOfBoundsException
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Style.getStyle(-1));
    }

    @Test
    public void testGetFont()
    {
        // Arrange
        Style style = new Style(0, Color.red, 48, 20);
        float scale = 1.0f;

        // Act
        Font font = style.getFont(scale);

        // Assert
        assertNotNull(font);
        assertTrue(font.isBold());
    }

    @Test
    public void testGetFontWithScale()
    {
        // Arrange
        Style style = Style.getStyle(0);
        float scale = 2.0f;

        // Act
        Font font = style.getFont(scale);

        // Assert
        assertNotNull(font);
        assertEquals(48 * scale, font.getSize2D(), 0.1);
    }

    @Test
    public void testGetFontWithSmallScale()
    {
        // Arrange
        Style style = Style.getStyle(0);
        float scale = 0.5f;

        // Act
        Font font = style.getFont(scale);

        // Assert
        assertNotNull(font);
        assertEquals(48 * scale, font.getSize2D(), 0.1);
    }

    @Test
    public void testGetFontWithZeroScale()
    {
        // Arrange
        Style style = Style.getStyle(0);
        float scale = 0.0f;

        // Act
        Font font = style.getFont(scale);

        // Assert
        assertNotNull(font);
        assertEquals(0, font.getSize2D(), 0.1);
    }

    @Test
    public void testAllStylesCreated()
    {
        // Assert all 5 style levels are accessible
        for (int level = 0; level < 5; level++)
        {
            Style style = Style.getStyle(level);
            assertNotNull(style, "Style for level " + level + " should not be null");
        }
    }

    @Test
    void testStyleIndents()
    {
        // Act
        Style style0 = Style.getStyle(0);
        Style style1 = Style.getStyle(1);
        Style style2 = Style.getStyle(2);
        Style style3 = Style.getStyle(3);
        Style style4 = Style.getStyle(4);

        // Assert - indents should increase with level
        assertEquals(0, style0.getIndent());
        assertEquals(20, style1.getIndent());
        assertEquals(50, style2.getIndent());
        assertEquals(70, style3.getIndent());
        assertEquals(90, style4.getIndent());
    }

    @Test
    public void testStyleLeading()
    {
        // Act
        Style style0 = Style.getStyle(0);
        Style style1 = Style.getStyle(1);

        // Assert
        assertEquals(20, style0.getLeading());
        assertEquals(10, style1.getLeading());
    }

    @Test
    void testCustomStyle()
    {
        // Arrange
        int customIndent = 50;
        Color customColor = new Color(100, 150, 200);
        int customSize = 32;
        int customLeading = 15;

        // Act
        Style customStyle = new Style(customIndent, customColor, customSize, customLeading);

        // Assert
        assertEquals(customIndent, customStyle.getIndent());
        assertEquals(customColor, customStyle.getColor());
        assertEquals(customSize, customStyle.getFontSize());
        assertEquals(customLeading, customStyle.getLeading());
    }

    @Test
    void testFontIsBold()
    {
        // Arrange
        Style style = Style.getStyle(0);

        // Act
        Font font = style.getFont(1.0f);

        // Assert
        assertTrue(font.isBold());
    }

    @Test
    void testGetFontMultipleScales()
    {
        // Arrange
        Style style = Style.getStyle(0);
        float[] scales = {0.5f, 1.0f, 1.5f, 2.0f};

        // Act & Assert
        for (float scale : scales)
        {
            Font font = style.getFont(scale);
            assertNotNull(font);
            assertEquals(48 * scale, font.getSize2D(), 0.1);
        }
    }
}
