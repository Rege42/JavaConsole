package console;

import console.command.echo.EchoCommand;
import console.command.runner.CommandExecutor;
import console.utility.CommandCall;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EchoCommandTest {

    private final String sampleFilePath = "src\\test\\samples\\echo.txt";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void createEchoFile() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStream() {
        System.setOut(originalOut);
    }

    @Test
    public void testEchoCommandSingle() {

        final var commandArgs = new CommandCall(new EchoCommand(),
                new HashSet<>(),
                new ArrayList<>(List.of("echo")));

        String expectedResult = "echo\n";

        CommandExecutor.execute(commandArgs);

        Assert.assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void testEchoCommandFile() {

        try {
            Path path = Path.of(sampleFilePath);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final var commandArgs = new CommandCall(new EchoCommand(),
                new HashSet<>(),
                new ArrayList<>(List.of(sampleFilePath, "echo")));

        String expectedResult = "echo";

        CommandExecutor.execute(commandArgs);

        try {
            final var reader = new BufferedReader(new FileReader(sampleFilePath));
            Assert.assertEquals(expectedResult, reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
