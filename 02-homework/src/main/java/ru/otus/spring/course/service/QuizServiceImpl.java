package ru.otus.spring.course.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.course.dao.CSVQuestionDAOImpl;
import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Console;
import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.domain.Score;
import ru.otus.spring.course.domain.UserAnswer;
import ru.otus.spring.course.domain.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class QuizServiceImpl implements QuizService {

    private final UserService userService;

    private final CSVQuestionDAOImpl csvQuestionDAO;

    private final FormatterServiceImpl formatterService;

    private final IOServiceImpl ioService;

    public QuizServiceImpl(UserService userService,
                           CSVQuestionDAOImpl csvQuestionDAO,
                           FormatterServiceImpl formatterService, IOServiceImpl ioService) {
        this.userService = userService;
        this.csvQuestionDAO = csvQuestionDAO;
        this.formatterService = formatterService;
        this.ioService = ioService;
    }

    @Override
    public void runQuiz() {
        final UserInfo userInfo = userService.getUser();
        final Score score = doQuiz(userInfo);
        finishQuiz(score);
    }

    private Score doQuiz(UserInfo userInfo) {
        List<Question> questions = csvQuestionDAO.getAll();
        List<UserAnswer> userAnswerList = new ArrayList<>();
        int score = 0;
        for (Question question : questions) {
            final String formattedQuestion = formatterService.format(question).toString();
            ioService.display(formattedQuestion);
            final UserAnswer userAnswer = mapInput(question);
            userAnswerList.add(userAnswer);
            final List<Answer> answerToCheck = question.getAnswerList();
            final Boolean checkResult = answerToCheck.get(userAnswer.getAnswerId() - 1).getIsCorrect();
            if (checkResult) {
                score += 1;
            }
            ioService.display(String.valueOf(checkResult));
        }
        return new Score(score, userInfo);
    }

    public UserAnswer mapInput(Question question) {
        String userAnswer = ioService.read();
        while (!checkValidInput(userAnswer)) {
            ioService.display("Wrong input, try another option:");
            userAnswer = ioService.read();
        }
        return new UserAnswer(question.getId(), Integer.parseInt(userAnswer));
    }

    private boolean checkValidInput(String userAnswer) {
        final Set<String> validOption = Set.of("1", "2", "3");
        return validOption.contains(userAnswer);
    }

    private void finishQuiz(Score score) {
        final String formattedText = Console.RESULT
                .getStr()
                .formatted(score.getUserInfo().getFirstName(),
                        score.getUserInfo().getLastName(),
                        score.getPoints());
        ioService.display(formattedText);
    }
}
