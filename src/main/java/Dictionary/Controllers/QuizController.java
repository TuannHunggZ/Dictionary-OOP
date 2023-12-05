package Dictionary.Controllers;

import Dictionary.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class QuizController {
    @FXML
    public AnchorPane quizAnchorPane;

    @FXML
    public Label question = new Label();

    @FXML
    public Button opt1 = new Button(), opt2 = new Button(), opt3 = new Button(), opt4 = new Button();

    static int counter = 0;
    static int correct = 0;
    static int wrong = 0;

    @FXML
    private void initialize() {
        counter = 0;
        correct = 0;
        wrong = 0;
        loadQuestions();
    }

    private void loadQuestions() {
        if (counter == 0) { //Question 1
            question.setText("1. How many consonants are there in the English alphabet?");
            opt1.setText("19");
            opt2.setText("20");
            opt3.setText("21");
            opt4.setText("22");
        } else if (counter == 1) { //Question 2
            question.setText("2. Who invented the Light bulb?");
            opt1.setText("Thomas Alva Edison");
            opt2.setText("Alexander Fleming");
            opt3.setText("Charles Babbage");
            opt4.setText("Albert Einstein");
        } else if (counter == 2) { //Question 3
            question.setText("3. In the Solar System, farthest planet from the Sun is");
            opt1.setText("Jupiter");
            opt2.setText("Saturn");
            opt3.setText("Uranus");
            opt4.setText("Neptune");
        } else if (counter == 3) { //Question 4
            question.setText("4. Largest moon in the Solar System?");
            opt1.setText("Titan");
            opt2.setText("Ganymede");
            opt3.setText("Moon");
            opt4.setText("Europa");
        } else if (counter == 4) {//Question 5
            question.setText("5. Which of these is 'not' a property of metal?");
            opt1.setText("Good Conduction");
            opt2.setText("Malleable");
            opt3.setText("Non Ductile");
            opt4.setText("Sonourous");
        } else if (counter == 5) { //Question 6
            question.setText("6. Who discovered Pasteurisation?");
            opt1.setText("Alexander Fleming");
            opt2.setText("Louis Pasteur");
            opt3.setText("Simon Pasteur");
            opt4.setText("William Pasteur");
        } else if (counter == 6) { //Question 7
            question.setText("7. Hydrochloric acid (HCl) is produced by -?");
            opt1.setText("Small Intestine");
            opt2.setText("Liver");
            opt3.setText("Oesophagus");
            opt4.setText("Stomach");
        } else if (counter == 7) { //Question 8
            question.setText("8. The fastest animal in the world is -");
            opt1.setText("Lion");
            opt2.setText("Blackbuck");
            opt3.setText("Cheetah");
            opt4.setText("Quarter Horse");
        } else if (counter == 8) { //Question 9
            question.setText("9. Complementary colour of Red is -");
            opt1.setText("Blue");
            opt2.setText("Green");
            opt3.setText("Yellow");
            opt4.setText("Pink");
        } else if (counter == 9) { //Question 10
            question.setText("10. World Environment Day is on -");
            opt1.setText("5th June");
            opt2.setText("5th July");
            opt3.setText("15th June");
            opt4.setText("25th June");
        }

    }

    boolean checkAnswer(String answer) {
        if (counter == 0) {
            if (answer.equals("21")) {
                return true;
            } else {
                return false;
            }
        } else if (counter == 1) {
            if (answer.equals("Thomas Alva Edison")) {
                return true;
            } else {
                return false;
            }
        } else if (counter == 2) {
            if (answer.equals("Neptune")) {
                return true;
            } else {
                return false;
            }
        } else if (counter == 3) {
            if (answer.equals("Ganymede")) {
                return true;
            } else {
                return false;
            }
        } else if (counter == 4) {
            if (answer.equals("Non Ductile")) {
                return true;
            } else {
                return false;
            }
        } else if (counter == 5) {
            if (answer.equals("Louis Pasteur")) {
                return true;
            } else {
                return false;
            }
        } else if (counter == 6) {
            if (answer.equals("Stomach")) {
                return true;
            } else {
                return false;
            }
        } else if (counter == 7) {
            if (answer.equals("Cheetah")) {
                return true;
            } else {
                return false;
            }
        } else if (counter == 8) {
            if (answer.equals("Green")) {
                return true;
            } else {
                return false;
            }
        } else if (counter == 9) {
            if (answer.equals("5th June")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @FXML
    public void opt1clicked(ActionEvent event) {
        boolean check = checkAnswer(opt1.getText());
        if (check) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }

        if (counter == 9) {
            try {
                showResult();
                resetQuiz();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            counter++;
            loadQuestions();
        }

    }

    @FXML
    public void opt2clicked(ActionEvent event) {
        boolean check = checkAnswer(opt2.getText());
        if (check) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }

        if (counter == 9) {
            try {
                showResult();
                resetQuiz();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            counter++;
            loadQuestions();
        }
    }

    @FXML
    public void opt3clicked(ActionEvent event) {
        boolean check = checkAnswer(opt3.getText());
        if (check) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }

        if (counter == 9) {
            try {
                showResult();
                resetQuiz();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            counter++;
            loadQuestions();
        }
    }

    @FXML
    public void opt4clicked(ActionEvent event) {
        boolean check = checkAnswer(opt4.getText());
        if (check) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }

        if (counter == 9) {
            try {
                showResult();
                resetQuiz();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            counter++;
            loadQuestions();
        }
    }

    private void resetQuiz() {
        counter = 0;
        correct = 0;
        wrong = 0;
        loadQuestions();
    }

    private void showResult() throws IOException {
        System.out.println(correct);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(Main.stage);
        alert.setTitle("Result");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        FXMLLoader quiz = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/UI/Quiz/result.fxml")));
        alert.getDialogPane().setContent(quiz.load());
        alert.showAndWait();
    }
}