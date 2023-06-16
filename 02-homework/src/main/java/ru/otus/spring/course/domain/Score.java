package ru.otus.spring.course.domain;

import lombok.Getter;

public class Score {
    @Getter
    private final int points;

    @Getter
    private final UserInfo userInfo;

    public Score(int points, UserInfo userInfo) {
        this.points = points;
        this.userInfo = userInfo;
    }
}
