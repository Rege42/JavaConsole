package console;

import console.command.cd.CdCommand;
import console.command.runner.CommandExecutor;
import console.utility.CommandCall;
import console.utility.PathResolver;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CdCommandTest {

    private final String sampleDirectoryPath = "src\\test\\samples\\testFolder\\testFolder1";

    @Test
    public void testCdCommand() {

        final var commandArgs = new CommandCall(new CdCommand(),
                new HashSet<>(),
                new ArrayList<>(List.of(sampleDirectoryPath)));

        Path expectedResult = Paths.get(".\\" + sampleDirectoryPath);

        CommandExecutor.execute(commandArgs);

        Assert.assertEquals(expectedResult, new PathResolver().getPath());
    }
}
