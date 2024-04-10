package com.example.wordcountanalyzer.service;

import java.util.List;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WordCountService {

    public Map<String, Integer> analyzeWords(MultipartFile[] files, int count) {
        // PDF 파일에서 텍스트 추출
        String text = extractTextFromPDFs(files);

        // 형태소 분석 및 어휘 추출
        Map<String, Integer> wordCounts = extractWords(text);

        // 상위 N개 단어 선택

        return getTopWords(wordCounts, count);
    }

    // PDF 파일에서 텍스트 추출 메서드 (Apache PDFBox 라이브러리 사용)
    private String extractTextFromPDFs(MultipartFile[] files) {
        StringBuilder textBuilder = new StringBuilder();

        try {
            for (MultipartFile file : files) {
                PDDocument document = PDDocument.load(file.getInputStream());
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(document);
                textBuilder.append(text);
                document.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return textBuilder.toString();
    }

    // 형태소 분석 및 어휘 추출 메서드
    private Map<String, Integer> extractWords(String text) {
        Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);
        KomoranResult analyzeResult = komoran.analyze(text);
        List<Token> tokenList = analyzeResult.getTokenList();

        Map<String, Integer> wordCounts = new HashMap<>();

        for (Token token : tokenList) {
            String word = token.getMorph();
            String pos = token.getPos();

            if (word.length() >= 2) {
                if (pos.startsWith("NN")) {
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                } else if (pos.startsWith("VV")) {
                    word += "다"; // 동사인 경우 "다"를 붙임
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
            }
        }

        return wordCounts;
    }

    // 상위 N개 단어 선택 메서드
    private Map<String, Integer> getTopWords(Map<String, Integer> wordCounts, int count) {
        return wordCounts.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(count)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }
}