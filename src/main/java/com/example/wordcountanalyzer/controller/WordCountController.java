package com.example.wordcountanalyzer.controller;

import com.example.wordcountanalyzer.service.WordCountService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class WordCountController {

    private final WordCountService wordCountService;

    public WordCountController(WordCountService wordCountService) {
        this.wordCountService = wordCountService;
    }

    @PostMapping(value = "/api/wordcount/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> analyzeWords(@RequestParam("files") MultipartFile[] files, @RequestParam("count") int count) {
        return wordCountService.analyzeWords(files, count);
    }
}