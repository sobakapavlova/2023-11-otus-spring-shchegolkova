package ru.otus.spring.course.service;

import lombok.Getter;
import ru.otus.spring.course.dao.CSVQuestionDAO;
import ru.otus.spring.course.domain.Question;

import java.util.List;

public class QuizServiceImpl implements QuizService {

    @Getter
    private final CSVQuestionDAO csvQuestionDAO;

    @Getter
    private final ConsoleService consoleService;

    public QuizServiceImpl(CSVQuestionDAO csvQuestionDAO, ConsoleService consoleService) {
        this.csvQuestionDAO = csvQuestionDAO;
        this.consoleService = consoleService;
    }

    @Override
    public void startQuiz() {
        List<Question> quiz = csvQuestionDAO.getAll();
        consoleService.display(quiz);
    }

}
