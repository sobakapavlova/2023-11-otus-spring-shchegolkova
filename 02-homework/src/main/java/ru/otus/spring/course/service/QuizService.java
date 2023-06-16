package ru.otus.spring.course.service;

import ru.otus.spring.course.domain.Score;
import ru.otus.spring.course.domain.UserInfo;

public interface QuizService {
    UserInfo startQuiz();
    Score doQuiz(UserInfo userInfo);
    void finishQuiz(Score score);

}
