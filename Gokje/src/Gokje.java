// Program should pick random number (defined in config variable)
// The player should guess the number
// The player should be able to guess a limited amount of times (defined in config variable)
// The program should tell the player if the guess is too high or too low

import java.io.*;

public class Gokje {
    public static void main(String[] args) {
        // Config variables
        int lowerLimit = 1;
        int upperLimit = 100;
        int maxGuesses = 5;

        // run the game main-screen
        boolean running = true;
        while (running) {
            System.out.println("\nWelcome to Gokje!");
            System.out.println("Do you want to play a game?, Let the computer guess? or quit?");
            System.out.println("[1] Play a game");
            System.out.println("[2] Let the computer guess");
            System.out.println("[3] Quit");

            // Get input from user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = "";
            try {
                input = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            switch (input) {
                case "1":
                    // Player guesses
                    boolean runningPlayerGuesses = true;
                    while (runningPlayerGuesses) {
                        userPlaysGame(lowerLimit, upperLimit, maxGuesses, reader);

                        // Ask if player wants to play again
                        System.out.println("\nDo you want to play again?");
                        System.out.println("[1] Yes");
                        System.out.println("[2] No");
                        try {
                            input = reader.readLine();
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        switch (input) {
                            case "1":
                                runningPlayerGuesses = true;
                                break;
                            case "2":
                                runningPlayerGuesses = false;
                                break;
                            default:
                                System.out.println("Invalid input, ending game");
                                runningPlayerGuesses = false;
                                break;
                        }
                    }
                    break;

                case "2":
                    // Computer guesses
                    computerPlaysGame(reader);
                    break;

                case "3":
                    System.out.println("\nYou chose to quit!");
                    running = false;
                    break;
            }
        }
    }

    // Utilities methods
    public static int getRandomNumber(int lowerLimit, int upperLimit) {
        return (int) (Math.random() * (upperLimit - lowerLimit + 1)) + lowerLimit;
    }

    public static int[] binarySearch(int lowerLimit, int upperLimit, int target){
        int[] result = new int[2];
        int guess = (lowerLimit + upperLimit) / 2;
        int guesses = 1;
        while (guess != target) {
            guesses++;
            if (guess < target) {
                lowerLimit = guess;
            } else {
                upperLimit = guess;
            }
            guess = (lowerLimit + upperLimit) / 2;
        }
        result[0] = guesses;
        result[1] = guess;
        return result;
    }

    // 'User plays game' method
    public static void userPlaysGame(int lowerLimit, int upperLimit, int guessesLeft, BufferedReader reader) {
        System.out.println("\nYou chose to play a game!");
        System.out.println("The computer will pick a number between " + lowerLimit + " and " + upperLimit);
        System.out.println("You have a maximum of " + guessesLeft + " guesses");

        int randomNumber = getRandomNumber(lowerLimit, upperLimit);
        int previousGuess = -999999999; // Initialize to a very low number to prevent compiler error; Cause int cannot be null
        while (guessesLeft > 0) {
            if (previousGuess != -999999999) {
                System.out.println("\nPrevious guess: " + previousGuess);
            }
            System.out.println("You have " + guessesLeft + " guesses left");
            System.out.println("Guess a number between " + lowerLimit + " and " + upperLimit);
            int userGuess;
            try {
                userGuess = Integer.parseInt(reader.readLine());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (userGuess < lowerLimit || userGuess > upperLimit) {
                System.out.println("The number you chose is not between " + lowerLimit + " and " + upperLimit);
            }
            else if( userGuess > randomNumber ){
                System.out.println("You guessed too high!");
                previousGuess = userGuess;
                upperLimit = userGuess;
                guessesLeft--;
            }
            else if( userGuess < randomNumber ){
                System.out.println("You guessed too low!");
                previousGuess = userGuess;
                lowerLimit = userGuess;
                guessesLeft--;
            }
            else {
                System.out.println("You guessed correctly!");
                break;
            }
        }
        if (guessesLeft == 0) {
            System.out.println("You ran out of guesses!");
            System.out.println("The number was " + randomNumber);
        }
    }

    // 'Computer plays game' method
    public static void computerPlaysGame(BufferedReader reader) {
        System.out.println("\nYou chose to let the computer guess!");
        System.out.println("Choose a lowerlimit and an upperlimit");
        System.out.println("Lowerlimit: ");
        int lowerPcLimit;
        try {
            lowerPcLimit = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // While loop to check if upperlimit is higher than lowerlimit
        boolean validInput = false;
        int upperPcLimit = 0; // Initialize to 0 to prevent compiler error
        while (!validInput) {
            System.out.println("\nUpperlimit: ");
            try {
                upperPcLimit = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (upperPcLimit <= lowerPcLimit) {
                System.out.println("Upperlimit must be higher than lowerlimit");
            }
            else {
                validInput = true;
            }
        }

        System.out.println("\nChoose the number the computer should guess between " + lowerPcLimit + " and " + upperPcLimit);
        // While loop to ensure player has entered correct input
        validInput = false;
        int randomUserNumber = 0; // Initialize to 0 to prevent compiler error
        while (!validInput) {
            try {
                randomUserNumber = Integer.parseInt(reader.readLine());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (randomUserNumber < lowerPcLimit || randomUserNumber > upperPcLimit) {
                System.out.println("The number you chose is not between " + lowerPcLimit + " and " + upperPcLimit);
            }
            else {
                validInput = true;
            }
        }
        int[] pcGuesserStats = binarySearch(lowerPcLimit, upperPcLimit, randomUserNumber);
        System.out.println("\n***Results*** ");
        System.out.println("The computer guessed " + pcGuesserStats[0] + " times");
        if (pcGuesserStats[1] == randomUserNumber) {
            System.out.println("The computer returned " + pcGuesserStats[1] + " which is correct!");
        }
        else {
            System.out.println("The computer returned " + pcGuesserStats[1] + " which is incorrect. Meaning Emily failed at making a foolproof binary search method!");
        }
        System.out.println("\nPress any key to continue");
        try {
            reader.readLine();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}