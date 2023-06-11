package ru.otus.spring.course.service;

import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.utils.Formatter;
import ru.otus.spring.course.utils.ResourceExtractor;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ConsoleServiceImpl implements ConsoleService {

    private final ResourceExtractor extractor;

    private final QuizService quizService = new QuizServiceImpl();

    public ConsoleServiceImpl(ResourceExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public void display() throws IOException {
        final Map<Question, List<Answer>> quiz = read();
        quiz.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getKey().getId()))
                .forEach(entry -> {
                    Question question = entry.getKey();
                    List<Answer> answers = entry.getValue();
                    StringBuilder sb = new Formatter().formatPrint(question.getId(), question.getQuestion(), answers);
                    System.out.println(sb);
                });
    }

    @Override
    public Map<Question, List<Answer>> read() throws IOException {
        final List<Answer> answers = extractor.extractAnswers();
        final List<Question> questions = extractor.extractQuestions();
        return quizService.getQuiz(answers, questions);
    }

}
