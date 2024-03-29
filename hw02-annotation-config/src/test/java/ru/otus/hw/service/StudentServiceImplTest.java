package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    IOService ioService = mock(IOService.class);
    StudentServiceImpl studentService = new StudentServiceImpl(ioService);

    @Test
    void determineCurrentStudent() {
        Student expectedResult = new Student("Cute", "Cat");
        when(ioService.readStringWithPrompt("Please input your first name")).thenReturn(expectedResult.firstName());
        when(ioService.readStringWithPrompt("Please input your last name")).thenReturn(expectedResult.lastName());
        Student currentResult = studentService.determineCurrentStudent();
        assertNotNull(currentResult);
        assertEquals(currentResult.firstName(), expectedResult.firstName());
        assertEquals(currentResult.lastName(), expectedResult.lastName());
    }
}

