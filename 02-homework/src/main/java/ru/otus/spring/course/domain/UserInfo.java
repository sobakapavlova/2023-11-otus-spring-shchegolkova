package ru.otus.spring.course.domain;

import lombok.Getter;

public class UserInfo {


    @Getter
    private final String firstName;

    @Getter
    private final String lastName;


    public UserInfo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
