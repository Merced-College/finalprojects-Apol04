//package WordleEngine;

// Name: Angel A. Cisneros
// Date: July 6 2025
//Game Logic Class


import java.io.*;
import java.util.*;

public class WordleEngine {
  public static final int WORD_LENGTH = 5;
  public static final int MAX_ATTEMPTS = 6;
  private static final String WORD_LIST_FILE = "wordlist.txt";

  private List<String> validWords = new ArrayList<>();
  private String targetWord;

  // Load word list from file
  public void loadWordList() {
    try (BufferedReader br = new BufferedReader(new FileReader(WORD_LIST_FILE))) {
      String line;
      while ((line = br.readLine()) != null) {
        line = line.trim().toLowerCase();
        if (line.length() == WORD_LENGTH) {
          validWords.add(line);
        }
      }
    } catch (IOException e) {
      System.out.println("Error loading word list. Make sure 'wordlist.txt is present.'");
      System.exit(1);
    }
  }

  //Start a new game by selecting a random target word
  public void startNewGame() {
    Random rand = new Random();
    targetWord = validWords.get(rand.nextInt(validWords.size()));
  }

  // Checks if the guess is valid
  public boolean isValidGuess(String guess) {
    return guess.length() == WORD_LENGTH && validWords.contains(guess);
  }

  // Check if the guess is correct
  public boolean isCorrectGuess(String guess) {
    return guess.equals(targetWord);
  }

  // Return the current target word
  public String getTargetWord() {
    return targetWord;
  }

  // Print color coded feedback for a guess
  public void printFeedback(String guess) {
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
