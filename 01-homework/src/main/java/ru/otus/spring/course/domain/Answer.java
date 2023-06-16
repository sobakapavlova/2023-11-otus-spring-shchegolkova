package ru.otus.spring.course.domain;

import lombok.Getter;

public class Answer {
    @Getter
    private final int id;

    @Getter
    private final String answer;

    @Getter
    private final Boolean isCorrect;

    public Answer(int id, String answer, Boolean isCorrect) {
        this.id = id;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

}
