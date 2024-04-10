package com.example.wordcountanalyzer.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/words")
public class WordController {

    @Autowired
    private RestTemplate restTemplate;
    private final String CLIENT_ID = "xxrO185kWvSjoSv14KbA";
    private final String CLIENT_SECRET = "gADnjha0YA";

    public WordController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{word}/meaning")
    public String getWordMeaning(@PathVariable String word) {
        String apiUrl = "https://openapi.naver.com/v1/search/encyc.json?query=" + word;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        String meaning = extractMeaningFromJson(response.getBody());

        return meaning;
    }

    private String extractMeaningFromJson(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray items = jsonObject.getJSONArray("items");

        if (items.length() > 0) {
            JSONObject firstItem = items.getJSONObject(0);
            String description = firstItem.getString("description");
            description = description.replaceAll("<b>|</b>|&quot;", ""); // 불필요한 글자 제거
            return description;
        }

        return "단어의 뜻을 찾을 수 없습니다.";
    }
}