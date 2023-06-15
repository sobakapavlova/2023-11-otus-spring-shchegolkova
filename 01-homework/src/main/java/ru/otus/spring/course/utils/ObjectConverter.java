package ru.otus.spring.course.utils;

import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class ObjectConverter {
    public Question convertToQuestion(String[] values) {
        int id = Integer.parseInt(values[0]);
        String question = values[1];
        List<Answer> answerList = new ArrayList<>();
        if (convertToAnswer(values).getId() == id) {
            answerList.add(convertToAnswer(values));
        }
        return new Question(id, question, answerList);
    }

    public Answer convertToAnswer(String[] values) {
        int id = Integer.parseInt(values[0]);
        String answer = values[2];
        Boolean isCorrect = Boolean.parseBoolean(values[3]);
        return new Answer(id, answer, isCorrect);
    }
}
