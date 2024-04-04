package com.openai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RouletteGame {
    private static final int[] numbers = {
        0, 32, 15, 19, 4, 21, 2, 25, 17, 34,
        6, 27, 13, 36, 11, 30, 8, 23, 10, 5,
        24, 16, 33, 1, 20, 14, 31, 9, 22, 18,
        29, 7, 28, 12, 35, 3, 26
    };

    private static final String[] colors = {
        "green", "red", "black", "red", "black", "red", "black", "red", "black", "red",
        "black", "black", "red", "black", "red", "black", "red", "black", "red", "red",
        "black", "red", "black", "red", "black", "red", "black", "red", "black", "black",
        "red", "black", "red", "black", "red", "green"
    };

    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Roulette!");

        while (true) {
            System.out.println("\nSelect your bet:");
            System.out.println("1. Bet on a specific number");
            System.out.println("2. Bet on red or black");
            System.out.println("3. Quit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    betOnNumber();
                    break;
                case 2:
                    betOnColor();
                    break;
                case 3:
                    System.out.println("Thank you for playing Roulette!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }

    private static void betOnNumber() {
        System.out.print("Enter the number you want to bet on (0-36): ");
        int betNumber = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (betNumber < 0 || betNumber > 36) {
            System.out.println("Invalid number. Please choose a number between 0 and 36.");
            return;
        }

        int winningNumber = spinWheel();
        System.out.println("The winning number is: " + winningNumber);

        if (betNumber == winningNumber) {
            System.out.println("Congratulations! You win!");
        } else {
            System.out.println("Sorry, you lose.");
        }
    }

    private static void betOnColor() {
        System.out.print("Enter the color you want to bet on (red or black): ");
        String betColor = scanner.nextLine();

        if (!betColor.equalsIgnoreCase("red") && !betColor.equalsIgnoreCase("black")) {
            System.out.println("Invalid color. Please choose either red or black.");
            return;
        }

        int winningNumber = spinWheel();
        System.out.println("The winning number is: " + winningNumber);

        String winningColor = getNumberColor(winningNumber);
        if (betColor.equalsIgnoreCase(winningColor)) {
            System.out.println("Congratulations! You win!");
        } else {
            System.out.println("Sorry, you lose.");
        }
    }

    private static int spinWheel() {
        return numbers[random.nextInt(numbers.length)];
    }

    private static String getNumberColor(int number) {
        return colors[number];
    }
}
