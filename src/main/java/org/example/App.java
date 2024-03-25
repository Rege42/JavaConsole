package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class App
{
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] arr = s.split(" ");

        ArrayList<String> options = new ArrayList<>();
        ArrayList<String> arguments = new ArrayList<>();

        //поиск в массиве опций и аргументов
        for (int i=1; i< arr.length; i++) {
            if (arr[i].startsWith("-")) {
                options.add(arr[i]);
            } else {
                arguments.add(arr[i]);
            }
        }

        //строка опций
        String optionsLine = "";
        if (!options.isEmpty()) {
            StringBuilder optionsBuffer = new StringBuilder();
            for (String elem: options) {
                optionsBuffer.append(elem).append(" ");
            }
            optionsLine = optionsBuffer.toString();
        }

        //строка аргументов
        String argumentsLine = "";
        if (!arguments.isEmpty()) {
            StringBuilder argumentsBuffer = new StringBuilder();
            for (String elem: arguments) {
                argumentsBuffer.append(elem).append(" ");
            }
            argumentsLine = argumentsBuffer.toString();
        }

        //поиск исполняемой команды
        switch (arr[0]) {
            case "cat":
                catCommand(optionsLine, argumentsLine);
                catClone(argumentsLine);
                break;
            case "ls":
                lsCommand(optionsLine, argumentsLine);
                lsClone(argumentsLine);
                break;
            default:
                System.out.println("Unknown command");
        }
    }

    //Команда Unix cat
    public static void catCommand(String optionsLine, String argumentsLine) {

        //System.out.println("cat " + optionsLine + argumentsLine);

        try {
            Process ps = new ProcessBuilder("cat ", optionsLine, argumentsLine).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void catClone(String argumentsLine) {

        if (Objects.equals(argumentsLine, "")) {
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()) {                 // while scanner scans something. (ends with Ctrl + D)
                String stdin = sc.nextLine();
                System.out.println(stdin);
            }
        } else {
            try {
                FileReader r = new FileReader(argumentsLine);
                BufferedReader br = new BufferedReader(r);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Команда Unix ls
    public static void lsCommand(String optionsLine, String argumentsLine) {

        try {
            Process ps = new ProcessBuilder("ls ", optionsLine, argumentsLine).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void lsClone(String argumentsLine) {

        if (Objects.equals(argumentsLine, ""))
            argumentsLine = ".";

        File folder = new File(argumentsLine);

        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            Arrays.sort(listOfFiles);
            for (File listOfFile : listOfFiles) {
                System.out.println(listOfFile.getName());
            }
        } else {
            System.out.println("Directory is empty");
        }
    }
}
