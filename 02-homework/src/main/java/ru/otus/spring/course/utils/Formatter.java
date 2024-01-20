package ru.otus.spring.course.utils;

import ru.otus.spring.course.domain.Answer;

import java.util.List;

public class Formatter {

    public StringBuilder formatQuestionPrint(int id, String questionText, List<Answer> answersList) {
        final StringBuilder sb = new StringBuilder();
        sb.append("Question ").append(id).append(" : ").append(questionText);
        if (!answersList.isEmpty()) {
            sb.append("...");
            char option = '1';
            for (Answer answer : answersList) {
                sb.append(" ").append(option).append(")").append(" ").append(answer.getAnswerText());
                option++;
            }
        }
        return sb;
    }
}
