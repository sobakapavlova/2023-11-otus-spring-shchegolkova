package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.config.TestAppProperties;
import ru.otus.hw.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestAppProperties.class)
class StudentServiceImplTest {

    @Autowired
    StudentService studentService;

    @Test
    @DisplayName("determine current student correctly")
    void determineCurrentStudent() {
        Student actualResult = studentService.determineCurrentStudent();
        assertNotNull(actualResult);
        assertEquals("Student", actualResult.firstName());
        assertEquals("Student", actualResult.lastName());
    }
}
