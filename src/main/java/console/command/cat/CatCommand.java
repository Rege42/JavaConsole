package console.command.cat;

import console.command.Command;
import lombok.Getter;

import java.util.List;
import java.util.Set;

public class CatCommand implements Command {

    @Getter
    private final String name = "cat";

    @Getter
    private final String description = "Возвращает содержимое указанного файла или потока ввода.\n" +
            "Структура:\n" +
            "cat <опции> <имя файла>\n" +
            "Доступные опции:\n" +
            "-t замена табуляции на набор символов\n" +
            "-b нумерация непустых строк\n" +
            "-n нумерация всех строк\n" +
            "-e добавление в конец строки символа $\n" +
            "-s удаление повторяющихся пустых строк\n" +
            "Доступные варианты аргументов:\n" +
            "-читабельный файл (например, .txt)\n" +
            "-пустой аргумент (открывается чтение потока ввода)";

    private static CatCommand INSTANCE;

    public static CatCommand getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CatCommand();
        }
        return INSTANCE;
    }

    @Override
    public void executeCommand(Set<String> options, List<String> arguments) {

        new CatRuntime(options, arguments).execute();

    }
}
