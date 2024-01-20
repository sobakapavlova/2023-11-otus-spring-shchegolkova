package ru.otus.spring.course.service;

import ru.otus.spring.course.domain.Question;

public interface FormatterService {
    StringBuilder format(Question quiz);
}