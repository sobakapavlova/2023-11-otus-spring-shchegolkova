package ru.otus.spring.course.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;

@Service
public class FormatterServiceImpl implements FormatterService {


    @Override
    public StringBuilder format(Question quiz) {
        final StringBuilder sb = new StringBuilder();
        sb.append("Question ").append(quiz.getId()).append(" : ").append(quiz.getQuestionText());
        if (!quiz.getAnswerList().isEmpty()) {
            sb.append("...");
            char option = '1';
            for (Answer answer : quiz.getAnswerList()) {
                sb.append(" ").append(option).append(")").append(" ").append(answer.getAnswerText());
                option++;
            }
        }
        return sb;
    }
}
