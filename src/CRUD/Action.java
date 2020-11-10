package CRUD;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Action {

    public static void updateBook() throws IOException {

        // Take data from original database
        File database = new File("database.txt");
        FileReader inputFile = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(inputFile);

        // Create temporary database
        File tempDB = new File("tempDB.txt");
        FileWriter outputFile = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(outputFile);

        // Show data
        System.out.println("Book list : ");
        showBook();

        // Take user input
        Scanner userInput = new Scanner(System.in);
        System.out.println("\nType book number that you want to update : ");
        int updateNum = userInput.nextInt();

        // Show data to update
        String data = bufferedInput.readLine();
        int entryCounts = 0;

        while (data != null) {
            entryCounts++;

            StringTokenizer getRidOfComa = new StringTokenizer(data, ",");

            // Show entryCounts == updateNum
            if (updateNum == entryCounts) {
                System.out.println("\nBook you want to update : ");
                System.out.println("----------------------------");
                System.out.println("Reference   : " + getRidOfComa.nextToken());
                System.out.println("Year        : " + getRidOfComa.nextToken());
                System.out.println("Author      : " + getRidOfComa.nextToken());
                System.out.println("Publisher   : " + getRidOfComa.nextToken());
                System.out.println("Title       : " + getRidOfComa.nextToken());

                // Take new input from user
                String[] fieldData = {"year", "author", "publisher", "title"};
                String[] tempData = new String[4];

                // Refresh token
                getRidOfComa = new StringTokenizer(data, ",");
                String originalData = getRidOfComa.nextToken();

                for (int i = 0; i < fieldData.length; i++) {

                    boolean isUpdate = Utility.getYesOrNo("Do you want to update " + fieldData[i] + "? ");
                    originalData = getRidOfComa.nextToken();

                    if (isUpdate) {

                        if (fieldData[i].equalsIgnoreCase("year")) {

                            System.out.print("Year [YYYY] : ");
                            tempData[i] = Utility.setYear();

                        } else {

                            userInput = new Scanner(System.in);
                            System.out.print("\nNew " + fieldData[i] + " : ");
                            tempData[i] = userInput.nextLine();
                        }

                    } else {

                        tempData[i] = originalData;
                    }
                }

                // Show new data
                getRidOfComa = new StringTokenizer(data, ",");
                getRidOfComa.nextToken();

                System.out.println("\nNew data : ");
                System.out.println("----------------------------");
                System.out.println("Year        : " + getRidOfComa.nextToken() + "               --> " + tempData[0]);
                System.out.println("Author      : " + getRidOfComa.nextToken() + "               --> " + tempData[1]);
                System.out.println("Publisher   : " + getRidOfComa.nextToken() + "               --> " + tempData[2]);
                System.out.println("Title       : " + getRidOfComa.nextToken() + "           --> " + tempData[3]);

                boolean isUpdate = Utility.getYesOrNo("Are you sure you want to update the book ? ");

                if (isUpdate) {
                    // Check new data in the database
                    boolean isExist = Utility.checkBook(tempData, false);

                    if (isExist) {
                        System.err.println("Already exist, terminated the updating process");
                    } else {
                        long entryNum = Utility.setEntryNum(tempData[1], tempData[0]) + 1;

                        String noSpaceAuthor = tempData[1].replaceAll("\\s+", "");
                        String primaryKey = noSpaceAuthor+"_"+tempData[0]+"_"+entryNum;

                        bufferedOutput.write(primaryKey + "," + tempData[0] + "," + tempData[1] + "," + tempData[2] + "," + tempData[3]);
                    }

                } else {
                    // Copy data
                    bufferedOutput.write(data);
                }

            } else {
                // Copy data
                bufferedOutput.write(data);
            }

            bufferedOutput.newLine();
            data = bufferedInput.readLine();
        }
        bufferedOutput.flush();

        // Delete original file
        database.delete();
        // Rename temporary database
        tempDB.renameTo(database);
    }

    public static void deleteBook() throws IOException {

        // Take data from the original database
        File database = new File("database.txt");
        FileReader inputFile = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(inputFile);

        // Create temporary database
        File tempDB = new File("tempDB.txt");
        FileWriter outputFile = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(outputFile);

        // Show data
        System.out.println("Book list : ");
        showBook();

        // Take input from user to delete data
        Scanner userInput = new Scanner(System.in);
        System.out.print("\nPlease type the book's number you want to delete : ");
        int deleteNum = userInput.nextInt();

        // Loop over the database to skip data that want to deleted
        boolean isFound = false;
        int entryCounts = 0;
        String data = bufferedInput.readLine();
        while (data != null) {
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer getRidOfComa = new StringTokenizer(data, ",");

            // Show selected data
            if (deleteNum == entryCounts) {
                System.out.println("\nBook you want to delete : ");
                System.out.println("----------------------------");
                System.out.println("Reference   :" + getRidOfComa.nextToken());
                System.out.println("Author      :" + getRidOfComa.nextToken());
                System.out.println("Title       :" + getRidOfComa.nextToken());
                System.out.println("Publisher   :" + getRidOfComa.nextToken());
                System.out.println("Year        :" + getRidOfComa.nextToken());

                isDelete = Utility.getYesOrNo("Are you sure want to delete this book? ");
                isFound = true;
            }

            if (isDelete) {
                // Skipped data to temporary database
                System.out.println("Successfully deleted selected book!");
            } else {
                // Move data to temporary database
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }
            data = bufferedInput.readLine();

        }

        if (!isFound) {
            System.err.println("Not Found!");
        }

        bufferedOutput.flush();

        // Delete original file
        database.delete();
        // Rename temporary database
        tempDB.renameTo(database);

    }

    public static void showBook() throws IOException {

        FileReader inputFile;
        BufferedReader bufferData;

        try {
            inputFile = new FileReader("database.txt");
            bufferData = new BufferedReader(inputFile);
        } catch (Exception ex) {
            System.err.println("File not found!");
            System.err.println("Please add some book : ");
            addBook();
            return;
        }

        System.out.println("\n| No |\tYear |\tAuthor         |\tPublisher      |\tTitle");
        System.out.println("---------------------------------------------------------------------------");

        String data = bufferData.readLine();
        int dataNum = 0;
        while (data != null) {
            dataNum++;

            StringTokenizer stringToken = new StringTokenizer(data, ",");

            stringToken.nextToken();
            System.out.printf("| %2d ", dataNum);
            System.out.printf("|\t%4s ",stringToken.nextToken());
            System.out.printf("|\t%-15s",stringToken.nextToken());
            System.out.printf("|\t%-15s",stringToken.nextToken());
            System.out.printf("|\t%s",stringToken.nextToken());
            System.out.print("\n");

            data = bufferData.readLine();
        }

        System.out.println("---------------------------------------------------------------------------");
    }

    public static void addBook() throws IOException {
        FileWriter outputFile = new FileWriter("database.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(outputFile);

        Scanner userInput = new Scanner(System.in);
        String author, title, publisher, year;

        System.out.print("Author : ");
        author = userInput.nextLine();
        System.out.print("Title : ");
        title = userInput.nextLine();
        System.out.print("Publisher : ");
        publisher = userInput.nextLine();
        System.out.print("Year [YYYY] : ");
        year = Utility.setYear();

        String[] keywords = {year+","+author+","+publisher+","+title};
        System.out.println(Arrays.toString(keywords));

        boolean isExist = Utility.checkBook(keywords, false);

        // Write book in database
        if (!isExist) {
            long entryNum = Utility.setEntryNum(author, year) + 1;

            String noSpaceAuthor = author.replaceAll("\\s+", "");
            String primaryKey = noSpaceAuthor+"_"+year+"_"+entryNum;

            System.out.println("\nYou will add this data to the database : ");
            System.out.println("-------------------------------------------");
            System.out.println("Primary key : " + primaryKey);
            System.out.println("Year        : " + year);
            System.out.println("Author      : " + author);
            System.out.println("Title       : " + title);
            System.out.println("Publisher   : " + publisher);

            boolean isAdd = Utility.getYesOrNo("Do you want to add the data above? ");
            if (isAdd) {
                bufferOutput.write(primaryKey + "," + year + "," + author + "," + publisher + "," + title);
                bufferOutput.newLine();
                bufferOutput.flush();
            }

        } else {
            System.out.println("Book is already exist with the following data : ");
            Utility.checkBook(keywords, true);
        }
        bufferOutput.close();
    }

    public static void findBook() throws IOException {

        // Take keyword from user
        Scanner userInput = new Scanner(System.in);
        System.out.print("Input book keyword : ");
        String findString = userInput.nextLine();

        String[] keywords = findString.split("\\s+");

        // Check the keyword in database
        Utility.checkBook(keywords, true);
    }
}