package org.example;

import java.util.Scanner;

public class Main {
    static final String quitCmd = "q:";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for (String userLine; !(userLine = sc.nextLine()).equals(quitCmd); System.out.printf("\nType '%s' to exit\n", quitCmd)) {
            Book userBook = new Book(userLine);

            System.out.println(userBook.countPopularWords(2, 20));
            System.out.println(userBook.countUniqueWords());
            userBook.printStatistics();
        }
    }
}