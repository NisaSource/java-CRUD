package CRUD;

import java.io.*;
import java.time.Year;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utility {

    static long setEntryNum(String author, String year) throws IOException {
        FileReader inputFile = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(inputFile);

        long entry = 0;
        String data = bufferInput.readLine();
        Scanner dataScanner;
        String primaryKey;

        while(data != null) {
            dataScanner = new Scanner(data);
            dataScanner.useDelimiter(",");
            primaryKey = dataScanner.next();
            dataScanner = new Scanner(primaryKey);
            dataScanner.useDelimiter("_");

            author = author.replaceAll("\\s+", "");

            if (author.equalsIgnoreCase(dataScanner.next()) && year.equalsIgnoreCase(dataScanner.next())) {
                entry = dataScanner.nextInt();
            }

            data = bufferInput.readLine();
        }
        return entry;
    }

    protected static String setYear() throws  IOException {

        Scanner userInput = new Scanner(System.in);
        String inputYear = userInput.nextLine();
        boolean validYear = false;
        while (!validYear) {
            try {
                Year.parse(inputYear);
                validYear = true;
            } catch (Exception e) {
                System.err.println("\nInvalid year input!");
                System.out.print("Please retype the year : ");
                inputYear = userInput.nextLine();
                validYear = false;
            }
        }

        return inputYear;
    }

    static boolean checkBook(String[] keywords, boolean isDisplay) throws IOException {

        FileReader inputFile = new FileReader("database.txt");
        BufferedReader bufferData = new BufferedReader(inputFile);

        String data = bufferData.readLine();
        boolean isExist = false;
        int dataNum = 0;

        if(isDisplay) {
            System.out.println("\n| No |\tYear |\tAuthor         |\tPublisher      |\tTitle");
            System.out.println("---------------------------------------------------------------------------");
        }

        while(data != null) {

            // Check the keyword in the row
            isExist = true;

            for (String keyword:keywords) {
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            // Show match keyword
            if(isExist) {
                if(isDisplay) {
                    dataNum++;

                    StringTokenizer stringToken = new StringTokenizer(data, ",");

                    stringToken.nextToken();
                    System.out.printf("| %2d ", dataNum);
                    System.out.printf("|\t%4s ", stringToken.nextToken());
                    System.out.printf("|\t%-15s", stringToken.nextToken());
                    System.out.printf("|\t%-15s", stringToken.nextToken());
                    System.out.printf("|\t%s", stringToken.nextToken());
                    System.out.print("\n");
                } else {
                    break;
                }
            }
            data = bufferData.readLine();
        }

        if(isDisplay) {
            System.out.println("---------------------------------------------------------------------------");
        }

        return isExist;
    }

    public static boolean getYesOrNo(String message) {
        Scanner userInput = new Scanner(System.in);
        System.out.print("\n" +message+ "(y/n)/ ");
        String userChoice = userInput.next();

        while(!userChoice.equalsIgnoreCase("y") && !userChoice.equalsIgnoreCase("n")) {
            System.err.println("Invalid input!");
            System.out.print("\n" +message+ "(y/n)/ ");
            userChoice = userInput.next();
        }

        return userChoice.equalsIgnoreCase("y");
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception ex) {
            System.err.println("Clear screen failed!");
        }
    }
}
