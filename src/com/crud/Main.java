package com.crud;

import java.io.IOException;
import java.util.Scanner;
import CRUD.Action;
import CRUD.Utility;


public class Main {

    public static void main(String[] args) throws IOException {

        Scanner userInput = new Scanner(System.in);
        String userChoice;
        boolean isContinue = true;

        while(isContinue) {

            Utility.clearScreen();

            System.out.println("\n======================");
            System.out.println("\tLIBRARY DATABASE");
            System.out.println("======================");
            System.out.println("1.\tExpand list");
            System.out.println("2.\tAdd book");
            System.out.println("3.\tUpdate book");
            System.out.println("4.\tSearch book");
            System.out.println("5.\tDelete book");

            System.out.print("\n\nYour choice: ");
            userChoice = userInput.next();

            switch (userChoice) {
                case "1":
                    System.out.println("======================");
                    System.out.println("BOOK LIST");
                    System.out.println("======================");
                    Action.showBook();
                    break;
                case "2":
                    System.out.println("======================");
                    System.out.println("ADD BOOK");
                    System.out.println("======================");
                    Action.addBook();
                    Action.showBook();
                    break;
                case "3":
                    System.out.println("======================");
                    System.out.println("UPDATE BOOK");
                    System.out.println("======================");

                    Action.updateBook();
                    break;
                case "4":
                    System.out.println("======================");
                    System.out.println("SEARCH BOOK");
                    System.out.println("======================");

                    Action.findBook();
                    break;
                case "5":
                    System.out.println("======================");
                    System.out.println("DELETE BOOK");
                    System.out.println("======================");

                    Action.deleteBook();
                    break;
                default:
                    System.err.println("\nInput not found.\nPlease choose [1-5]");
            }

            isContinue = Utility.getYesOrNo("Do you want to continue ");
        }

    }

}
