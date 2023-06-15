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
                readLineToQuestion(questions, line);
            }
            return questions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readLineToQuestion(List<Question> questions, String line) {
        String[] values = line.split(SEPARATOR);
        Question question = new ObjectConverter().convertToQuestion(values);
        if (!questions.contains(question)) {
            questions.add(question);
        }
    }
}