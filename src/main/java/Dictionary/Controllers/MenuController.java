package Dictionary.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    Button searchBtn, additionBtn, translateBtn, quizBtn, gameBtn, signOutBtn;
    @FXML
    AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showComponent("/UI/SearchUI.fxml");
        searchBtn.setOnMouseClicked(e -> showComponent("/UI/SearchUI.fxml"));
        additionBtn.setOnMouseClicked(e -> showComponent("/UI/AddUI.fxml"));
        translateBtn.setOnMouseClicked(e -> showComponent("/UI/GGTrans.fxml"));
        quizBtn.setOnMouseClicked(e -> showComponent("/UI/Quiz/home.fxml"));
        gameBtn.setOnMouseClicked(e -> showComponent("/UI/Minigame.fxml"));
        signOutBtn.setOnMouseClicked(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    @FXML
    public void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource(path)));
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(component);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}