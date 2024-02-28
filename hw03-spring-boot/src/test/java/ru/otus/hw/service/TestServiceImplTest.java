package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestServiceImplTest {

    LocalizedIOService ioService;
    TestServiceImpl testService;
    QuestionDao questionDao;
    Question question;

    Answer answer;

    @BeforeEach
    void setUp() {
        ioService = mock(LocalizedIOService.class);
        questionDao = mock(QuestionDao.class);
        question = mock(Question.class);
        answer = mock(Answer.class);
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    @DisplayName("get student test result correctly")
    void executeTestFor() {
        final Student student = new Student("Cute", "Cat");
        final String ERROR_MESSAGE = "TestService.error.incorrect.input";

        when(question.answers()).thenReturn(List.of(answer));
        when(questionDao.findAll()).thenReturn(List.of(question));
        when(ioService.readIntForRangeLocalized(1, question.answers().size(), ERROR_MESSAGE)).thenReturn(1);

        final TestResult result = testService.executeTestFor(student);

        assertEquals(result.getStudent(), student);
        assertEquals(result.getAnsweredQuestions(), questionDao.findAll());
        assertEquals(result.getRightAnswersCount(), 0);
    }
}