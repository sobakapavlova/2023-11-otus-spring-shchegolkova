package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.config.TestAppProperties;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestAppProperties.class)
class TestServiceImplTest {

    @Autowired
    TestService testService;

    @Autowired
    StudentService studentService;

    @Autowired
    CsvQuestionDao questionDao;

    @Test
    @DisplayName("get student test result correctly")
    void executeTestFor() {
        Student student = studentService.determineCurrentStudent();
        TestResult testResult = testService.executeTestFor(student);
        List<Question> questions = questionDao.findAll();
        assertEquals(testResult.getRightAnswersCount(), 2);
        assertEquals(testResult.getAnsweredQuestions(), questions);
        assertEquals(testResult.getStudent(), student);
    }

}