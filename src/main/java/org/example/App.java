package org.example;

import java.io.*;
import java.util.*;

public class App
{

    // цветовые настройки текста консоли
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) {

        //System.out.println("Введите команду:");
        //Scanner scanner = new Scanner(System.in);
        //String s = scanner.nextLine();
        //String[] arr = s.split(" ");

        ArrayList<String> options = new ArrayList<>();          // массив с опциями
        ArrayList<String> arguments = new ArrayList<>();        // массив с аргументами

        for (int i=1; i< args.length; i++) {                     // поиск в введенной строке опций и аргументов
            if (args[i].startsWith("-")) {
                options.add(args[i]);
            } else {
                arguments.add(args[i]);
            }
        }

        String optionsLine = "";                                //строка опций
        if (!options.isEmpty()) {
            if (options.size() == 1) {
                optionsLine = options.get(0);
            } else {
                StringBuilder optionsBuffer = new StringBuilder();
                for (String elem: options) {
                    optionsBuffer.append(elem).append(" ");
                }
                optionsLine = optionsBuffer.toString();
            }
        }

        String argumentsLine = "";                              //строка аргументов
        if (!arguments.isEmpty()) {
            if (arguments.size() == 1) {
                argumentsLine = arguments.get(0);
            } else {
                StringBuilder argumentsBuffer = new StringBuilder();
                for (String elem: arguments) {
                    argumentsBuffer.append(elem).append(" ");
                }
                argumentsLine = argumentsBuffer.toString();
            }
        }

        switch (args[0]) {                                       //поиск исполняемой команды
            case "cat":
                //System.out.println("Оригинал:");
                //unixCommand(arr[0], optionsLine, argumentsLine);
                //System.out.println("Копия:");
                catClone(options, argumentsLine);
                break;
            case "ls":
                //System.out.println("Оригинал:");
                //unixCommand(arr[0], optionsLine, argumentsLine);
                //System.out.println("Копия:");
                lsClone(options, argumentsLine, "");
                break;
            default:
                System.out.println("Unknown command");
        }
    }

    // эмуляция команд Unix
    public static void unixCommand(String command, String optionsLine, String argumentsLine) {

        try {
            // создание команды
            ProcessBuilder ps = new ProcessBuilder(command);
            if ((Objects.equals(argumentsLine, ""))&&(!Objects.equals(optionsLine, ""))) {
                ps = new ProcessBuilder(command, optionsLine);
            } else if ((!Objects.equals(argumentsLine, ""))&&(Objects.equals(optionsLine, ""))) {
                ps = new ProcessBuilder(command, argumentsLine);
            } else if ((!Objects.equals(argumentsLine, ""))&&(!Objects.equals(optionsLine, ""))){
                ps = new ProcessBuilder(command, optionsLine, argumentsLine);
            }

            ps.redirectErrorStream(true);                       // объединение потока ввода с потоком ошибок

            Process pr = ps.start();                            // старт работы команды

            // считывание данных с потока ввода
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            pr.waitFor();
            in.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // команда Unix cat
    public static void catClone(ArrayList<String> options, String argumentsLine) {

        int i = 0;                                              // счетчик строк для команд -n и -b
        String lastLine = "something";                          // значение предыдущей строки для команды -s

        if (Objects.equals(argumentsLine, "")) {             // если файл не предоставлен, то данные читаются с потока ввода
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()) {                              // цикл обеспечиваеь конец работы при нажатии Ctrl + D
                String line = sc.nextLine();
                i = catOptions(options, line, lastLine, i);
                lastLine = line;
            }
        } else {
            try {
                FileReader r = new FileReader(argumentsLine);
                BufferedReader br = new BufferedReader(r);
                String line;
                while ((line = br.readLine()) != null) {        // цикл чтения данных из файла
                    i = catOptions(options, line, lastLine, i);
                    lastLine = line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // обработчик функциий команды Unix cat
    public static int catOptions (ArrayList<String> options, String line, String lastLine, int i) {

        // -t замена табуляцию на набор символов
        if ((options.contains("-t")) && (line.contains("\t"))) {
            line = line.replaceAll("\t", "tab");
        }

        StringBuilder lineBuffer = new StringBuilder(line);

        // -b нумерация непустых строк
        if ((options.contains("-b")) && (!line.equals(""))) {
            i++;
            lineBuffer.insert(0, "\t"+i+" ");
        }

        // -e добавление в конец строки символа $
        if (options.contains("-e")) {
            lineBuffer.append("$");
        }

        // -s удаление повторяющихся пустых строк
        if (options.contains("-s") && (!line.equals("") || !lastLine.equals(""))) {
            // -n нумерация всех строк
            if ((options.contains("-n")) && (!options.contains("-b"))) {
                i++;
                lineBuffer.insert(0, "\t"+i+" ");
            }
            System.out.println(lineBuffer);
        } else if (!options.contains("-s")) {
            if ((options.contains("-n")) && (!options.contains("-b"))) {
                i++;
                lineBuffer.insert(0, "\t"+i+" ");
            }
            System.out.println(lineBuffer);
        }
        return i;                                               // вовзращает индекс строки для нумерации
    }

    //Команда Unix ls
    public static void lsClone(ArrayList<String> options, String argumentsLine, String spacing) {

        String path;
        if (Objects.equals(argumentsLine, ""))               // если аргумент пустой, то устанавливается значение по умолчанию
            argumentsLine = ".";

        path = argumentsLine;                                   // строка с путем
        File folder = new File(path);

        File[] listOfFiles = folder.listFiles();                // извлечение файлов из каталога
        if (listOfFiles != null) {
            // -Х сортировка по алфавиту
            // -r сортировка в обратном порядке
            if ((options.contains("-X")) && (!options.contains("-r"))) {
                Arrays.sort(listOfFiles);
            } else if ((options.contains("-X")) && (options.contains("-r"))) {
                Arrays.sort(listOfFiles, Collections.<File>reverseOrder());
            }
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isDirectory() && !listOfFile.isHidden()) {
                    System.out.println(spacing + ANSI_BLUE + listOfFile.getName() + ANSI_RESET);
                    // -R рекурсивный показ файлов в подкаталогах
                    if (options.contains("-R")) {
                        lsClone(options, path + "/" + listOfFile.getName(), spacing + "\t");
                    }
                }
                if (listOfFile.isFile() && !listOfFile.isHidden()) {
                    System.out.println(spacing + ANSI_GREEN + listOfFile.getName() + ANSI_RESET);
                }
            }
        } else {
            System.out.println(spacing + "Directory is empty");
        }
    }
}
