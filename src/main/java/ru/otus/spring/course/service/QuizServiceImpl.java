package ru.otus.spring.course.service;

import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuizServiceImpl implements QuizService {

    @Override
    public Map<Question, List<Answer>> getQuiz(List<Answer> answers, List<Question> questions) {
        Map<Integer, List<Answer>> optionalAnswer = groupToOptionalAnswer(answers);
        return mergeQuestionWithAnswers(questions, optionalAnswer);
    }

    private Map<Question, List<Answer>> mergeQuestionWithAnswers(List<Question> questions,
                                                                 Map<Integer, List<Answer>> answersByQuestion) {
        return questions.stream()
                .collect(Collectors.toMap(question -> question,
                        question -> answersByQuestion.getOrDefault(question.getId(), Collections.emptyList())));
    }

    private Map<Integer, List<Answer>> groupToOptionalAnswer(List<Answer> answers) {
        return answers.stream()
                .collect(Collectors.groupingBy(Answer::getId));
    }

}
