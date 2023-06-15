package ru.otus.spring.course.service;

import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.utils.Formatter;

import java.util.List;

public class ConsoleServiceImpl implements ConsoleService {

    @Override
    public void display(List<Question> quiz) {
        quiz.forEach(
                question -> {
                    StringBuilder sb = new Formatter()
                            .formatPrint(question.getId(), question.getQuestion(), question.getAnswerList());
                    System.out.println(sb);
                }
        );
    }

}
