package ru.otus.spring.course.service.console;

import org.springframework.stereotype.Service;
import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.domain.UserAnswer;
import ru.otus.spring.course.utils.Formatter;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.Set;

@Service
public class QuestionConsoleServiceImpl implements ConsoleService<Question> {

    private final PrintStream outputStream;
    private final Scanner inputStream;

    public QuestionConsoleServiceImpl() {
        outputStream = System.out;
        inputStream = new Scanner(System.in);
    }

    @Override
    public void display(Question quiz) {
        StringBuilder sb = new Formatter()
                .formatQuestionPrint(quiz.getId(), quiz.getQuestionText(), quiz.getAnswerList());
        System.out.println(sb);
    }

    @Override
    public void display(String str) {
        outputStream.println(str);
    }

    @Override
    public String read() {
        return inputStream.nextLine();
    }

    public UserAnswer read(Question question) {
        String userAnswer = inputStream.nextLine();
        while (!checkValidInput(userAnswer)) {
            outputStream.println("Wrong input, try another option:");
            userAnswer = inputStream.nextLine();
        }
        return new UserAnswer(question.getId(), Integer.parseInt(userAnswer));
    }

    private boolean checkValidInput(String userAnswer) {
        final Set<String> validOption = Set.of("1", "2", "3");
        return validOption.contains(userAnswer);
    }
}
