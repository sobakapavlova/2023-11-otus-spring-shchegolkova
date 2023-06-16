package ru.otus.spring.course.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.spring.course.domain.Answer;
import ru.otus.spring.course.domain.Question;
import ru.otus.spring.course.service.console.QuestionConsoleServiceImpl;
import ru.otus.spring.course.utils.ObjectConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CSVQuestionDAOImpl implements CSVQuestionDAO {
    public static final String SEPARATOR = ";";

    private final String resourceName;

    public CSVQuestionDAOImpl(@Value("questions.csv") String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public List<Question> getAll() {
        try (InputStream inputStream = QuestionConsoleServiceImpl.class.getClassLoader()
                .getResourceAsStream(resourceName)) {
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


    @Override
    public List<Answer> getAnswerListById(Integer id) {
        final List<Question> questions = getAll();
        return questions.stream()
                .flatMap(question -> question.getAnswerList().stream())
                .filter(answer -> answer.getId() == id)
                .toList();
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
                        question.getQuestionText().equals(newQuestion.getQuestionText())) {
                    newQuestion.getAnswerList().addAll(question.getAnswerList());
                    questionExists = true;
                    break;
                }
            }
            if (!questionExists) {
                List<Answer> answers = new ArrayList<>(question.getAnswerList());
                Question newQuestion = new Question(question.getId(), question.getQuestionText(), answers);
                newQuestions.add(newQuestion);
            }
        }
        return newQuestions;
    }
}