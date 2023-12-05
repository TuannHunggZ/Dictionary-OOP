package Dictionary.Controllers;

import Dictionary.Main;
import Dictionary.Models.English;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Dictionary.Models.DatabaseConfig.englishDAO;

public class AddController implements Initializable {
    @FXML
    TextField wordText, speechText, pronunciationText, synonymText, antonymText, vietnameseText;
    @FXML
    TextArea exampleText, meaningText;
    @FXML
    Button addBtn;

    private String standardization(String s) {
        s = s.toLowerCase();
        s = s.trim();
        if (!s.isEmpty()) {
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
        }
        return s;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wordText.setText("");
        vietnameseText.setText("");
        speechText.setText("");
        pronunciationText.setText("");
        synonymText.setText("");
        antonymText.setText("");
        exampleText.setText("");
        meaningText.setText("");
    }

    public void reset() {
        wordText.setText("");
        vietnameseText.setText("");
        speechText.setText("");
        pronunciationText.setText("");
        synonymText.setText("");
        antonymText.setText("");
        exampleText.setText("");
        meaningText.setText("");
    }

    @FXML
    public void handleAdd(MouseEvent mouseEvent) {
        addBtn.setOnMouseClicked(e -> {
            if (wordText.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(Main.stage);
                alert.setTitle("Fail");
                alert.setHeaderText("Result: ");
                alert.setContentText("Word's field is empty");
                alert.showAndWait();
            } else if (vietnameseText.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(Main.stage);
                alert.setTitle("Fail");
                alert.setHeaderText("Result: ");
                alert.setContentText("Vietnamese meaning's field is empty");
                alert.showAndWait();
            } else if (meaningText.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Fail");
                alert.setHeaderText("Result: ");
                alert.setContentText("Meaning's field is empty");
                alert.showAndWait();
            } else {
                String word = standardization(wordText.getText());
                String vietnamese = standardization(vietnameseText.getText());
                String type = standardization(speechText.getText());
                String pronunciation = standardization(pronunciationText.getText());
                String antonym = standardization(antonymText.getText());
                String synonym = standardization(synonymText.getText());
                String example = standardization(exampleText.getText());
                String meaning = standardization(meaningText.getText());
                try {
                    if (englishDAO.equalQuery(word) != null)
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Fail");
                        alert.setHeaderText("Result: ");
                        alert.setContentText("Word existed in dictionary");
                        alert.showAndWait();
                        return;
                    }
                } catch (SQLException e1) {
                    System.err.println(e1.getMessage());
                }
                English english = new English(word, type, meaning, pronunciation, example, synonym, antonym, vietnamese);
                if (englishDAO.addEnglish(english)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initOwner(Main.stage);
                    alert.setTitle("Success");
                    alert.setHeaderText("Result: ");
                    alert.setContentText("Add word successfully");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initOwner(Main.stage);
                    alert.setTitle("Fail");
                    alert.setHeaderText("Result: ");
                    alert.setContentText("Fail to add word");
                    alert.showAndWait();
                }
            }
        });
    }
}
