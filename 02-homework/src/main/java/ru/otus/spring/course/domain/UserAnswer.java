package ru.otus.spring.course.domain;

import lombok.Getter;

public class UserAnswer {
    @Getter
    final int questionId;

    @Getter
    final int answerId;
    public UserAnswer(Integer questionId, int answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
    }

}
