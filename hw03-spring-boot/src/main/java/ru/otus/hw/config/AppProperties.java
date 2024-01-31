package ru.otus.hw.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.Locale;
import java.util.Map;

@Setter
@EnableConfigurationProperties(AppProperties.class)
@ConfigurationProperties(prefix = "test")
public class AppProperties implements TestConfig, TestFileNameProvider, LocaleConfig {

    @Getter
    @Value("${rightAnswersCountToPass}")
    private int rightAnswersCountToPass;

    @Getter
    @Value("#{systemProperties ['user.region']}")
    private Locale locale;

    @Value("${fileNameByLocaleTag}")
    private Map<String, String> fileNameByLocaleTag;

    public void setLocale(String locale) {
        this.locale = Locale.forLanguageTag(locale);
    }

    @Override
    public String getTestFileName() {
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }
}
