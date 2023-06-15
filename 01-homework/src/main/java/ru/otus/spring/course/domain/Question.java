package ru.otus.spring.course.domain;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

public class Question {

    @Getter
    private final int id;

    @Getter
    private final String question;

    @Getter
    private final List<Answer> answerList;

    public Question(int id, String question, List<Answer> answerList) {
        this.id = id;
        this.question = question;
        this.answerList = answerList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question q = (Question) o;
        return id == q.id &&
                Objects.equals(question, q.question);
    }

}
