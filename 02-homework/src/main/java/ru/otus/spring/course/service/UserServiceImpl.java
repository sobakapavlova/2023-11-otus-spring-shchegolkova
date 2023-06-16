package ru.otus.spring.course.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.otus.spring.course.domain.Console;
import ru.otus.spring.course.domain.Score;
import ru.otus.spring.course.domain.UserInfo;
import ru.otus.spring.course.service.console.ScoreConsoleServiceImpl;

@Service
public class UserServiceImpl implements UserService {

    final private ScoreConsoleServiceImpl scoreConsoleService;


    public UserServiceImpl(ScoreConsoleServiceImpl scoreConsoleService) {
        this.scoreConsoleService = scoreConsoleService;
    }

    @Override
    public UserInfo collectInfo() {
        scoreConsoleService.display(Console.PRINT_FIRSTNAME.getStr());
        final String firstName = scoreConsoleService.read();
        scoreConsoleService.display(Console.PRINT_LASTNAME.getStr());
        final String lastName = scoreConsoleService.read();
        return new UserInfo(firstName, lastName);
    }

    @Override
    public void greet(UserInfo userInfo) {
        final String formattedText = Console.GREETING.getStr().formatted(userInfo.getFirstName(), userInfo.getLastName());
        scoreConsoleService.display(formattedText);
    }

    @Override
    public void getUserResult(Score score) {
        final String formattedText = Console.RESULT.getStr().formatted(score.getUserInfo().getFirstName(),
                score.getUserInfo().getLastName(),
                score.getPoints());
        scoreConsoleService.display(formattedText);
    }
}
