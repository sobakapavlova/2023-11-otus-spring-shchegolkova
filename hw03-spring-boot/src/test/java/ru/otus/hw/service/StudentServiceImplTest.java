package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    LocalizedIOService ioService = mock(LocalizedIOService.class);
    StudentServiceImpl studentService = new StudentServiceImpl(ioService);

    @Test
    @DisplayName("determine current student correctly")
    void determineCurrentStudent() {
        Student expectedResult = new Student("Cute", "Cat");
        when(ioService.readStringWithPromptLocalized("StudentService.input.first.name")).thenReturn(expectedResult.firstName());
        when(ioService.readStringWithPromptLocalized("StudentService.input.last.name")).thenReturn(expectedResult.lastName());
        Student currentResult = studentService.determineCurrentStudent();
        assertNotNull(currentResult);
        assertEquals(expectedResult.firstName(), currentResult.firstName());
        assertEquals(expectedResult.lastName(), currentResult.lastName());
    }
}

