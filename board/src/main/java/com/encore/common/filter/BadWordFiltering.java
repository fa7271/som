package com.encore.common.filter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BadWordFiltering extends HashSet<String> implements BadWords {
    private String substituteValue = "*";

    public BadWordFiltering() {
        addAll(List.of(koreaWord1));
    }

    public String pre_change(String text) {
        Pattern pattern = Pattern.compile("[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z\\s]");
        Matcher matcher = pattern.matcher(text);

        StringBuilder filteredTextBuilder = new StringBuilder();
        while (matcher.find()) {
            filteredTextBuilder.append(matcher.group());
        }
        return filteredTextBuilder.toString();
    }
    public String export_html(String html_text) {
        Document doc = Jsoup.parse(html_text);
        for (Element element : doc.getAllElements()) {
            // 구분자 제거
            String pureBadWord = pre_change(element.ownText());
            // 태그 내용이 있는 경우에만 처리
            if (!element.ownText().isEmpty()) {

                String filteredText = change(pureBadWord);
                element.text(filteredText); // 욕설 필터링된 텍스트로 변경
            }
        }
        return doc.outerHtml();
    }


    public String change(String text) {
        for (String badWord : this) {
//            대소문자를 무시하고 정규 표현식을 만듭니다.
            Pattern pattern = Pattern.compile("(?i)" + Pattern.quote(badWord));
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();

                String sub = this.substituteValue.repeat(end - start);
                text = text.substring(0, start) + sub + text.substring(end);
            }
        }
        return text;
    }

    //    badWordFiltering.check(contents)를 호출하면
//    내부적으로 HashSet에 있는 나쁜 단어 목록을 확인하여 주어진 텍스트(contents)에
//    해당 단어가 포함되어 있는지 여부를 판단
    public boolean check(String text) {
        return stream().anyMatch(text::contains);
    }
}
