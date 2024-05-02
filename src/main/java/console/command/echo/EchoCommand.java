package console.command.echo;

import console.command.Command;
import console.utility.PathResolver;
import lombok.Getter;

import java.io.*;
import java.util.List;
import java.util.Set;

public class EchoCommand implements Command {

    @Getter
    private final String name = "echo";

    @Getter
    private final String description = "Возвращает введенную строку. Если кроме строки указан файл, строка будет записана в его конец.\n" +
            "Структура:\n" +
            "echo <имя файла> <строка>\n" +
            "Доступные варианты аргументов:\n" +
            "-строка\n" +
            "-текстовый файл и строка";

    public void echoBuffered(Writer writer, String message) {

        final var bufferedWriter = new BufferedWriter(writer);
        try {
            bufferedWriter.write(message+"\n");
            bufferedWriter.flush();
            if (writer instanceof FileWriter) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void executeCommand(Set<String> options, List<String> arguments) {

        if (arguments.size() == 1) {
            echoBuffered(new OutputStreamWriter(System.out), arguments.get(0));
            return;
        } else if (arguments.size() == 2) {

            final var file = new PathResolver().resolvePath(arguments.get(0)).toFile();
            try {
                echoBuffered(new FileWriter(file, true), arguments.get(1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        System.out.println("echo command summoned incorrectly");

    }
}
