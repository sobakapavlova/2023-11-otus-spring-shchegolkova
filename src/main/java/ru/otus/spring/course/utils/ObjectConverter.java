package ru.otus.spring.course.utils;

import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;

public class ObjectConverter {
    public Question convertToQuestion(String[] values) {
        int id = Integer.parseInt(values[0]);
        String question = values[1];
        return new Question(id, question);
    }

    public Answer convertToAnswer(String[] values) {
        int id = Integer.parseInt(values[0]);
        String answer = values[2];
        Boolean isCorrect = Boolean.parseBoolean(values[3]);
        return new Answer(id, answer, isCorrect);
    }
}
