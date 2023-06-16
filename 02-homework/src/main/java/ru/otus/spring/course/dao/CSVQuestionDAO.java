package ru.otus.spring.course.dao;

import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;

import java.util.List;

public interface CSVQuestionDAO {

    List<Question> getAll();
    List<Answer> getAnswerListById(Integer id);

}