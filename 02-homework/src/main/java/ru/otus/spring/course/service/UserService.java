package ru.otus.spring.course.service;

import ru.otus.spring.course.domain.Score;
import ru.otus.spring.course.domain.UserInfo;

public interface UserService {
    UserInfo getUser();

    void greet(UserInfo userInfo);

    void getUserResult(Score score);

}
