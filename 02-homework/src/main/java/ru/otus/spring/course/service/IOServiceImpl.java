package ru.otus.spring.course.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private final PrintStream outputStream;

    private final Scanner inputStream;

    public IOServiceImpl() {
        outputStream = System.out;
        inputStream = new Scanner(System.in);
    }

    @Override
    public void display(String str) {
        outputStream.println(str);
    }

    @Override
    public String read() {
        return inputStream.nextLine();
    }
}
