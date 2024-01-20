package ru.otus.spring.course.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.course.domain.Console;
import ru.otus.spring.course.domain.Score;
import ru.otus.spring.course.domain.UserInfo;

@Service
public class UserServiceImpl implements UserService {

    private final IOServiceImpl ioService;

    public UserServiceImpl(IOServiceImpl ioService) {
        this.ioService = ioService;
    }

    @Override
    public UserInfo getUser() {
        ioService.display(Console.PRINT_FIRSTNAME.getStr());
        final String firstName = ioService.read();
        ioService.display(Console.PRINT_LASTNAME.getStr());
        final String lastName = ioService.read();
        return new UserInfo(firstName, lastName);
    }

    @Override
    public void greet(UserInfo userInfo) {
        final String formattedText = Console.GREETING
                .getStr()
                .formatted(userInfo.getFirstName(), userInfo.getLastName());
        ioService.display(formattedText);
    }

    @Override
    public void getUserResult(Score score) {
        final String formattedText = Console.RESULT
                .getStr()
                .formatted(score.getUserInfo().getFirstName(),
                        score.getUserInfo().getLastName(),
                        score.getPoints());
        ioService.display(formattedText);
    }
}
