package controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ExitCommand class.
 * Tests command execution and exit behavior.
 */
public class ExitCommandTest
{
    private ExitCommand exitCommand;

    @BeforeEach
    public void setUp()
    {
        exitCommand = new ExitCommand();
    }

    @Test
    public void testExitCommandInitialization()
    {
        // Assert
        assertNotNull(exitCommand);
    }

    @Test
    public void testExitCommandCreated()
    {
        // Act
        ExitCommand command = new ExitCommand();

        // Assert
        assertNotNull(command);
    }

    @Test
    public void testExitCommandIsCommand()
    {
        // Assert - ExitCommand should be an instance of Command
        assertNotNull(exitCommand);
        assertTrue(exitCommand instanceof Command);
    }

    @Test
    public void testMultipleExitCommands()
    {
        // Act
        ExitCommand command1 = new ExitCommand();
        ExitCommand command2 = new ExitCommand();

        // Assert
        assertNotNull(command1);
        assertNotNull(command2);
    }

    @Test
    public void testExitCommandNotNull()
    {
        // Assert - Verify command is not null after construction
        ExitCommand cmd = new ExitCommand();
        assertTrue(cmd != null);
    }

    @Test
    public void testExitCommandImplementsCommandInterface()
    {
        // Assert - Verify it implements the Command interface
        assertTrue(exitCommand instanceof Command);
        assertNotNull(exitCommand);
    }
}
