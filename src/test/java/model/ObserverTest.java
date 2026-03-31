package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Observer interface.
 */
public class ObserverTest
{
    @Test
    public void testObserverImplementation()
    {
        // Arrange
        boolean[] updated = {false};
        Observer observer = () -> updated[0] = true;

        // Act
        observer.update();

        // Assert
        assertTrue(updated[0]);
    }

    @Test
    public void testMultipleObserverUpdates()
    {
        // Arrange
        int[] updateCount1 = {0};
        int[] updateCount2 = {0};

        Observer observer1 = () -> updateCount1[0]++;
        Observer observer2 = () -> updateCount2[0]++;

        // Act
        observer1.update();
        observer1.update();
        observer2.update();

        // Assert
        assertEquals(2, updateCount1[0]);
        assertEquals(1, updateCount2[0]);
    }
}
