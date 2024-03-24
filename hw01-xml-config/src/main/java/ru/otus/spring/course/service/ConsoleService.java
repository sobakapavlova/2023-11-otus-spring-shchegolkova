package ru.otus.spring.course.service;

import ru.otus.spring.course.domain.Question;

import java.util.List;

public interface ConsoleService {
    void display(List<Question> quiz);
}
