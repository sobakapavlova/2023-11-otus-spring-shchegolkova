package ru.otus.spring.course.service.console;

import ru.otus.spring.course.domain.Question;

import java.util.List;

public interface ConsoleService<T> {
    void display(T t);

    void display(String str);

    <R> R read(T t);

    <R> R read();
}
