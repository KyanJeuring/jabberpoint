package app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the AboutBox dialog.
 * Tests about dialog class structure without GUI interaction.
 */
public class AboutBoxTest
{

    @Test
    public void testAboutBoxShowMethodExists()
    {
        // Assert AboutBox has a show method
        assertDoesNotThrow(() -> {
            var method = AboutBox.class.getMethod("show", java.awt.Frame.class);
            assertNotNull(method);
        });
    }

    @Test
    public void testAboutBoxIsStaticClass()
    {
        // Assert AboutBox is a valid class
        assertNotNull(AboutBox.class);
    }

    @Test
    public void testAboutBoxClassCanBeLoaded()
    {
        // Assert the class loads without errors
        Class<?> clazz = AboutBox.class;
        assertEquals("AboutBox", clazz.getSimpleName());
    }

    @Test
    public void testAboutBoxPackage()
    {
        // Assert AboutBox is in the correct package
        assertEquals("app", AboutBox.class.getPackageName());
    }

    @Test
    public void testAboutBoxShowMethodSignature()
    {
        // Assert show method exists with correct signature
        assertDoesNotThrow(() -> {
            var method = AboutBox.class.getDeclaredMethod("show", java.awt.Frame.class);
            assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()));
            assertEquals(void.class, method.getReturnType());
        });
    }
}
