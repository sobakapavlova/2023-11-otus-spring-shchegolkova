package ru.otus.spring.course.domain;

import lombok.Getter;

public class Answer {
    @Getter
    private final int id;

    @Getter
    private final String answerText;

    @Getter
    private final Boolean isCorrect;

    public Answer(int id, String answerText, Boolean isCorrect) {
        this.id = id;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

}
