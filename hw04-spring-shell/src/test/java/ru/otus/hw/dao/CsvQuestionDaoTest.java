package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CsvQuestionDaoTest {

    @Autowired
    private QuestionDao csvQuestionDao;

    List<Question> questions;

    @BeforeEach
    void setUp() {
        questions = csvQuestionDao.findAll();
    }

    @Test
    @DisplayName("questions and answers are equal to provided resource")
    void readObjectFromResource() {
        assertEquals("How often should you feed a cat?", questions.get(0).text());
        assertEquals("Food should always be in the bowl", questions.get(0).answers().get(1).text());
    }

    @Test
    @DisplayName("must return non-empty list")
    void questionListNotEmpty() {
        assertNotNull(questions);
        assertEquals(5, questions.size());
    }

    @Test
    @DisplayName("question texts are not empty")
    void questionTextIsNotBlank() {
        final boolean isAllNotBlank = questions.stream().noneMatch(question -> question.text().isBlank());
        assertTrue(isAllNotBlank);
    }

    @Test
    @DisplayName("right answer exists")
    void questionTextIsNotEmpty() {
        final boolean isRightAnswerExists = questions.stream()
                .flatMap(question -> question.answers().stream())
                .anyMatch(Answer::isCorrect);
        assertTrue(isRightAnswerExists);
    }

    @Test
    @DisplayName("must throw if resource not exists")
    void findAllThrowsException() {
        csvQuestionDao = new CsvQuestionDao(null);
        assertThrows(QuestionReadException.class, () -> {
            csvQuestionDao.findAll();
        });
    }
}
