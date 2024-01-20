package ru.otus.spring.course.service;

import org.junit.jupiter.api.Test;
import ru.otus.spring.course.domain.Console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserServiceImplTest extends TestData {

    IOServiceImpl ioService = mock(IOServiceImpl.class);

    @Test
    void collectInfo() {
        final String firstName = "Ivan";
        final String lastName = "Kruzenshtern";
        final String firstnameStr = Console.PRINT_FIRSTNAME.getStr();
        final String lastnameStr = Console.PRINT_LASTNAME.getStr();
        ioService.display(firstnameStr);
        ioService.display(lastnameStr);
        assertEquals(firstnameStr, "Print your first name: ");
        assertEquals(lastnameStr, "Print your last name: ");
        assertEquals(userInfo.getFirstName(), firstName);
        assertEquals(userInfo.getLastName(), lastName);
        verify(ioService, times(2)).display(anyString());
    }

    @Test
    void greet() {
        final String formattedText = Console.GREETING.getStr().formatted(userInfo.getFirstName(), userInfo.getLastName());
        assertEquals(formattedText, "\nWelcome, Ivan Kruzenshtern, let's check your knowledge of English. Answer the questions by typing the letter:\n");
        ioService.display(formattedText);
        verify(ioService).display(formattedText);
    }

    @Test
    void getUserResult() {
        final String formattedText = Console.RESULT.getStr().formatted(score.getUserInfo().getFirstName(),
                score.getUserInfo().getLastName(),
                score.getPoints());
        ioService.display(formattedText);
        verify(ioService).display(formattedText);
        assertEquals(formattedText, "\nIvan Kruzenshtern, your score is: 5");
    }
}