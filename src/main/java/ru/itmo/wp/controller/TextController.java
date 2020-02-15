package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.wp.domain.Text;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.service.EasifyService;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

@RestController
@RequestMapping("/api/1")
public class TextController extends ApiController {
    private final EasifyService easifyService;

    public TextController(EasifyService easifyService) {
        this.easifyService = easifyService;
    }

    @PostMapping("easify")
    public void easify(@RequestBody @Valid Text mainText, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }

        String text = mainText.getText();
        text += ' ';

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
                    new SendRequest(temp.toString(), "en_US", "EYoF3bv0HygjOv8YZDbg", "json");
                    words.add(temp.toString());
                    temp = new StringBuilder();
                }
            }

            temp.append(text.charAt(i));
        }
    }

    private boolean isLatinLetter(char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
    }
}

class SendRequest {
    final String endpoint = "http://thesaurus.altervista.org/thesaurus/v1";

    public SendRequest(String word, String language, String key, String output) {
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
                            System.out.println(synonym.substring(0, index));
                        }
                    }
                }
                System.out.println("========");
            } else System.out.println("HTTP error:" + rc);
            connection.disconnect();
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        } catch (java.net.ProtocolException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
