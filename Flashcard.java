
/**
 * The Flashcard class represents a flashcard
 * with question and answer variables.
 * 
 * @author Minh Tan Nguyen
 */

public class Flashcard {

    /**
     * Question of a flashcard.
     */
    private String question;

    /**
     * Answer of a flashcard.
     */
    private String answer;

    /**
     * Constructors initializes the question and answer.
     * 
     * @param question The question of this flashcard
     * @param answer The answer of this flashcard
     */
    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Returns the question of this flashcard.
     * 
     * @return The question of this flashcard
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the answer of this flashcard.
     * 
     * @return The answer of this flashcard
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets the question of this flashcard.
     * 
     * @param question The new question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Sets the answer of this flashcard.
     * 
     * @param answer The new answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Returns the status of this flashcard.
     * 
     * @return The string representation of this flashcard
     */
    public String toString() {
        return "Q: " + question + 
            "\nA: " + answer;
    }

}
