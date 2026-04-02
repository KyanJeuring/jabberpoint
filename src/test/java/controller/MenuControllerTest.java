package controller;

import java.awt.Frame;
import java.awt.MenuBar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Presentation;
import model.Slide;

/**
 * Unit tests for the MenuController class.
 * Tests menu bar initialization and menu item creation.
 */
public class MenuControllerTest
{
    private MenuController menuController;
    private Presentation presentation;
    private Frame testFrame;

    @BeforeEach
    public void setUp()
    {
        presentation = new Presentation();
        testFrame = new Frame();
        menuController = new MenuController(testFrame, presentation);
    }

    @Test
    public void testMenuControllerInitialization()
    {
        // Assert
        assertNotNull(menuController);
    }

    @Test
    public void testMenuControllerWithPresentation()
    {
        // Arrange
        Presentation pres = new Presentation();
        Frame frame = new Frame();

        // Act
        MenuController controller = new MenuController(frame, pres);

        // Assert
        assertNotNull(controller);
    }

    @Test
    public void testMenuControllerIsMenuBar()
    {
        // Assert
        assertNotNull(menuController);
        assertTrue(menuController instanceof MenuBar);
    }

    @Test
    public void testMenuControllerFileMenuConstant()
    {
        // Assert
        assertEquals("File", MenuController.FILE);
    }

    @Test
    public void testMenuControllerViewMenuConstant()
    {
        // Assert
        assertEquals("View", MenuController.VIEW);
    }

    @Test
    public void testMenuControllerHelpMenuConstant()
    {
        // Assert
        assertEquals("Help", MenuController.HELP);
    }

    @Test
    public void testMenuControllerAllConstants()
    {
        // Assert - Verify all menu constants exist
        assertEquals("About", MenuController.ABOUT);
        assertEquals("File", MenuController.FILE);
        assertEquals("Exit", MenuController.EXIT);
        assertEquals("Go to", MenuController.GOTO);
        assertEquals("Help", MenuController.HELP);
        assertEquals("New", MenuController.NEW);
        assertEquals("Next", MenuController.NEXT);
        assertEquals("Open", MenuController.OPEN);
        assertEquals("Page number?", MenuController.PAGENR);
        assertEquals("Prev", MenuController.PREV);
        assertEquals("Save", MenuController.SAVE);
        assertEquals("View", MenuController.VIEW);
    }

    @Test
    public void testMenuControllerMultipleInstances()
    {
        // Arrange
        Presentation pres1 = new Presentation();
        Presentation pres2 = new Presentation();
        Frame frame = new Frame();

        // Act
        MenuController ctrl1 = new MenuController(frame, pres1);
        MenuController ctrl2 = new MenuController(frame, pres2);

        // Assert
        assertNotNull(ctrl1);
        assertNotNull(ctrl2);
    }

    @Test
    public void testMenuControllerWithDifferentFrames()
    {
        // Arrange
        Presentation pres = new Presentation();
        Frame frame1 = new Frame();
        Frame frame2 = new Frame();

        // Act
        MenuController ctrl1 = new MenuController(frame1, pres);
        MenuController ctrl2 = new MenuController(frame2, pres);

        // Assert
        assertNotNull(ctrl1);
        assertNotNull(ctrl2);
    }

    @Test
    public void testMenuControllerFunctionality()
    {
        // Arrange
        MenuController controller = new MenuController(testFrame, presentation);

        // Assert - Controller should be created and operational
        assertNotNull(controller);
        assertEquals(0, presentation.getSize());
    }

    @Test
    public void testMenuItemNames()
    {
        // Verify all menu item constants for typos and consistency
        assertNotNull(MenuController.ABOUT);
        assertNotNull(MenuController.FILE);
        assertNotNull(MenuController.EXIT);
        assertNotNull(MenuController.GOTO);
        assertNotNull(MenuController.HELP);
        assertNotNull(MenuController.NEW);
        assertNotNull(MenuController.NEXT);
        assertNotNull(MenuController.OPEN);
        assertNotNull(MenuController.PAGENR);
        assertNotNull(MenuController.PREV);
        assertNotNull(MenuController.SAVE);
        assertNotNull(MenuController.VIEW);
    }

    @Test
    public void testMenuControllerMenubarCount()
    {
        // Menus should be added to the menu bar
        assertNotNull(menuController);
        assertTrue(menuController.getMenuCount() > 0);
    }

    @Test
    public void testMenuItemConstantValues()
    {
        // Verify constant values match expected strings
        assertEquals("File", MenuController.FILE);
        assertEquals("New", MenuController.NEW);
        assertEquals("Open", MenuController.OPEN);
        assertEquals("Save", MenuController.SAVE);
        assertEquals("Exit", MenuController.EXIT);
        assertEquals("View", MenuController.VIEW);
        assertEquals("Next", MenuController.NEXT);
        assertEquals("Prev", MenuController.PREV);
        assertEquals("Help", MenuController.HELP);
        assertEquals("About", MenuController.ABOUT);
    }

    @Test
    public void testMenuControllerComplexMenu()
    {
        // Test with a more complex presentation
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        slide1.append(0, "Content 1");
        presentation.append(slide1);

        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        slide2.append(0, "Content 2");
        presentation.append(slide2);

        MenuController controller = new MenuController(testFrame, presentation);
        assertNotNull(controller);
        assertTrue(controller.getMenuCount() > 0);
    }

    @Test
    public void testMenuControllerWithEmptyPresentation()
    {
        // Test menu behavior with empty presentation
        MenuController controller = new MenuController(testFrame, presentation);
        assertNotNull(controller);
        assertEquals(0, presentation.getSize());
    }
}
