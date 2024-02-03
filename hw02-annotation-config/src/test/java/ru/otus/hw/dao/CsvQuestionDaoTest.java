package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CsvQuestionDaoTest {

    AppProperties appProperties = mock(AppProperties.class);

    private QuestionDao csvQuestionDao;

    @BeforeEach
    void setUp() {
        csvQuestionDao = new CsvQuestionDao(appProperties);
    }

    @Test
    @DisplayName("findAll() must return list of questions from provided resource")
    void findAll() {
        when(appProperties.getTestFileName()).thenReturn("questions.csv");
        List<Question> questions = csvQuestionDao.findAll();

        assertNotNull(questions);
        assertEquals(5, questions.size());
        assertEquals("A person who lives in Somalia is", questions.get(0).text());
        assertEquals("Somali", questions.get(0).answers().get(0).text());
        assertTrue(questions.get(0).answers().get(0).isCorrect());
        assertTrue(questions.stream().allMatch(question -> countAnswers(question, true) == 1));
        assertTrue(questions.stream().allMatch(question -> countAnswers(question, false) == 2));
    }

    @Test
    @DisplayName("findAll() must throws if resource not exists")
    void findAllThrowsException() {
        {
            when(appProperties.getTestFileName()).thenReturn("notExists.csv");
            assertThrows(IllegalArgumentException.class, () -> {
                csvQuestionDao.findAll();
            });
        }
    }

    private long countAnswers(Question question, Boolean isCorrect) {
        return question.answers().stream().filter(answer -> answer.isCorrect() == isCorrect).count();
    }
}