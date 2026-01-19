import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.FileNotFoundException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Sonnet class
 */
class SonnetTest {
    private Sonnet game;

    @Test
    @DisplayName("Constructor should initialize game with text")
    void testConstructorWithText() throws FileNotFoundException {
        this.game = new Sonnet(Sonnet.readSonnet("Sonnet.txt"));
        assertNotNull(game);
        assertEquals(0, game.getCorrectAnswers());
        assertEquals(0, game.getIncorrectAnswers());
    }

    @Test
    @DisplayName("generateQuestion should return valid question")
    void testGenerateQuestion() throws FileNotFoundException {
        this.game = new Sonnet(Sonnet.readSonnet("Sonnet.txt"));
        Sonnet.QuizQuestion question = game.generateQuestion();

        assertNotNull(question);
        assertNotNull(question.getContext());
        assertNotNull(question.getAnswer());
        assertFalse(question.getAnswer().isEmpty());
    }


    @Test
    @DisplayName("Game should track score correctly")
    void testScoreTracking() throws FileNotFoundException {
        Random seededRandom = new Random(42);
        Sonnet seededGame = new Sonnet(Sonnet.readSonnet("Sonnet.txt"), seededRandom);

        // 2 correct 1 incorrect
        for (int i = 0; i < 2; i++) {
            Sonnet.QuizQuestion question = seededGame.generateQuestion();
            seededGame.checkAnswer(question, question.getAnswer());
        }

        Sonnet.QuizQuestion question = seededGame.generateQuestion();
        seededGame.checkAnswer(question, "flabingus");

        assertEquals(2, seededGame.getCorrectAnswers());
        assertEquals(1, seededGame.getIncorrectAnswers());
    }
}