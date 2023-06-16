package ru.otus.spring.course.dao;

import org.junit.jupiter.api.Test;
import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.service.TestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVQuestionDAOImplTest {

    private final Question question = new TestData().question;
    private final List<Question> expectedQuestionList = new TestData().questionList;
    CSVQuestionDAOImpl dao = new CSVQuestionDAOImpl("questions.csv");


    @Test
    void testGetAllReturnsQuestionList() {
        List<Question> questionList = dao.getAll();
        assertNotNull(questionList);
        assertNotNull(questionList.get(0));
    }

    @Test
    void testGetAllReturnsCorrectQuestionList() {
        List<Question> questionList = dao.getAll();
        expectedQuestionList.add(question);

        assertNotNull(questionList);
        assertEquals(5, questionList.size());
        assertEquals(1, questionList.get(0).getId());
        assertEquals(expectedQuestionList.get(0).getQuestionText(), questionList.get(0).getQuestionText());
        assertEquals(expectedQuestionList.get(0).getAnswerList().get(0).getAnswerText(), questionList.get(0).getAnswerList().get(0).getAnswerText());
        assertEquals(expectedQuestionList.get(0).getAnswerList().get(0).getIsCorrect(), questionList.get(0).getAnswerList().get(0).getIsCorrect());
    }

    @Test
    void testGetAnswerListByIdReturnsAnswerList() {
        List<Answer> answerList = dao.getAnswerListById(1);
        assertNotNull(answerList);
        assertNotNull(answerList.get(0));
    }

    @Test
    void testGetAnswerListByIdReturnsCorrectAnswerList() {
        List<Answer> answerList = dao.getAnswerListById(1);
        assertNotNull(answerList);
        assertEquals(3, answerList.size());
        assertEquals(1, answerList.get(0).getId());
        assertEquals(answerList.get(0).getAnswerText(), answerList.get(0).getAnswerText());
        assertEquals(answerList.get(0).getIsCorrect(), answerList.get(0).getIsCorrect());
    }

    @Test
    void testGetAllThrowsIllegalArgumentExceptionWhenResourceNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            CSVQuestionDAOImpl dao = new CSVQuestionDAOImpl("doesnotexist.csv");
            dao.getAll();
        });
    }
}