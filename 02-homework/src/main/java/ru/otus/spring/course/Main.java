package ru.otus.spring.course;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.course.domain.Score;
import ru.otus.spring.course.domain.UserInfo;
import ru.otus.spring.course.service.QuizService;

@ComponentScan
public class Main {

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuizService quizService = context.getBean(QuizService.class);
        final UserInfo userInfo = quizService.startQuiz();
        final Score score = quizService.doQuiz(userInfo);
        quizService.finishQuiz(score);
        context.close();
    }
}