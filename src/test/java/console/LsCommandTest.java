package console;

import console.command.ls.LsCommand;
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

public class LsCommandTest {

    private final String sampleDirectoryPath = "src\\test\\samples\\testFolder";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStream() {
        System.setOut(originalOut);
    }

    @Test
    public void testLsCommandBasic() {

        final var commandArgs = new CommandCall(new LsCommand(),
                new HashSet<>(),
                new ArrayList<>(List.of(sampleDirectoryPath)));

        String expectedResult = " root\r\n" +
                "  \u001B[32mtest1.txt\u001B[0m\r\n" +
                "  \u001B[34mtestFolder1\u001B[0m\r\n" +
                "  \u001B[34mtestFolder2\u001B[0m\r\n" +
                "  \u001B[34mtestFolder3\u001B[0m\r\n";

        CommandExecutor.execute(commandArgs);

        Assert.assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void testLsCommandSort() {

        final var commandArgs = new CommandCall(new LsCommand(),
                new HashSet<>(List.of("-X", "-r")),
                new ArrayList<>(List.of(sampleDirectoryPath)));

        String expectedResult = " root\r\n" +
                "  \u001B[34mtestFolder3\u001B[0m\r\n" +
                "  \u001B[34mtestFolder2\u001B[0m\r\n" +
                "  \u001B[34mtestFolder1\u001B[0m\r\n" +
                "  \u001B[32mtest1.txt\u001B[0m\r\n";

        CommandExecutor.execute(commandArgs);

        Assert.assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void testLsCommandRecursion() {

        final var commandArgs = new CommandCall(new LsCommand(),
                new HashSet<>(List.of("-R")),
                new ArrayList<>(List.of(sampleDirectoryPath)));

        String expectedResult = " root\r\n" +
                "  \u001B[32mtest1.txt\u001B[0m\r\n" +
                "  \u001B[34mtestFolder1\u001B[0m\r\n" +
                "   \u001B[32mtest11.txt\u001B[0m\r\n" +
                "   \u001B[32mtest12.txt\u001B[0m\r\n" +
                "  \u001B[34mtestFolder2\u001B[0m\r\n" +
                "  \u001B[34mtestFolder3\u001B[0m\r\n" +
                "   \u001B[32mtest31.txt\u001B[0m\r\n" +
                "   \u001B[34mtestFolder31\u001B[0m\r\n" +
                "    \u001B[32mtestA.txt\u001B[0m\r\n" +
                "    \u001B[32mtestB.txt\u001B[0m\r\n" +
                "    \u001B[32mtestC.txt\u001B[0m\r\n";

        CommandExecutor.execute(commandArgs);

        Assert.assertEquals(expectedResult, outContent.toString());
    }
}
