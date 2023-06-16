package ru.otus.spring.course.utils;

import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Console;
import ru.otus.spring.course.domain.Score;
import ru.otus.spring.course.domain.UserInfo;

import java.util.List;

public class Formatter {

    public StringBuilder formatQuestionPrint(int id, String questionText, List<Answer> answersList) {
        final StringBuilder sb = new StringBuilder();
        sb.append("Question ").append(id).append(" : ").append(questionText);
        if (!answersList.isEmpty()) {
            sb.append("...");
            char letter = 'a';
            for (Answer answer : answersList) {
                sb.append(" ").append(letter).append(")").append(" ").append(answer.getAnswerText());
                letter++;
            }
        }
        return sb;
    }
}
