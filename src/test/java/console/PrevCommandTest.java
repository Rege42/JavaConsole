package console;

import console.command.echo.EchoCommand;
import console.command.prev.PrevCommand;
import console.command.runner.CommandExecutor;
import console.utility.CommandCall;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PrevCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setPrevCommand() {
        final var commandArgs = new CommandCall(new EchoCommand(),
                new HashSet<>(),
                new ArrayList<>(List.of("echo")));
        CommandExecutor.execute(commandArgs);
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStream() {
        System.setOut(originalOut);
    }

    @Test
    public void testPrevCommand() {

        final var commandArgs = new CommandCall(new PrevCommand(),
                new HashSet<>(),
                new ArrayList<>());

        String expectedResult = "echo\n";

        CommandExecutor.execute(commandArgs);

        Assert.assertEquals(expectedResult, outContent.toString());
    }

}
