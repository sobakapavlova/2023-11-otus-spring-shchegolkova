package ru.otus.spring.course.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.course.dao.CSVQuestionDAOImpl;
import ru.otus.spring.course.domain.Console;
import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.domain.Score;
import ru.otus.spring.course.domain.UserAnswer;
import ru.otus.spring.course.domain.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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
        greet(userInfo);
        final Score score = doQuiz(userInfo);
        finishQuiz(score);
    }

    private Score doQuiz(UserInfo userInfo) {
        List<Question> questions = csvQuestionDAO.getAll();
        List<UserAnswer> userAnswerList = new ArrayList<>();
        AtomicInteger score = new AtomicInteger();

        questions.forEach(question -> {
            String formattedQuestion = formatterService.format(question).toString();
            ioService.display(formattedQuestion);

            UserAnswer userAnswer = getAnswerForQuestion(question);
            userAnswerList.add(userAnswer);

            boolean checkResult = checkAnswer(question, userAnswer);
            if (checkResult) {
                score.incrementAndGet();
            }

            displayFeedback(checkResult);
        });

        return new Score(score.get(), userInfo);
    }


    private boolean checkAnswer(Question question, UserAnswer userAnswer) {
        try {
            return question.getAnswerList().get(userAnswer.getAnswerId() - 1).getIsCorrect();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private void displayFeedback(boolean isCorrect) {
        ioService.display(isCorrect ? "Correct!" : "Incorrect.");
    }

    public UserAnswer getAnswerForQuestion(Question question) {
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

    private void greet(UserInfo userInfo) {
        final String formattedText = Console.GREETING
                .getStr()
                .formatted(userInfo.getFirstName(), userInfo.getLastName());
        ioService.display(formattedText);
    }

}
