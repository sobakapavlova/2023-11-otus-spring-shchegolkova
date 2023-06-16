package ru.otus.spring.course.service.console;

import org.springframework.stereotype.Service;
import ru.otus.spring.course.domain.Option;
import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.domain.UserAnswer;
import ru.otus.spring.course.utils.Formatter;

import java.util.Scanner;

@Service
public class QuestionConsoleServiceImpl implements ConsoleService<Question> {

    @Override
    public void display(Question quiz) {
        StringBuilder sb = new Formatter()
                .formatQuestionPrint(quiz.getId(), quiz.getQuestionText(), quiz.getAnswerList());
        System.out.println(sb);
    }

    @Override
    public void display(String str) {
        System.out.println(str);
    }

    @Override
    public String read() {
        return getScanner();
    }

    @Override
    public UserAnswer read(Question question) {
        final String userAnswer = getScanner();
        final int answerId = Option.getByChar(userAnswer.charAt(0)).getId();
        return new UserAnswer(question.getId(), answerId);
    }

    private String getScanner() {
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
