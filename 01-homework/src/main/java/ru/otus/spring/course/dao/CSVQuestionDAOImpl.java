package ru.otus.spring.course.dao;

import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.service.ConsoleServiceImpl;
import ru.otus.spring.course.utils.ObjectConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVQuestionDAOImpl implements CSVQuestionDAO {
    public static final String SEPARATOR = ";";

    private final String resourceName;

    public CSVQuestionDAOImpl(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public List<Question> getAll() {
        try (InputStream inputStream = ConsoleServiceImpl.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourceName);
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.readLine();
            List<Question> questions = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                questions.add(readLineToQuestion(line));
            }
            return makeAnswerOptionsForQuestion(questions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Question readLineToQuestion(String line) {
        String[] values = line.split(SEPARATOR);
        return new ObjectConverter().convertToQuestion(values);
    }

    private List<Question> makeAnswerOptionsForQuestion(List<Question> questions) {
        List<Question> newQuestions = new ArrayList<>();
        for (Question question : questions) {
            boolean questionExists = false;
            for (Question newQuestion : newQuestions) {
                if (question.getId() == newQuestion.getId() &&
                        question.getQuestion().equals(newQuestion.getQuestion())) {
                    newQuestion.getAnswerList().addAll(question.getAnswerList());
                    questionExists = true;
                    break;
                }
            }
            if (!questionExists) {
                List<Answer> answers = new ArrayList<>(question.getAnswerList());
                Question newQuestion = new Question(question.getId(), question.getQuestion(), answers);
                newQuestions.add(newQuestion);
            }
        }
        return newQuestions;
    }
}