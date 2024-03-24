package ru.otus.spring.course.dao;

import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.service.ConsoleServiceImpl;
import ru.otus.spring.course.utils.ObjectConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<String> lines = new BufferedReader(inputStreamReader)
                    .lines()
                    .skip(1)
                    .toList();
            List<Question> questions = lines
                    .stream()
                    .map(this::readLineToQuestion)
                    .toList();
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
        Map<Integer, Question> questionMap = new HashMap<>();
        for (Question question : questions) {
            int questionId = question.getId();
            if (questionMap.containsKey(questionId)) {
                Question existingQuestion = questionMap.get(questionId);
                existingQuestion.getAnswerList().addAll(question.getAnswerList());
            } else {
                questionMap.put(questionId, question);
            }
        }
        return new ArrayList<>(questionMap.values());
    }
}