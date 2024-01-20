package ru.otus.spring.course;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.course.service.QuizService;

@ComponentScan
public class Main {

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuizService quizService = context.getBean(QuizService.class);
        quizService.runQuiz();
        context.close();
    }
}