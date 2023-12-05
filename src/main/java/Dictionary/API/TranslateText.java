package Dictionary.API;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TranslateText {
    public static String translate(String encodedText, String fromLanguage, String toLanguage) throws Exception {
        String uri = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" +
                URLEncoder.encode(fromLanguage, StandardCharsets.UTF_8) + "&tl=" + URLEncoder.encode(toLanguage, StandardCharsets.UTF_8) +
                "&dt=t&text=" + URLEncoder.encode(encodedText, StandardCharsets.UTF_8) + "&op=translate";

        HttpURLConnection connection = null;
        connection = (HttpURLConnection) new URI(uri).toURL().openConnection();
        connection.setRequestMethod("GET");

        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }

        String jsonString = textBuilder.toString();
        JSONTokener tokener = new JSONTokener(jsonString);
        JSONArray array = new JSONArray(tokener);
        JSONArray array1 = array.getJSONArray(0);
        String sourceLanguage = null;
        try {
            sourceLanguage = array.getString(0);
        } catch (Exception e2) {}
        if (sourceLanguage != null && sourceLanguage.contains("-")) {
            sourceLanguage = sourceLanguage.substring(0, sourceLanguage.indexOf('-'));
        }
        String result = "";
        for (int i = 0; i < array1.length(); ++i) {
            String blockText = array1.getJSONArray(i).getString(0);
            if (blockText != null && !blockText.equals("null"))
                result += blockText;
        }
        if (!encodedText.isEmpty() && encodedText.charAt(0) == '\n')
            result = "\n" + result;
        return result;
    }
}
