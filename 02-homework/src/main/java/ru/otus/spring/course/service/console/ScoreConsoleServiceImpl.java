package ru.otus.spring.course.service.console;

import org.springframework.stereotype.Service;
import ru.otus.spring.course.domain.Score;

import java.util.Scanner;

@Service
public class ScoreConsoleServiceImpl implements ConsoleService<Score> {
    @Override
    public void display(Score score) {
        System.out.println(score);
    }

    @Override
    public void display(String str) {
        System.out.println(str);
    }

    @Override
    public <R> R read(Score score) {
        return null;
    }

    @Override
    public String read() {
        return getScanner();
    }

    private String getScanner() {
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
