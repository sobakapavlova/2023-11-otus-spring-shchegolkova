package ru.otus.spring.course.domain;

import lombok.Getter;

public enum Console {
    PRINT_FIRSTNAME("Print your first name: "),
    PRINT_LASTNAME("Print your last name: "),
    GREETING("\nWelcome, %s %s, let's check your knowledge of English. Answer the questions by typing the letter:\n"),
    RESULT("\n%s %s, your score is: %s");
    @Getter
    private final String str;

    Console(String str) {
        this.str = str;
    }
}
