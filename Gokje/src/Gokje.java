// Program should pick random number (defined in config variable)
// The player should guess the number
// The player should be able to guess a limited amount of times (defined in config variable)
// The program should tell the player if the guess is too high or too low

import java.io.*;

public class Gokje {
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            // Config variables
            int lowerLimit = 1;
            int upperLimit = 10;
            int maxGuesses = 3;

            System.out.println("Welcome to Gokje!");
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

            // Switch statement based on input
            switch (input) {
                case "1":
                    // Player guesses
                    System.out.println("You chose to play a game!");
                    System.out.println("The computer will pick a number between " + lowerLimit + " and " + upperLimit);
                    System.out.println("You have " + maxGuesses + " guesses");
                    System.out.println("Guess a number");

                    //TODO: Implement game logic
                    break;

                case "2":
                    // Computer guesses
                    System.out.println("You chose to let the computer guess!");
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
                        System.out.println("Upperlimit: ");
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

                    System.out.println("Choose the number the computer should guess between " + lowerPcLimit + " and " + upperPcLimit);
                    // While loop to ensure player has entered correct input
                    validInput = false;
                    int pcGuess;
                    while (!validInput) {
                        try {
                            pcGuess = Integer.parseInt(reader.readLine());
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (pcGuess < lowerPcLimit || pcGuess > upperPcLimit) {
                            System.out.println("The number you chose is not between " + lowerPcLimit + " and " + upperPcLimit);
                        }
                        else {
                            validInput = true;
                        }
                    }
                    //TODO: Implement game logic
                    break;

                case "3":
                    System.out.println("You chose to quit!");
                    running = false;
                    break;
            }
        }
    }
}