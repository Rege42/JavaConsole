package console;

import console.command.cat.CatCommand;
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


public class CatCommandTest {

    private final String sampleFilePath = "src\\test\\samples\\test.txt";
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
    public void testCatCommandBasic() {

        final var commandArgs = new CommandCall(new CatCommand(),
                new HashSet<>(),
                new ArrayList<>(List.of(sampleFilePath)));

        String expectedResult = "string one\r\n" +
                "string   two\r\n" +
                "\r\n" +
                "string 4\r\n" +
                "string 5\r\n" +
                "\r\n" +
                "\r\n" +
                "string      8\r\n";

        CommandExecutor.execute(commandArgs);

        Assert.assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void testCatCommandT() {

        final var commandArgs = new CommandCall(new CatCommand(),
                new HashSet<>(List.of("-t")),
                new ArrayList<>(List.of(sampleFilePath)));

        String expectedResult = "string one\r\n" +
                "stringTABtwo\r\n" +
                "\r\n" +
                "string 4\r\n" +
                "string 5\r\n" +
                "\r\n" +
                "\r\n" +
                "stringTABTAB8\r\n";

        CommandExecutor.execute(commandArgs);

        Assert.assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void testCatCommandN() {

        final var commandArgs = new CommandCall(new CatCommand(),
                new HashSet<>(List.of("-n")),
                new ArrayList<>(List.of(sampleFilePath)));

        String expectedResult = "\t1 string one\r\n" +
                "\t2 string   two\r\n" +
                "\t3 \r\n" +
                "\t4 string 4\r\n" +
                "\t5 string 5\r\n" +
                "\t6 \r\n" +
                "\t7 \r\n" +
                "\t8 string      8\r\n";

        CommandExecutor.execute(commandArgs);

        Assert.assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void testCatCommandB() {

        final var commandArgs = new CommandCall(new CatCommand(),
                new HashSet<>(List.of("-b")),
                new ArrayList<>(List.of(sampleFilePath)));

        String expectedResult = "\t1 string one\r\n" +
                "\t2 string   two\r\n" +
                "\r\n" +
                "\t3 string 4\r\n" +
                "\t4 string 5\r\n" +
                "\r\n" +
                "\r\n" +
                "\t5 string      8\r\n";

        CommandExecutor.execute(commandArgs);

        Assert.assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void testCatCommandE() {

        final var commandArgs = new CommandCall(new CatCommand(),
                new HashSet<>(List.of("-e")),
                new ArrayList<>(List.of(sampleFilePath)));

        String expectedResult = "string one$\r\n" +
                "string   two$\r\n" +
                "$\r\n" +
                "string 4$\r\n" +
                "string 5$\r\n" +
                "$\r\n" +
                "$\r\n" +
                "string      8$\r\n";

        CommandExecutor.execute(commandArgs);

        Assert.assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void testCatCommandS() {

        final var commandArgs = new CommandCall(new CatCommand(),
                new HashSet<>(List.of("-s")),
                new ArrayList<>(List.of(sampleFilePath)));

        String expectedResult = "string one\r\n" +
                "string   two\r\n" +
                "\r\n" +
                "string 4\r\n" +
                "string 5\r\n" +
                "\r\n" +
                "string      8\r\n";

        CommandExecutor.execute(commandArgs);

        Assert.assertEquals(expectedResult, outContent.toString());
    }
}
