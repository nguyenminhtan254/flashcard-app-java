
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.io.*;

/**
 * The GUI for the FlashcardApp using JavaFX.
 * 
 * @author Minh Tan Nguyen
 */

public class FlashcardApp extends Application {

    /**
     * The deck of flashcards.
     */
    private Deck deck;

    /**
     * The list of flashcards on the GUI.
     */
    private ListView<Flashcard> deckLV = new ListView<>();

    /**
     * Variables for study mode.
     */
    int count = 0;
    int correct = 0;
    int incorrect = 0;

    /**
     * Main window of this FlashcardApp.
     * 
     * @param stage The main window stage
     */
    public void start(Stage stage) {

        stage.setTitle("Flashcard App");

        // Top layouts
        VBox mainTop = new VBox();
        HBox topCenter = new HBox();
        mainTop.setAlignment(Pos.CENTER);
        mainTop.setPadding(new Insets(10));
        topCenter.setAlignment(Pos.CENTER);
        topCenter.setPadding(new Insets(5));

        // Center layouts
        HBox mainCenter = new HBox();
        VBox centerRight = new VBox(10);
        mainCenter.setAlignment(Pos.CENTER);
        mainCenter.setPadding(new Insets(20));
        centerRight.setAlignment(Pos.CENTER);
        centerRight.setPadding(new Insets(10));

        // Main layout
        BorderPane root = new BorderPane();
        root.setTop(mainTop);
        root.setCenter(mainCenter);

        // Labels
        Label title = new Label("Flashcard App");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label deckName = new Label("No deck loaded");
        deckName.setStyle("-fx-font-size: 15px;");

        // Buttons
        Button loadBtn = new Button("Load");
        Button addBtn = new Button("Add");
        Button deleteBtn = new Button("Delete");
        Button studyBtn = new Button("Study");
        Button exitBtn = new Button("Save & Exit");
        Button createBtn = new Button("Create Deck");

        // TextFields
        TextField loadTF = new TextField();
        loadTF.setPromptText("Enter a deck name");

        // Adds controls to the top layouts
        topCenter.getChildren().addAll(loadTF, loadBtn);
        mainTop.getChildren().addAll(title, topCenter, deckName);

        // Adds controls to the center layouts
        mainCenter.getChildren().addAll(deckLV, centerRight);
        centerRight.getChildren().addAll(addBtn, deleteBtn, studyBtn, exitBtn);

        // Actions for all buttons
        loadBtn.setOnAction(e -> {

            String dataFile = loadTF.getText();

            try {

                deck = new Deck(dataFile);
                deckName.setText(dataFile);
                deckName.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
                deckLV.getItems().clear();
                deckLV.getItems().addAll(deck.getFlashcardsList());

            } catch (IOException error) {

                if (dataFile.isEmpty()) {
                    deckName.setText("Please enter a file name");
                } else {
                    deckName.setText("File Not Found");
                    mainTop.getChildren().add(createBtn);
                }

            }
            
        });

        exitBtn.setOnAction(e -> {

            try {

                if (deck != null) {
                    deck.saveData();
                }
                stage.close();

            } catch (IOException error) {
                System.out.println(error.getMessage());
            }
            
        });

        deleteBtn.setOnAction(e -> {

            Flashcard selectedCard = deckLV.getSelectionModel().getSelectedItem();
            deck.remove(selectedCard);
            deckLV.getItems().remove(selectedCard);

        });

        addBtn.setOnAction(e -> {
            addStage();
        });

        studyBtn.setOnAction(e -> {
            studyStage();
        });

        createBtn.setOnAction(e -> {

            try {

                String dataFile = loadTF.getText() + ".txt";

                File newFile = new File(dataFile);
                newFile.createNewFile();

                PrintWriter inFile = new PrintWriter(newFile);
                inFile.println("0");

                mainTop.getChildren().remove(createBtn);

                inFile.close();

            } catch (IOException error) {
                System.out.println(error.getMessage());
            } 

        });

        // Display the main window
        Scene scene = new Scene(root, 400, 500);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Add window after clicking the add button.
     */
    public void addStage() {

        Stage stage = new Stage();
        stage.setTitle("Add Flashcard");

        // Layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Labels
        Label questionLbl = new Label("Enter question:");
        Label answerLbl = new Label("Enter answer:");

        // Button
        Button saveBtn = new Button("Save");

        // TextFields
        TextField questionTF = new TextField();
        TextField answerTF = new TextField();

        // Adds controls to the layout
        root.getChildren().addAll(questionLbl, questionTF, answerLbl, answerTF, saveBtn);

        // Action for save button
        saveBtn.setOnAction(e -> {

            String question = questionTF.getText();
            String answer = answerTF.getText();

            Flashcard card = new Flashcard(question, answer);
            deck.add(card);
            deckLV.getItems().add(card);

            stage.close();

        });
        
        // Display the add window
        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Study window after clicking the study button.
     */
    public void studyStage() {

        Stage stage = new Stage();
        stage.setTitle("Study");

        // Layouts
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10));

        // Labels
        Label countLbl = new Label("Card " + (count+1) + " of " + deck.size());
        Label questionLbl = new Label("Question: " + deck.get(count).getQuestion());
        Label answerLbl = new Label("Your answer:");
        Label resultLbl = new Label("");
        Label totalResultLbl = new Label("");
        totalResultLbl.setStyle("-fx-font-weight: bold;");

        // Buttons
        Button checkBtn = new Button("Check Answer");
        Button nextBtn = new Button("Next");
        Button closeBtn = new Button("Close");

        // TextField
        TextField answerTF = new TextField();

        // Adds controls to the layout
        buttons.getChildren().addAll(checkBtn, nextBtn, closeBtn);
        root.getChildren().addAll(countLbl, questionLbl, answerLbl, answerTF, buttons, resultLbl, totalResultLbl);

        // Actions for all buttons
        checkBtn.setOnAction(e -> {

            if (count < deck.size()) {
                String result = "Answer: " + deck.get(count).getAnswer();

                if (deck.get(count).getAnswer().equalsIgnoreCase(answerTF.getText())) {
                    correct++;
                    result += "\nCorrect!";
                } else {
                    incorrect++;
                    result += "\nIncorrect!";
                }

                // Display the final result
                if (count ==  deck.size()-1) {
                    totalResultLbl.setText("Total correct: " + correct + "/" + deck.size()
                        + "\nTotal incorrect: " + incorrect + "/" + deck.size());
                }

                resultLbl.setText(result);
                answerTF.setDisable(true);
                checkBtn.setDisable(true);
            }


        });

        nextBtn.setOnAction(e -> {

            if (count < deck.size()-1) {
                count++;

                answerTF.setDisable(false);
                answerTF.clear();

                checkBtn.setDisable(false);

                countLbl.setText("Card " + (count+1) + " of " + deck.size());
                questionLbl.setText("Question: " + deck.get(count).getQuestion());
                resultLbl.setText("");
            }

        });

        closeBtn.setOnAction(e -> {
            count = 0;
            correct = 0;
            incorrect = 0;
            stage.close();
        });

        // Display the study window
        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
