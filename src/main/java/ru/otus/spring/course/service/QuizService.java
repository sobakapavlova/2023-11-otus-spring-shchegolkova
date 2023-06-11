package ru.otus.spring.course.service;

import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;

import java.util.List;
import java.util.Map;

public interface QuizService {
    Map<Question, List<Answer>> getQuiz(List<Answer> answers, List<Question> questions);
}
