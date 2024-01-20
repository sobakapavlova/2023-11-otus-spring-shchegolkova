package ru.otus.spring.course;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Getter
    private static String encoding;

    public AppConfig(@Value("${encoding}") String encoding) {
        this.encoding = encoding;
    }

}
