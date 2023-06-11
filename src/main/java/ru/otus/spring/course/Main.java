package ru.otus.spring.course;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.course.service.ConsoleService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        ConsoleService consoleService = context.getBean(ConsoleService.class);
        consoleService.display();
        context.close();
    }
}