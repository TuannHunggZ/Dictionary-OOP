package Dictionary.Controllers;

import Dictionary.Models.English;
import Dictionary.Main;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static Dictionary.Models.DatabaseConfig.englishDAO;

public class MiniGameController implements Initializable {
    public static List<English> wordList;

    static {
        try {
            wordList = englishDAO.queryWordle();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private List<String> currentWordList;
    private String winningWord;
    private final int MAX_ROW = 5;
    private final int MAX_COLUMN = 4;
    private int current_row = 0;
    private int current_column = 0;

    @FXML
    GridPane keyBoardRow1, keyBoardRow2, keyBoardRow3, wordGridPane;

    @FXML
    ListView<String> suggestionListView;

    @FXML
    TextField searchBox;

    @FXML
    Button ruleBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialKeyboardRow(keyBoardRow1);
        initialKeyboardRow(keyBoardRow2);
        initialKeyboardRow(keyBoardRow3);

        suggestionListView.getItems().clear();
        currentWordList = new ArrayList<>();
        for (English english : wordList) {
            suggestionListView.getItems().add(english.getWord().toLowerCase());
            currentWordList.add(english.getWord().toLowerCase());
        }
        Collections.sort(suggestionListView.getItems());
        Collections.sort(currentWordList);
        int suggestSize = suggestionListView.getItems().size();
        winningWord = suggestionListView.getItems().get(new Random().nextInt(suggestSize));
        System.out.println(winningWord);
    }

    @FXML
    public void handleSearch(KeyEvent keyEvent) {
        String searchWord = searchBox.getText().trim().toLowerCase();
        if (searchWord.isEmpty()) {
            suggestionListView.getItems().clear();
            for (String word : currentWordList) {
                suggestionListView.getItems().add(word);
            }
            Collections.sort(suggestionListView.getItems());
            return;
        }
        int n = searchWord.length();
        if (n > 5) {
            suggestionListView.getItems().clear();
            return;
        }
        suggestionListView.getItems().clear();
        for (String word : currentWordList) {
            if (word.substring(0, n).equals(searchWord)) {
                suggestionListView.getItems().add(word);
            }
        }
    }

    @FXML
    public void handleRule(MouseEvent mouseEvent) {
        ruleBtn.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Image image = new Image(getClass().getResourceAsStream("/UI/Image/rule.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(450);
            imageView.setPreserveRatio(true);
            VBox vBox = new VBox(imageView);
            alert.initOwner(Main.stage);
            alert.setTitle("How to play ?");
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(vBox);
            alert.showAndWait();
        });
    }

    private void initialKeyboardRow(GridPane keyboardRow) {
        for (Node child : keyboardRow.getChildren()) {
            if (child instanceof Button) {
                Button button = (Button) child;
                button.setOnMouseClicked(e -> {
                    ScaleTransition firstScaleTransition = new ScaleTransition(Duration.millis(100), button);
                    firstScaleTransition.fromXProperty().setValue(1);
                    firstScaleTransition.toXProperty().setValue(1.1);
                    firstScaleTransition.fromYProperty().setValue(1);
                    firstScaleTransition.toYProperty().setValue(1.1);

                    ScaleTransition secondScaleTransition = new ScaleTransition(Duration.millis(100), button);
                    secondScaleTransition.fromXProperty().setValue(1.1);
                    secondScaleTransition.toXProperty().setValue(1);
                    secondScaleTransition.fromYProperty().setValue(1.1);
                    secondScaleTransition.toYProperty().setValue(1);

                    SequentialTransition transition = new SequentialTransition(firstScaleTransition, secondScaleTransition);
                    transition.play();

                    onVirtualKeyPressed(button);
                });
            }
        }
    }

    private Label getLabel(int searchRow, int searchColumn) {
        for (Node child : wordGridPane.getChildren()) {
            Integer r = GridPane.getRowIndex(child);
            Integer c = GridPane.getColumnIndex(child);
            int row = r;
            int column = c;
            if (row == searchRow && column == searchColumn && (child instanceof Label)) {
                return (Label) child;
            }
        }
        return null;
    }

    private String getWordFromCurrentRow() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j <= MAX_COLUMN; j++) {
            Label label = getLabel(current_row, j);
            if (label != null) {
                stringBuilder.append(label.getText());
            }
        }
        return stringBuilder.toString().toLowerCase();
    }

    private void updateRowColors() {
        boolean[] winningCheck = new boolean[5];
        boolean[] guessCheck = new boolean[5];
        String guess = getWordFromCurrentRow();
        for (int i = 0; i < 5; i++) {
            if (String.valueOf(winningWord.charAt(i)).equals(String.valueOf(guess.charAt(i)))) {
                Label label = getLabel(current_row, i);
                if (label != null) {
                    winningCheck[i] = true;
                    guessCheck[i] = true;
                    label.getStyleClass().clear();
                    label.getStyleClass().setAll("correct-letter");
                }
            }
        }
        int j = 0;
        while (j < 5) {
            if (!guessCheck[j]) {
                String currentCharacter = String.valueOf(guess.charAt(j));
                int indexOfSearch = -1;
                for (int i = 0; i < 5; i++) {
                    if (!winningCheck[i] && String.valueOf(winningWord.charAt(i)).equals(currentCharacter)) {
                        indexOfSearch = i;
                        winningCheck[i] = true;
                        guessCheck[j] = true;
                        break;
                    }
                }
                if (indexOfSearch == -1) {
                    Label label = getLabel(current_row, j);
                    if (label != null) {
                        label.getStyleClass().clear();
                        label.getStyleClass().setAll("wrong-letter");
                    }
                } else {
                    Label label = getLabel(current_row, j);
                    if (label != null) {
                        label.getStyleClass().clear();
                        label.getStyleClass().setAll("present-letter");
                    }
                }
            }
            j++;
        }
    }

    private void updateCurrentWordList() {
        String guess = getWordFromCurrentRow();
        if (guess.length() < 5) {
            return;
        }
        List<Integer> rightIndex = new ArrayList<>();
        for (int i = 0; i <= MAX_COLUMN; i++) {
            if (String.valueOf(guess.charAt(i)).equals(String.valueOf(winningWord.charAt(i)))) {
                rightIndex.add(i);
            }
        }

        for (int i = 0; i < currentWordList.size(); i++) {
            String word = currentWordList.get(i);
            for (int index : rightIndex) {
                if (!String.valueOf(word.charAt(index)).equals(String.valueOf(winningWord.charAt(index)))) {
                    currentWordList.remove(i);
                    i--;
                    break;
                }
            }
        }

        suggestionListView.getItems().clear();
        for (String word : currentWordList) {
            suggestionListView.getItems().add(word);
        }
        searchBox.setText("");
    }

    private boolean isValidGuess(String guess) {
        if (guess.length() < 5) {
            return false;
        }
        int low = 0, high = wordList.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = guess.compareTo(wordList.get(mid).getWord().toLowerCase());
            if (comparison == 0) {
                return true;
            } else if (comparison > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

    private Button getButton(GridPane keyBoardRow, String letter) {
        for (Node child : keyBoardRow.getChildren()) {
            if (child instanceof Button) {
                Button button = (Button) child;
                if (letter.equalsIgnoreCase(button.getText()))
                    return button;
            }
        }
        return null;
    }

    private void updateKeyboardColors() {
        String currentWord = getWordFromCurrentRow();
        for (int j = 0; j <= MAX_COLUMN; j++) {
            Button keyboardButton = new Button();
            String currentCharacter = String.valueOf(currentWord.charAt(j));
            String winningCharacter = String.valueOf(winningWord.charAt(j));

            keyboardButton = getButton(keyBoardRow1, currentCharacter);
            if (keyboardButton == null) {
                keyboardButton = getButton(keyBoardRow2, currentCharacter);
            }
            if (keyboardButton == null) {
                keyboardButton = getButton(keyBoardRow3, currentCharacter);
            }

            if (keyboardButton != null) {
                if (currentCharacter.equals(winningCharacter)) {
                    keyboardButton.getStyleClass().clear();
                    keyboardButton.getStyleClass().add("keyboardCorrectColor");
                } else if (winningWord.contains(currentCharacter)) {
                    ObservableList<String> style = keyboardButton.getStyleClass();
                    if (!style.contains("keyboardCorrectColor")) {
                        keyboardButton.getStyleClass().clear();
                        keyboardButton.getStyleClass().add("keyboardPresentColor");
                    }
                } else {
                    keyboardButton.getStyleClass().clear();
                    keyboardButton.getStyleClass().add("keyboardWrongColor");
                }
            }
    }
    }

    private void resetGame() {
        current_row = 0;
        current_column = 0;
        suggestionListView.getItems().clear();
        currentWordList.clear();
        searchBox.setText("");
        for (English english : wordList) {
            suggestionListView.getItems().add(english.getWord().toLowerCase());
            currentWordList.add(english.getWord().toLowerCase());
        }
        Collections.sort(suggestionListView.getItems());
        Collections.sort(currentWordList);
        int suggestSize = suggestionListView.getItems().size();
        winningWord = suggestionListView.getItems().get(new Random().nextInt(suggestSize));
        System.out.println(winningWord);

        for (Node child : wordGridPane.getChildren()) {
            if (child instanceof Label) {
                Label label = (Label) child;
                label.getStyleClass().clear();
                label.setText("");
                label.getStyleClass().add("default-tile");
            }
        }

        for (Node child : keyBoardRow1.getChildren()) {
            if (child instanceof Button) {
                Button button = (Button) child;
                button.getStyleClass().clear();
                button.getStyleClass().add("keyboardTile");
            }
        }

        for (Node child : keyBoardRow2.getChildren()) {
            if (child instanceof Button) {
                Button button = (Button) child;
                button.getStyleClass().clear();
                button.getStyleClass().add("keyboardTile");
            }
        }

        for (Node child : keyBoardRow3.getChildren()) {
            if (child instanceof Button) {
                Button button = (Button) child;
                button.getStyleClass().clear();
                button.getStyleClass().add("keyboardTile");
            }
        }
    }

    private void onVirtualKeyPressed(Button btn) {
        if (btn.getText().equals("⌫")) {
            onBackspacePressed();
        } else if (btn.getText().equals("Enter")) {
            onEnterPressed();
        } else {
            onLetterPressed(btn);
        }
    }

    private void onLetterPressed(Button btn) {
        Label label = getLabel(current_row, current_column);
        if (label != null) {
            if (label.getText().isEmpty()) {
                label.setText(btn.getText());
                label.getStyleClass().add("tile-with-letter");

                ScaleTransition firstScaleTransition = new ScaleTransition(Duration.millis(100), label);
                firstScaleTransition.fromXProperty().setValue(1);
                firstScaleTransition.toXProperty().setValue(1.1);
                firstScaleTransition.fromYProperty().setValue(1);
                firstScaleTransition.toYProperty().setValue(1.1);

                ScaleTransition secondScaleTransition = new ScaleTransition(Duration.millis(100), label);
                secondScaleTransition.fromXProperty().setValue(1.1);
                secondScaleTransition.toXProperty().setValue(1);
                secondScaleTransition.fromYProperty().setValue(1.1);
                secondScaleTransition.toYProperty().setValue(1);

                SequentialTransition transition = new SequentialTransition(firstScaleTransition, secondScaleTransition);
                transition.play();
                if (current_column < MAX_COLUMN)
                    current_column++;
            }
        }
    }

    private void onBackspacePressed() {
        Label label = getLabel(current_row, current_column);
        if (label != null) {
            if ((current_column == MAX_COLUMN || current_row == 0) && !label.getText().isEmpty()) {
                label.setText("");
                label.getStyleClass().clear();
                label.getStyleClass().add("default-tile");
            } else if ((current_column > 0 && current_column < MAX_COLUMN) || (current_column == MAX_COLUMN && label.getText().isEmpty())) {
                current_column--;
                Label other = getLabel(current_row, current_column);
                if (other != null) {
                    other.setText("");
                    other.getStyleClass().clear();
                    other.getStyleClass().add("default-tile");
                }
            }
        }
    }

    private void onEnterPressed() {
        if (current_row <= MAX_ROW && current_column <= MAX_COLUMN) {
            String guess = getWordFromCurrentRow();
            if (isValidGuess(guess)) {
                if (guess.equals(winningWord)) {
                    updateRowColors();
                    updateKeyboardColors();

                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.initOwner(Main.stage);
                    dialog.setTitle("Congratulation");
                    dialog.setContentText("You win");
                    ButtonType homeButtonType = new ButtonType("Home");
                    ButtonType playAgainButtonType = new ButtonType("Play Again");
                    dialog.getDialogPane().getButtonTypes().addAll(homeButtonType, playAgainButtonType);

                    Optional<ButtonType> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        if (result.get() == homeButtonType) {
                            dialog.close();
                            try {
                                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/UI/MenuUI.fxml")));
                                Main.stage.setScene(new Scene(root));
                                Main.stage.setResizable(false);
                                Main.stage.show();
                            } catch (IOException e) {
                                System.out.println(e. getMessage());
                            }
                        } else if (result.get() == playAgainButtonType) {
                            resetGame();
                        }
                    }
                } else {
                    updateRowColors();
                    updateKeyboardColors();
                    updateCurrentWordList();
                    current_row++;
                    current_column = 0;
                    if (current_row - 1 == MAX_ROW) {
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.initOwner(Main.stage);
                        dialog.setTitle("Lose");
                        dialog.setContentText("You lose");
                        ButtonType homeButtonType = new ButtonType("Home");
                        ButtonType playAgainButtonType = new ButtonType("Play Again");
                        dialog.getDialogPane().getButtonTypes().addAll(homeButtonType, playAgainButtonType);

                        Optional<ButtonType> result = dialog.showAndWait();
                        if (result.isPresent()) {
                            if (result.get() == homeButtonType) {
                                dialog.close();
                                try {
                                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/UI/MenuUI.fxml")));
                                    Main.stage.setScene(new Scene(root));
                                    Main.stage.setResizable(false);
                                    Main.stage.show();
                                } catch (IOException e) {
                                    System.out.println(e. getMessage());
                                }
                            } else if (result.get() == playAgainButtonType) {
                                resetGame();
                            }
                        }
                    }
                }
            } else {
                if (guess.length() < 5) {
                    showMessage("Not enough letter");
                } else {
                    showMessage("Not in wordlist");
                }
            }
        }
    }

    private void showMessage(String message) {
        Stage newStage = new Stage();
        newStage.initOwner(Main.stage);
        newStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(message);
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-size: 16px;");

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 3; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 10;");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.show();

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(750), root);
        fadeTransition.setFromValue(0.75);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(event -> newStage.close());
        fadeTransition.play();
    }
}