package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        List<Question> questions = null;
        try (InputStreamReader inputStreamReader = getInputStreamReader()) {
            List<QuestionDto> beans = new CsvToBeanBuilder(inputStreamReader)
                    .withType(QuestionDto.class)
                    .withSkipLines(1)
                    .withSeparator(';')
                    .build()
                    .parse();

            questions = beans.stream()
                    .map(QuestionDto::toDomainObject)
                    .toList();

        } catch (IOException e) {
            throw new QuestionReadException("Cannot read from file: ", e);
        }
        return questions;
    }


    private InputStream getFileFromResourceAsStream(String fileNameProvider) {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileNameProvider);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileNameProvider);
        } else {
            return inputStream;
        }

    }

    private InputStreamReader getInputStreamReader() {
        final String testFileName = fileNameProvider.getTestFileName();

        InputStream inputStream = getFileFromResourceAsStream(testFileName);
        return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    }

}
