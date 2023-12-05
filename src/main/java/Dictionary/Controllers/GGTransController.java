package Dictionary.Controllers;

import Dictionary.API.TranslateText;
import Dictionary.API.VoiceText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class GGTransController implements Initializable {
    private final ObservableList<String> languages = FXCollections.observableArrayList("English", "Vietnamese");
    private String sourceCode = "en", targetCode = "vi";
    @FXML
    ComboBox<String> sourceLanguage, targetLanguage;
    @FXML
    TextArea sourceText = new TextArea(), targetText = new TextArea();
    @FXML
    Button sourceVoice, targetVoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sourceLanguage.setItems(languages);
        targetLanguage.setItems(languages);
        sourceLanguage.setValue("English");
        targetLanguage.setValue("Vietnamese");

        sourceLanguage.setOnAction(e -> {
            String source = getLanguageCode(sourceLanguage.getValue());
            if (!source.equals(sourceCode)) {
                targetCode = sourceCode;
                sourceCode = source;

                if (sourceLanguage.getValue().equals("English")) {
                    targetLanguage.setValue("Vietnamese");
                } else {
                    targetLanguage.setValue("English");
                }

                try {
                    if (!sourceText.getText().isEmpty() && !sourceText.getText().isBlank()) {
                        targetText.setText(TranslateText.translate(sourceText.getText(), sourceCode, targetCode));
                    }
                } catch (Exception e1) {
                    System.err.println(e1.getMessage());
                }
            }
        });

        targetLanguage.setOnAction(e -> {
            String target = getLanguageCode(targetLanguage.getValue());
            if (!target.equals(targetCode)) {
                sourceCode = targetCode;
                targetCode = target;

                if (targetLanguage.getValue().equals("English")) {
                    sourceLanguage.setValue("Vietnamese");
                } else {
                    sourceLanguage.setValue("English");
                }

                try {
                    if (!sourceText.getText().isEmpty() && !sourceText.getText().isBlank()) {
                        targetText.setText(TranslateText.translate(sourceText.getText(), sourceCode, targetCode));
                    }
                } catch (Exception e1) {
                    System.err.println(e1.getMessage());
                }
            }
        });
    }

    private String getLanguageCode(String languageName) {
        if (languageName.equals("Vietnamese")) {
            return "vi";
        }
        return "en";
    }

    @FXML
    public void handleSourceTextArea(KeyEvent keyEvent) {
        if (sourceText.getText().isEmpty() || sourceText.getText().isBlank()) {
            targetText.setText("");
            return;
        }
        String sourceCode = getLanguageCode(sourceLanguage.getValue());
        String targetCode = getLanguageCode(targetLanguage.getValue());
        try {
            targetText.setText(TranslateText.translate(sourceText.getText(), sourceCode, targetCode));
        } catch (Exception e1) {
            System.err.println(e1.getMessage());
        }
    }

    @FXML
    public void handleSourceVoice(MouseEvent mouseEvent) {
        sourceVoice.setOnMouseClicked(e -> {
            try {
                if (!sourceText.getText().isEmpty() && !sourceText.getText().isBlank()) {
                    VoiceText.playVoice(sourceText.getText(), sourceCode);
                }
            } catch (Exception e1) {
                System.err.println(e1.getMessage());
            }
        });
    }

    @FXML
    public void handleTargetVoice(MouseEvent mouseEvent) {
        targetVoice.setOnMouseClicked(e -> {
            try {
                VoiceText.playVoice(targetText.getText(), targetCode);
            } catch (Exception e1) {
                System.err.println(e1.getMessage());
            }
        });
    }
}