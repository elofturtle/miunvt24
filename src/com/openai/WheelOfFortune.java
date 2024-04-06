package com.openai;

import java.util.Random;
import java.util.Scanner;

public class WheelOfFortune {

    private static final String[] puzzles = {"HELLO WORLD", "JAVA PROGRAMMING", "WHEEL OF FORTUNE", "OPENAI CHATGPT"};
    private static final int[] wheelValues = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, -1, -2}; // -1 for Bankrupt, -2 for Lose a Turn
    private static String currentPuzzle;
    private static String displayPuzzle;
    private static int currentPlayer = 0;
    private static int[] playerScores = {0, 0, 0};


    private static int spinTheWheel() {
        Random random = new Random();
        return wheelValues[random.nextInt(wheelValues.length)];
    }

    private static int revealLetters(String guess) {
        int count = 0;
        for (int i = 0; i < currentPuzzle.length(); i++) {
            if (currentPuzzle.charAt(i) == guess.charAt(0)) {
                displayPuzzle = displayPuzzle.substring(0, i) + guess.charAt(0) + displayPuzzle.substring(i + 1);
                count++;
            }
        }
        return count;
    }

    private static void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % 3;
    }

    private static boolean isPuzzleSolved() {
        return displayPuzzle.equals(currentPuzzle);
    }

	
	  public static void main(String[] args) { new WheelOfFortune().startGame(); }
	 

    private void startGame() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        currentPuzzle = puzzles[random.nextInt(puzzles.length)];
        displayPuzzle = currentPuzzle.replaceAll("[A-Z]", "_");

        System.out.println("Welcome to the Wheel of Fortune!");

        while (!isPuzzleSolved()) {
            System.out.println("Puzzle: " + displayPuzzle);
            System.out.println("Player " + (currentPlayer + 1) + "'s turn. Score: " + playerScores[currentPlayer]);
            System.out.println("Spin the wheel (s), solve the puzzle (solve), or buy a vowel (vowel) for $250? (Enter 's', 'solve', or 'vowel')");
            String choice = scanner.nextLine();

            switch (choice) {
                case "s" -> spinAndGuess(scanner);
                case "solve" -> attemptToSolve(scanner);
                case "vowel" -> buyAVowel(scanner);
                default -> System.out.println("Invalid choice.");
            }
        }

        System.out.println("Congratulations! Player " + (currentPlayer + 1) + " has won with a score of: " + playerScores[currentPlayer]);
        System.out.println("The correct puzzle was: \"" + currentPuzzle + "\"");
        scanner.close();
    }

    private static void spinAndGuess(Scanner scanner) {
        int spinResult = spinTheWheel();
        switch (spinResult) {
            case -1 -> {
                System.out.println("Oh no, Bankrupt!");
                playerScores[currentPlayer] = 0;
                nextPlayer();
            }
            case -2 -> {
                System.out.println("Tough luck, Lose a Turn!");
                nextPlayer();
            }
            default -> {
                System.out.println("The wheel landed on: $" + spinResult);
                System.out.print("Guess a consonant: ");
                String consonant = scanner.nextLine().toUpperCase();
                if (!consonant.matches("[AEIOU]") && consonant.length() == 1 && consonant.matches("[A-Z]")) {
                    int count = revealLetters(consonant);
                    if (count > 0) {
                        System.out.println("Good guess! " + count + " letter(s) found.");
                        playerScores[currentPlayer] += spinResult * count;
                    } else {
                        System.out.println("Nope, that letter isn't in the puzzle. Next player.");
                        nextPlayer();
                    }
                } else {
                    System.out.println("Invalid input or that's a vowel. Next player.");
                    nextPlayer();
                }
            }
        }
    }

    private static void attemptToSolve(Scanner scanner) {
        System.out.print("Attempt to solve the puzzle: ");
        String attempt = scanner.nextLine().toUpperCase();
        if (attempt.equals(currentPuzzle)) {
            System.out.println("That's correct! Congratulations!");
            displayPuzzle = currentPuzzle; // This will end the game
        } else {
            System.out.println("Unfortunately, that's not correct. Next player.");
            nextPlayer();
        }
    }

    private static void buyAVowel(Scanner scanner) {
        if (playerScores[currentPlayer] >= 250) {
            System.out.print("Pick a vowel: ");
            String vowel = scanner.nextLine().toUpperCase();
            if (vowel.matches("[AEIOU]") && vowel.length() == 1) {
                int count = revealLetters(vowel);
                if (count > 0) {
                    System.out.println("Correct! Found " + count + " vowel(s).");
                    playerScores[currentPlayer] -= 250;
                } else {
                    System.out.println("Sorry, that vowel does not appear. Next player.");
                    nextPlayer();
                }
            } else {
                System.out.println("That's not a vowel. Next player.");
                nextPlayer();
            }
        } else {
            System.out.println("You need at least $250 to buy a vowel. Next player.");
            nextPlayer();
        }
    }
}
