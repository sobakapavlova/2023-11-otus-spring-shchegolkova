package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private static final String ERROR_MESSAGE = "TestService.error.incorrect.input";

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            List<Answer> answers = question.answers();

            ioService.printLine(getQuestionText(question, answers));
            int userAnswer = ioService.readIntForRangeLocalized(1, answers.size(), ERROR_MESSAGE);
            boolean answerValid = isRightAnswer(answers, userAnswer);
            testResult.applyAnswer(question, answerValid);
        }
        return testResult;
    }

    private String getQuestionText(Question question, List<Answer> answers) {
        List<String> list = new ArrayList<>();
        for (int i = 0, answersSize = answers.size(); i < answersSize; i++) {
            Answer answer = answers.get(i);
            String text = answer.text();
            list.add("%d. %s".formatted(i + 1, text));
        }
        return "%s... %s".formatted(question.text(), list);
    }

    private boolean isRightAnswer(List<Answer> answers, int userAnswer) {
        return answers.get(userAnswer - 1).isCorrect();
    }

}
