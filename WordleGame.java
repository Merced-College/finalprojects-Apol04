import java.util.Scanner;

public class WordleGame {
  public static void main(String[] args) {
    // Scanner for reading user input
    Scanner scanner = new Scanner(System.in);
    WordleEngine engine = new WordleEngine();
    
    // Loads the word list from the file
    engine.loadWordList();

    System.out.println("Welcome to Wordle in Java!!!");

    boolean playAgain = true;

    // Main game loop for replaying
    while (playAgain) {
      engine.startNewGame();
      int attempts = 0;
      boolean guessedCorrectly = false;

      // Loop for each attempt
      while (attempts < WordleEngine.MAX_ATTEMPTS && !guessedCorrectly) {
        System.out.printf("Attempt %d/%d - Enter your %d-letter guess: ", attempts + 1, WordleEngine.MAX_ATTEMPTS, WordleEngine.WORD_LENGTH);
        String guess = scanner.nextLine().toLowerCase();

        //Confirms the guess
        if (!engine.isValidGuess(guess)) {
          System.out.println("Invalid guess. Make sure it's valid 5 letter word.");
          continue;
        }

        attempts++;
        guessedCorrectly = engine.isCorrectGuess(guess);

        //Prints color coded feedback
        engine.printFeedback(guess);
      }

      // Prints the result of the game
      if (guessedCorrectly) {
        System.out.println("Congrats! You guessed the word.");
      } else {
        System.out.println("You've used all your attempts. The word was: " + engine.getTargetWord());
      }

      // Asks the user if the want to play the game again
      System.out.print("Do you want to play again? (y/n): ");
      playAgain = scanner.nextLine().trim().toLowerCase().startsWith("y");
    }

    // Closes scanner
    scanner.close();
    System.out.println("Thanks for playing!!!");
  }
}
