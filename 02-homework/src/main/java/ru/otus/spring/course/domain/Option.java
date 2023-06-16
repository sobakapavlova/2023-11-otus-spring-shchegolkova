package ru.otus.spring.course.domain;

import lombok.Getter;

public enum Option {
    A(1),
    B(2),
    C(3);

    @Getter
    private final int id;

    Option(int id) {
        this.id = id;
    }

    public static Option getByChar(char c) {
        return switch (c) {
            case 'a', 'A' -> A;
            case 'b', 'B' -> B;
            case 'c', 'C' -> C;
            default -> throw new IllegalArgumentException("Invalid character: " + c);
        };
    }

}
