
import java.util.Scanner;
import java.util.ArrayList;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;

/**
 * The Deck class represents a deck of flashcards.
 * 
 * @author Minh Tan Nguyen
 */

public class Deck {

    /**
     * The list of flashcards.
     */
    private ArrayList<Flashcard> deck;

    /**
     * The name of data file.
     */
    private String dataFile;

    /**
     * Constructor initializes the deck, dataFile,
     * and loads data to the deck.
     * 
     * @param dataFile
     * @throws IOException
     */
    public Deck(String dataFile) throws IOException {
        deck = new ArrayList<>();
        this.dataFile = dataFile + ".txt";
        loadData();
    }

    /**
     * Returns the name of the data file.
     * 
     * @return The name of the data file
     */
    public String getDataFile() {
        return dataFile;
    }

    /**
     * Sets the name of the data file.
     * 
     * @param dataFile The new data file name
     */
    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    /**
     * Adds a flashcard to the deck.
     * 
     * @param card The new flashcard
     */
    public void add(Flashcard card) {
        deck.add(card);
    }

    /**
     * Removes a flashcard from the deck.
     * 
     * @param card The flashcard to be removed
     */
    public void remove(Flashcard card) {
        deck.remove(card);
    }

    /**
     * Returns the flashcard at an index.
     * 
     * @param index The position of flashcard
     * @return The desired flashcard
     */
    public Flashcard get(int index) {
        return deck.get(index);
    }

    /**
     * Returns the size of this deck.
     * 
     * @return The size of this deck.
     */
    public int size() {
        return deck.size();
    }

    /**
     * Loads flashcard data from a file.
     * 
     * @throws IOException If the file can't be found or any
     * error while loading
     */
    private void loadData() throws IOException {

        Scanner outFile = new Scanner(new File(dataFile));

        int numCards = outFile.nextInt();
        outFile.nextLine();

        for (int i = 0; i < numCards; i++) {
            String question = outFile.nextLine();
            String answer = outFile.nextLine();
            deck.add(new Flashcard (question, answer));
        }

        outFile.close();

    }

    /**
     * Saves the flashcards to the file.
     * 
     * @throws IOException If the file can't be found or any
     * error while saving
     */
    protected void saveData() throws IOException {

        PrintWriter inFile = new PrintWriter(new File (dataFile));

        inFile.println(deck.size());

        for (Flashcard card : deck) {
            inFile.println(card.getQuestion());
            inFile.println(card.getAnswer());
        }

        inFile.close();

    }

    /**
     * Returns the access to the list of flashcards.
     * 
     * @return The access to the list of flashcards
     */
    protected ArrayList<Flashcard> getFlashcardsList() {
        return deck;
    }
    
}
