package Dictionary.API;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class VoiceText {
    public static void playVoice(String text, String languageCode) throws Exception {
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
        HttpURLConnection connection = null;
        int responseCode = 0;
        String url = String.format("https://translate.google.com/translate_tts?ie=UTF-8&tl=%s&client=tw-ob&q=%s", languageCode, encodedText);

        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                 FileOutputStream fileOutputStream = new FileOutputStream("output.mp3")) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }
        } else {
            System.out.println("Error in calling API google translate voice");
        }

        connection.disconnect();

        String audioPath = "output.mp3";

        Media media = new Media((new File(audioPath)).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaPlayer.play();
    }
}
