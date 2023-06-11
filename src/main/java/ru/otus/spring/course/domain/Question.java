package ru.otus.spring.course.domain;

import lombok.Getter;

import java.util.Objects;

public class Question {

    @Getter
    private final int id;

    @Getter
    private final String question;

    public Question(int id, String question) {
        this.id = id;
        this.question = question;
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
