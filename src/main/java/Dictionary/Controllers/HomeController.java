package Dictionary.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class HomeController {

    @FXML
    private Button playQuizBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private void initialize() {

        playQuizBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AnchorPane component = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/UI/Quiz/quiz.fxml")));
                    anchorPane.getChildren().clear();
                    anchorPane.getChildren().add(component);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        });

    }

}
