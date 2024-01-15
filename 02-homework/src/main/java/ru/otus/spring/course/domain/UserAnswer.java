package ru.otus.spring.course.domain;

import lombok.Getter;

public class UserAnswer {
    @Getter
    private final int questionId;

    @Getter
    private final int answerId;

    public UserAnswer(Integer questionId, int answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
    }

}
