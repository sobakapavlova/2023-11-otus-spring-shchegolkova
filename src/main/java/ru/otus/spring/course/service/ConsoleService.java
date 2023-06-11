package ru.otus.spring.course.service;

import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ConsoleService {
    Map<Question, List<Answer>> read() throws IOException;

    void display() throws IOException;
}
