package ru.otus.spring.course.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.otus.spring.course.dao.CSVQuestionDAOImpl;
import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.domain.Score;
import ru.otus.spring.course.domain.UserAnswer;
import ru.otus.spring.course.domain.UserInfo;
import ru.otus.spring.course.service.console.QuestionConsoleServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final UserService userService;

    private final CSVQuestionDAOImpl csvQuestionDAO;

    private final QuestionConsoleServiceImpl questionConsoleService;

    public QuizServiceImpl(UserService userService, CSVQuestionDAOImpl csvQuestionDAO, QuestionConsoleServiceImpl questionConsoleService) {
        this.userService = userService;
        this.csvQuestionDAO = csvQuestionDAO;
        this.questionConsoleService = questionConsoleService;
    }

    @Override
    public UserInfo startQuiz() {
        final UserInfo userInfo = userService.collectInfo();
        userService.greet(userInfo);
        return userInfo;
    }

    @Override
    public Score doQuiz(UserInfo userInfo) {
        List<Question> questions = csvQuestionDAO.getAll();
        List<UserAnswer> userAnswerList = new ArrayList<>();
        int score = 0;
        for (Question question : questions) {
            questionConsoleService.display(question);
            final UserAnswer userAnswer = questionConsoleService.read(question);
            userAnswerList.add(userAnswer);
            final List<Answer> answerToCheck = csvQuestionDAO.getAnswerListById(userAnswer.getQuestionId());
            final Boolean checkResult = answerToCheck.get(userAnswer.getAnswerId() - 1).getIsCorrect();
            if (checkResult)
                score += 1;
            questionConsoleService.display(String.valueOf(checkResult));
        }
        return new Score(score, userInfo);
    }


    @Override
    public void finishQuiz(Score score) {
        userService.getUserResult(score);
    }

}
