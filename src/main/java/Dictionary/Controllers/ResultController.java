package Dictionary.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController {

    @FXML
    public Label remark, marks, markstext, correcttext, wrongtext;

    @FXML
    public ProgressIndicator correct_progress, wrong_progress;

    @FXML
    private void initialize() {
        correcttext.setText("Correct Answers : " + QuizController.correct);
        wrongtext.setText("Incorrect Answers : " + QuizController.wrong);

        marks.setText(QuizController.correct + "/10");
        float correctf = (float) QuizController.correct / 10;
        correct_progress.setProgress(correctf);

        float wrongf = (float) QuizController.wrong / 10;
        wrong_progress.setProgress(wrongf);


        markstext.setText(QuizController.correct + " Marks Scored");

        if (QuizController.correct < 2) {
            remark.setText("Oh no..! You have failed the quiz. It seems that you need to improve your general knowledge. Practice daily! Check your results here.");
        } else if (QuizController.correct < 5) {
            remark.setText("Oops..! You have scored less marks. It seems like you need to improve your general knowledge. Check your results here.");
        } else if (QuizController.correct <= 7) {
            remark.setText("Good. A bit more improvement might help you to get better results. Practice is the key to success. Check your results here.");
        } else if (QuizController.correct == 8 || QuizController.correct == 9) {
            remark.setText("Congratulations! Its your hard work and determination which helped you to score good marks. Check you results here.");
        } else if (QuizController.correct == 10) {
            remark.setText("Congratulations! You have passed the quiz with full marks because of your hard work and dedication towards studies. Keep it up! Check your results here.");
        }
    }
}
