package ru.otus.spring.course.utils;

import ru.otus.spring.course.domain.Answer;

import java.util.List;

public class Formatter {
    public StringBuilder formatPrint(int id, String questionText, List<Answer> answersList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Question ").append(id).append(" : ").append(questionText);
        if (!answersList.isEmpty()) {
            sb.append("...");
            char letter = 'a';
            for (Answer answer : answersList) {
                sb.append(" ").append(letter).append(")").append(" ").append(answer.getAnswer());
                letter++;
            }
        }
        return sb;
    }
}
