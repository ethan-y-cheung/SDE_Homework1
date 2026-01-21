import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * A quiz of words from Prithee (for Pat?).
 *
 * <p>The program reads a sonnet from a file (Prithee), randomly selects a word, and asks
 * the user to guess the word after printing the Sonnet up to that word. The program continues
 * until the user gets 3 correct or 3 incorrect answers.
 */

public class Sonnet {
    private final String[] words;
    private final Random random;
    private int correctAnswers;
    private int incorrectAnswers;

    /**
     * Constructs a new Sonnet from any text.
     *
     * @param text the complete text of the sonnet (Prithee)
     */
    public Sonnet(String text) {
        if(text == null) {
            text = "";
        }
        this.words = text.split(" ");
        this.random = new Random();
        this.correctAnswers = 0;
        this.incorrectAnswers = 0;
    }
    /**
     * Constructs a new Sonnet with a random instance (Used for testing)
     *
     * @param text the complete text of the sonnet (Prithee)
     * @param random the Random instance to select words
     */
    public Sonnet(String text, Random random) {
        if(text == null) {
            text = "";
        }
        this.words = text.split(" ");
        this.random = random;
        this.correctAnswers = 0;
        this.incorrectAnswers = 0;
    }

    /**
     * Reads a sonnet from a file.
     *
     * @param filename the filepath of the sonnet
     * @return the text of the file
     * @throws FileNotFoundException if the file doesn't exist
     */
    public static String readSonnet(String filename) throws FileNotFoundException {
        File sonnet = new File(filename);
        StringBuilder text = new StringBuilder();
        try (Scanner sc = new Scanner(sonnet)) {
            while(sc.hasNextLine()) {
                text.append(sc.nextLine()).append(" \n"); // Add newline to the front of the next word when split
            }
        }

        return text.toString();
    }

    /**
     * Generates a quiz question by selecting a random word from the sonnet
     *
     * @return a QuizQuestion containing the context and the word
     */
    public QuizQuestion generateQuestion() {
        int wordIndex = random.nextInt(words.length);
        String word = words[wordIndex].replaceAll("[,\n]", "").toLowerCase(); // Just the word
        StringBuilder context = new StringBuilder();
        for(int i = 0; i < wordIndex; i++) {
            context.append(words[i]).append(" "); // Refill spaces after .split(" ")
        }
        context.append(word.replaceAll(".", "_")); // Replaces each char with underscores

        return new QuizQuestion(context.toString(), word);
    }

    /**
     * Checks if the user's answer is correct (for testing)
     *
     * @param question the quiz question
     * @param userAnswer the user's answer
     * @return true if the answer is correct, false otherwise
     */
    public boolean checkAnswer(QuizQuestion question, String userAnswer) {
        String normalizedAnswer = userAnswer.strip().toLowerCase();
        boolean isCorrect = normalizedAnswer.equals(question.getAnswer());

        if (isCorrect) {correctAnswers++;}
        else {incorrectAnswers++;}

        return isCorrect;
    }

    /**
     * Gets number of correct answers (for testing)
     *
     * @return number of correct answers
     */
    public int getCorrectAnswers() {return correctAnswers;}

    /**
     * Gets number of incorrect answers
     *
     * @return number of incorrect answers
     */
    public int getIncorrectAnswers() {return incorrectAnswers;}

    /**
     * Runs the program
     *
     * @param scanner the Scanner to use for user input
     */
    public void play(Scanner scanner) {
        while(correctAnswers < 3 && incorrectAnswers < 3) { // Game not over
            QuizQuestion question = generateQuestion();

            System.out.println(question.getContext());
            System.out.println("What is the next word?");
            String answer = scanner.nextLine(); // User's answer in cli
            String normalizedAnswer = answer.strip().toLowerCase();
            boolean correct = normalizedAnswer.equals(question.getAnswer());

            if (correct) {
                System.out.println("You are correct!");
                correctAnswers++;
            } else {
                System.out.println("You are incorrect. The correct word is "
                        + question.getAnswer() + ".");
                incorrectAnswers++;
            }

            System.out.println();
        }
        System.out.println("You got " + correctAnswers + "/" + (correctAnswers + incorrectAnswers) + " correct.");
    }

    /**
     * A quiz question with context and answer
     */
    public static class QuizQuestion {
        private final String context;
        private final String answer;

        /**
         * Constructs a new QuizQuestion
         *
         * @param context the context before the word
         * @param correctAnswer the answer
         */
        public QuizQuestion(String context, String correctAnswer) {
            this.context = context;
            this.answer = correctAnswer;
        }

        /**
         * Gets the context for the question
         *
         * @return the context
         */
        public String getContext() {
            return context;
        }

        /**
         * Gets the answer for the question
         *
         * @return the answer
         */
        public String getAnswer() {
            return answer;
        }
    }
}