package com.example.calculatorapp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionTokenizer {

    public List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();

        String cleanExpression = expression.replaceAll("\\s+", "");

        String regex = "([a-zA-Z]+)|([0-9]*\\.?[0-9]+)|([\\+\\-\\*\\/\\^\\(\\)\\√])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cleanExpression);

        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        return tokens;
    }
}