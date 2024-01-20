package ru.otus.spring.course.service.console;

public interface FormatterService<T> {

    void display(T t);

//    void display(String str);

    <R> R read(T t);

//    <R> R read();
}
