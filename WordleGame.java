import java.io.*;
import java.util.*;

public class WordleGame {

  // Constants for the game
  private static final int WORD_LENGTH = 5;
  private static final int MAX_ATTEMPTS = 6;
  private static final String WORD_LIST_FILE = "wordlist.txt";

  //List to store valid 5 letter words
  private static final List<String> validWords = new ArrayList<>();
  
  // The word the user is trying to guess
  private static String targetWord;

  public static void main(String[] args) {
    // Scanner for reading user input
    Scanner scanner = new Scanner(System.in);

    // Loads the word list from the file
    loadWordList();

    System.out.println("Welcome to Wordle in Java!!!");

    boolean playAgain = true;

    // Main game loop for replaying
    while (playAgain) {
      targetWord = getRandomWord();
      int attempts = 0;
      boolean guessedCorrectly = false;

      // Loop for each attempt
      while (attempts < MAX_ATTEMPTS && !guessedCorrectly) {
        System.out.printf("Attempt %d/%d - Enter your %d-letter guess: ", attempts + 1, MAX_ATTEMPTS, WORD_LENGTH);
        String guess = scanner.nextLine().toLowerCase();

        //Confirms the guess
        if (!isValidGuess(guess)) {
          System.out.println("Invalid guess. Make sure it's valid 5 letter word.");
          continue;
        }

        attempts++;
        guessedCorrectly = guess.equals(targetWord);

        //Prints color coded feedback
        printFeedback(guess);
      }

      // Prints the result of the game
      if (guessedCorrectly) {
        System.out.println("Congrats! You guessed the word.");
      } else {
        System.out.println("You've used all your attempts. The word was: " + targetWord);
      }

      // Asks the user if the want to play the game again
      System.out.print("Do you want to play again? (y/n): ");
      playAgain = scanner.nextLine().trim().toLowerCase().startsWith("y");
    }

    // Closes scanner
    scanner.close();
    System.out.println("Thanks for playing!!!");
  }

  // Load valid 5 letter words from the file into the list
  private static void loadWordList() {
    try (BufferedReader br = new BufferedReader(new FileReader(WORD_LIST_FILE))) {
      String line;
      while ((line = br.readLine()) != null) {
        line = line.trim().toLowerCase();
        if (line.length() == WORD_LENGTH) {
          validWords.add(line);
        }
      }
    } catch (IOException e) {
      System.out.println("Error loading word list. Make sure 'wordlist.txt' is present.");
      System.exit(1); // Exits if file loading fails
    }
  }

  // Return a random word from the list
  private static String getRandomWord() {
    Random rand = new Random();
    return validWords.get(rand.nextInt(validWords.size()));
  }

  // Checks if the guess is valid (correct length and in the word list)
  private static boolean isValidGuess(String guess) {
    return guess.length() == WORD_LENGTH && validWords.contains(guess);
  }

  // Prints feedback showing which letters are correct and which ones are incorrect
  private static void printFeedback(String guess) {
    StringBuilder feedback = new StringBuilder();
    for (int i = 0; i < WORD_LENGTH; i++) {
      char g = guess.charAt(i);
      char t = targetWord.charAt(i);
      if (g == t) {
        feedback.append("\u001B[32m").append(g).append("\u001B[0m"); // Color Green
      } else if (targetWord.contains(String.valueOf(g))) {
        feedback.append("\u001B[33m").append(g).append("\u001B[0m"); // Color Yellow
      } else {
        feedback.append("\u001B[90m").append(g).append("\u001B[0m"); // Color Red
      }
    }
    System.out.println("Feedback: " + feedback.toString());
  }
}
