package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CatCommand implements Command {

    public static void catEmpty(ArrayList<String> options, int lineCount, String lastLine) {

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {                              // цикл обеспечиваеь конец работы при нажатии Ctrl + D
            String line = sc.nextLine();
            lineCount = catOptions(options, line, lastLine, lineCount);
            lastLine = line;
        }
    }

    public static void catFull(ArrayList<String> options, String argumentsLine, int lineCount, String lastLine) {

        try {
            FileReader r = new FileReader(argumentsLine);
            BufferedReader br = new BufferedReader(r);
            String line;
            while ((line = br.readLine()) != null) {        // цикл чтения данных из файла
                lineCount = catOptions(options, line, lastLine, lineCount);
                lastLine = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // обработчик функциий команды Unix cat
    public static int catOptions(ArrayList<String> options, String line, String lastLine, int lineCount) {

        // -t замена табуляцию на набор символов
        if ((options.contains("-t")) && (line.contains("\t"))) {
            line = line.replaceAll("\t", "tab");
        }

        StringBuilder lineBuffer = new StringBuilder(line);

        // -b нумерация непустых строк
        if ((options.contains("-b")) && (!line.equals(""))) {
            lineCount++;
            lineBuffer.insert(0, "\t" + lineCount + " ");
        }

        // -e добавление в конец строки символа $
        if (options.contains("-e")) {
            lineBuffer.append("$");
        }

        // -s удаление повторяющихся пустых строк
        if (options.contains("-s") && (!line.equals("") || !lastLine.equals(""))) {
            // -n нумерация всех строк
            if ((options.contains("-n")) && (!options.contains("-b"))) {
                lineCount++;
                lineBuffer.insert(0, "\t" + lineCount + " ");
            }
            System.out.println(lineBuffer);
        } else if (!options.contains("-s")) {
            if ((options.contains("-n")) && (!options.contains("-b"))) {
                lineCount++;
                lineBuffer.insert(0, "\t" + lineCount + " ");
            }
            System.out.println(lineBuffer);
        }
        return lineCount;                                               // вовзращает индекс строки для нумерации
    }

    @Override
    public void executeCommand(ArrayList<String> options, ArrayList<String> arguments) {
        int lineCount = 0;                                      // счетчик строк для команд -n и -b
        String lastLine = "something";                          // значение предыдущей строки для команды -s

        if (Objects.equals(arguments.get(0), "")) {             // если файл не предоставлен, то данные читаются с потока ввода
            catEmpty(options, lineCount, lastLine);
        } else {
            catFull(options, arguments.get(0), lineCount, lastLine);
        }
    }
}
