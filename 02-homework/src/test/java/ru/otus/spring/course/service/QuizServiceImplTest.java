package ru.otus.spring.course.service;

import org.junit.jupiter.api.Test;
import ru.otus.spring.course.dao.CSVQuestionDAOImpl;
import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.domain.Score;
import ru.otus.spring.course.domain.UserAnswer;
import ru.otus.spring.course.domain.UserInfo;
import ru.otus.spring.course.service.console.QuestionConsoleServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class QuizServiceImplTest {

    private final UserServiceImpl userService = mock(UserServiceImpl.class);
    private final QuestionConsoleServiceImpl questionConsoleService = mock(QuestionConsoleServiceImpl.class);
    private final CSVQuestionDAOImpl csvQuestionDAO = mock(CSVQuestionDAOImpl.class);
    private final UserInfo userInfo = new TestData().userInfo;
    private final Question question = new TestData().question;
    private final List<Question> questions = new TestData().questionList;
    private final List<Answer> answerList = new TestData().answerList;
    private final Score scoreObject = new TestData().score;
    private final List<UserAnswer> userAnswerList = new TestData().userAnswerList;
    private final UserAnswer userAnswer = new TestData().userAnswer;

    @Test
    void startQuiz() {
        when(userService.collectInfo()).thenReturn(userInfo);
        userService.collectInfo();
        assertEquals(userInfo.getFirstName(), "Ivan");
        assertEquals(userInfo.getLastName(), "Kruzenshtern");
        verify(userService).collectInfo();
    }

    @Test
    void doQuizWithAllCorrectUserAnswers() {
        questions.add(question);

        when(csvQuestionDAO.getAll()).thenReturn(questions);
        when(csvQuestionDAO.getAnswerListById(any())).thenReturn(answerList);
        when(questionConsoleService.read(question)).thenReturn(userAnswer);

        final int answerId = userAnswer.getAnswerId();
        int score = 0;
        userAnswerList.add(userAnswer);

        for (Question question : questions) {
            questionConsoleService.display(question);
            final List<Answer> answerToCheck = csvQuestionDAO.getAnswerListById(userAnswer.getQuestionId());
            final Boolean checkResult = answerToCheck.get(answerId - 1).getIsCorrect();

            when(answerToCheck).thenReturn(answerList);

            if (checkResult)
                score += 1;

            assertTrue(checkResult);
            questionConsoleService.display(anyString());
        }

        assertEquals(scoreObject.getUserInfo().getFirstName(), userInfo.getFirstName());
        assertEquals(scoreObject.getUserInfo().getLastName(), userInfo.getLastName());
        assertEquals(score, 2);
        verify(questionConsoleService, times(2)).display(anyString());
    }

    @Test
    void finishQuiz() {
        userService.getUserResult(scoreObject);
        verify(userService).getUserResult(scoreObject);
    }
}