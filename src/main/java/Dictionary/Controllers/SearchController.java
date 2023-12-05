package Dictionary.Controllers;

import Dictionary.API.VoiceText;
import Dictionary.Main;
import Dictionary.Models.English;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static Dictionary.Models.DatabaseConfig.englishDAO;

public class SearchController {
    @FXML
    public TextField searchBox, countSuggestion;
    @FXML
    public ListView<String> suggestionListView;
    @FXML
    public TextArea definition;
    @FXML
    public Button phoneticBtn, updateBtn, deleteBtn;

    private English currentWord = null;

    private String standardization(String s) {
        s = s.toLowerCase();
        s = s.trim();
        return s;
    }

    @FXML
    public void handleSearch(KeyEvent keyEvent) {
        String searchWord = searchBox.getText();
        if (searchWord.isEmpty() || searchWord.isBlank()) {
            countSuggestion.setText("");
            suggestionListView.getItems().clear();
            definition.setText("");
            currentWord = null;
            return;
        }
        try {
            searchWord = standardization(searchWord);
            List<English> englishList = englishDAO.likeQuery(searchWord);
            suggestionListView.getItems().clear();
            if (englishList.isEmpty()) {
                suggestionListView.getItems().clear();
                countSuggestion.setText("Not found");
                definition.setText("");
                return;
            }
            for (English english : englishList) {
                suggestionListView.getItems().add(english.getWord());
            }
            if (englishList.size() == 1) {
                countSuggestion.setText("1 related word");
            } else {
                countSuggestion.setText(String.valueOf(englishList.size()) + " related words");
            }

            currentWord = englishList.get(0);
            definition.setText(englishDAO.getDefinition(currentWord));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void handleListView(MouseEvent mouseEvent) {
        String selectedWord = suggestionListView.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            try {
                currentWord = englishDAO.equalQuery(selectedWord);
                if (currentWord != null) {
                    definition.setText(englishDAO.getDefinition(currentWord));
                } else {
                    definition.setText("Definition not found for: " + selectedWord);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @FXML
    public void handleDelete(MouseEvent mouseEvent) {
        deleteBtn.setOnMouseClicked(e -> {
            if (currentWord == null) {
                return;
            }
            if (currentWord.getWord().isEmpty()) {
                return;
            }
            if (englishDAO.deleteWord(currentWord)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(Main.stage);
                alert.setTitle("Success");
                alert.setHeaderText("Result: ");
                alert.setContentText("Delete successfully");
                alert.showAndWait();
                searchBox.setText("");
                suggestionListView.getItems().clear();
                currentWord = null;
                countSuggestion.setText("");
                definition.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(Main.stage);
                alert.setTitle("Fail");
                alert.setHeaderText("Result: ");
                alert.setContentText("Fail to delete");
                alert.showAndWait();
            }
        });
    }

    @FXML
    public void handleUpdate(MouseEvent mouseEvent) {
        updateBtn.setOnMouseClicked(e -> {
            if (currentWord == null) {
                return;
            }

            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Word information");
            dialog.initOwner(Main.stage);
            ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, cancelButtonType);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            Label wordLabel = new Label("Word: ");
            TextField wordField = new TextField(currentWord.getWord());
            wordField.setEditable(false);
            gridPane.add(wordLabel, 0, 0);
            gridPane.add(wordField, 1, 0);

            Label vietnamLabel = new Label("Vietnamese meaning: ");
            TextField vietnamField = new TextField(currentWord.getVietnamese());
            gridPane.add(vietnamLabel, 0, 1);
            gridPane.add(vietnamField, 1, 1);

            Label typeLabel = new Label("Part of speech: ");
            TextField typeField = new TextField(currentWord.getType());
            gridPane.add(typeLabel, 0, 2);
            gridPane.add(typeField, 1, 2);

            Label meaningLabel = new Label("Definition: ");
            TextArea meaningField = new TextArea(currentWord.getMeaning());
            meaningField.setWrapText(true);
            gridPane.add(meaningLabel, 0, 3);
            gridPane.add(meaningField, 1, 3);

            Label pronunciationLabel = new Label("Pronunciation: ");
            TextField pronunciationField = new TextField(currentWord.getPronunciation());
            gridPane.add(pronunciationLabel, 0, 4);
            gridPane.add(pronunciationField, 1, 4);

            Label synonymLabel = new Label("Synonym: ");
            TextField synonymField = new TextField(currentWord.getSynonym());
            gridPane.add(synonymLabel, 0, 5);
            gridPane.add(synonymField, 1, 5);

            Label antonymLabel = new Label("Antonym: ");
            TextField antonymField = new TextField(currentWord.getAntonyms());
            gridPane.add(antonymLabel, 0, 6);
            gridPane.add(antonymField, 1, 6);

            Label exampleLabel = new Label("Example: ");
            TextArea exampleField = new TextArea(currentWord.getExample());
            exampleField.setWrapText(true);
            gridPane.add(exampleLabel, 0, 7);
            gridPane.add(exampleField, 1, 7);

            dialog.getDialogPane().setContent(gridPane);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    return "update";
                }
                return null;
            });
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(present -> {
                String meaning = meaningField.getText();
                String vietnamese = vietnamField.getText();
                String type = typeField.getText();
                String pronunciation = pronunciationField.getText();
                String example = exampleField.getText();
                String synonym = synonymField.getText();
                String antonym = antonymField.getText();
                if (englishDAO.updateMeaning(currentWord, meaning) &&
                        englishDAO.updateVietnamese(currentWord, vietnamese) &&
                        englishDAO.updateType(currentWord, type) &&
                        englishDAO.updatePronunciation(currentWord, pronunciation) &&
                        englishDAO.updateExample(currentWord, example) &&
                        englishDAO.updateSynonym(currentWord, synonym) &&
                        englishDAO.updateAntonyms(currentWord, antonym)) {
                    definition.setText(englishDAO.getDefinition(currentWord));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Result: ");
                    alert.setContentText("Update successfully");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Fail");
                    alert.setHeaderText("Result: ");
                    alert.setContentText("Fail to update");
                    alert.showAndWait();
                }
            });
        });
    }

    @FXML
    public void handleVoice(MouseEvent mouseEvent) {
        phoneticBtn.setOnMouseClicked(e -> {
            try {
                if (currentWord != null) {
                    VoiceText.playVoice(currentWord.getWord(), "en");
                }
            } catch (Exception e1) {
                System.err.println(e1.getMessage());
            }
        });
    }
}
