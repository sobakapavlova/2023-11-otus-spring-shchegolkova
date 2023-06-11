package ru.otus.spring.course.utils;

import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.service.ConsoleServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ResourceExtractor {
    public static final String SEPARATOR = ";";

    private final String resourceName;

    public ResourceExtractor(String resourceName) {
        this.resourceName = resourceName;
    }

    public BufferedReader getBufferedReader(String resourceName) {
        InputStream inputStream = ConsoleServiceImpl.class.getClassLoader().getResourceAsStream(resourceName);
        if (inputStream == null) {
            throw new IllegalArgumentException("Resource not found: " + resourceName);
        }
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public List<Answer> extractAnswers() throws IOException {
        BufferedReader bufferedReader = getBufferedReader(resourceName);
        bufferedReader.readLine();
        List<Answer> answers = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(SEPARATOR);
            Answer answer = new ObjectConverter().convertToAnswer(values);
            answers.add(answer);
        }
        return answers;
    }

    public List<Question> extractQuestions() throws IOException {
        BufferedReader bufferedReader = getBufferedReader(resourceName);
        bufferedReader.readLine();
        List<Question> questions = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(SEPARATOR);
            Question question = new ObjectConverter().convertToQuestion(values);
            if (!questions.contains(question)) {
                questions.add(question);
            }
        }
        return questions;
    }
}
