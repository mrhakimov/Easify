package hakimov.easify.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hakimov.easify.domain.Text;
import hakimov.easify.exception.ValidationException;
import hakimov.easify.service.EasifyService;
import hakimov.easify.util.BindingResultUtils;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class TextController extends ApiController {
    private final EasifyService easifyService;
    private final String THESAURUS_KEY = "EYoF3bv0HygjOv8YZDbg";

    public TextController(EasifyService easifyService) {
        this.easifyService = easifyService;
    }
    final String endpoint = "http://thesaurus.altervista.org/thesaurus/v1";

    public String SendRequest(String word, String language, String key, String output) {
        try {
            URL serverAddress = new URL(endpoint + "?word=" + URLEncoder.encode(word, "UTF-8") + "&language=" + language + "&key=" + key + "&output=" + output);
            HttpURLConnection connection = (HttpURLConnection) serverAddress.openConnection();
            connection.connect();
            int rc = connection.getResponseCode();
            if (rc == 200) {
                String line;
                BufferedReader br = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null)
                    sb.append(line).append('\n');
                JSONObject obj = (JSONObject) JSONValue.parse(sb.toString());
                JSONArray array = (JSONArray) obj.get("response");
                List<String> words = new ArrayList<>();

                for (Object o : array) {
                    JSONObject list = (JSONObject) ((JSONObject) o).get("list");
                    String[] synonyms = list.get("synonyms").toString().split("[|]");
                    for (String synonym : synonyms) {
                        int index = synonym.length();
                        int indexX = synonym.indexOf('(');
                        if (indexX != -1) {
                            index = indexX;
                        }

                        if (index != 0) {
                            words.add(synonym.substring(0, index));
                        }
                    }
                }

                words.sort(Comparator.comparingInt(String::length));
                System.out.println("Should be " + words.get(0));
                return words.get(0);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return word;
    }

    @PostMapping("easify")
    public String easify(@RequestBody @Valid Text mainText, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }

        String text = mainText.getText();
        if (isLatinLetter(text.charAt(text.length() - 1))) {
            text += ' ';
        } else {
            text += 'a';
        }

        StringBuilder temp = new StringBuilder();
        List<String> words = new ArrayList<>();

        for (int i = 0; i < text.length(); ++i) {
            boolean statement1 = (i == 0 || (i > 0 && !isLatinLetter(text.charAt(i - 1)))) && (temp.length() > 0);
            boolean statement2 = (i == 0 || (i > 0 && isLatinLetter(text.charAt(i - 1)))) && (temp.length() > 0);

            if (isLatinLetter(text.charAt(i))) {
                if (statement1) {
                    words.add(temp.toString());
                    temp = new StringBuilder();
                }
            } else {
                if (statement2) {
                    String word = temp.toString();
                    word = SendRequest(word, "en_US", THESAURUS_KEY, "json");
                    System.out.println("Give " + word);
                    words.add(word);
                    temp = new StringBuilder();
                }
            }

            temp.append(text.charAt(i));
        }

        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word);
        }

        System.out.println("Result : " + result.toString());
        return result.toString();
    }

    private boolean isLatinLetter(char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
    }
}
