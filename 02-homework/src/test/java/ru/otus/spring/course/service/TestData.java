package ru.otus.spring.course.service;

import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.domain.Score;
import ru.otus.spring.course.domain.UserAnswer;
import ru.otus.spring.course.domain.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    UserInfo userInfo = new UserInfo("Ivan", "Kruzenshtern");
    Score score = new Score(5, userInfo);
    Answer answer = new Answer(1, "Somali", true);
    List<Answer> answerList = new ArrayList<>();
    public List<Question> questionList = new ArrayList<>();

    public Question question;

    {
        answerList.add(answer);
        question = new Question(1, "A person who lives in Somalia is", answerList);
        questionList.add(question);
    }


    List<UserAnswer> userAnswerList = new ArrayList<>();
    UserAnswer userAnswer = new UserAnswer(1, 1);


}