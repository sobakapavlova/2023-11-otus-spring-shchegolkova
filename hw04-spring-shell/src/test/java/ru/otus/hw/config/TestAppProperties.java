package ru.otus.hw.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.otus.hw.service.LocalizedIOService;

@TestConfiguration
public class TestAppProperties {
    public static class NestedConfiguration {

        @Bean
        @Primary
        LocalizedIOService localizedIOService() {

            return new LocalizedIOService() {
                @Override
                public String getMessage(String code, Object... args) {
                    return null;
                }

                @Override
                public void printLine(String s) {
                }

                @Override
                public void printFormattedLine(String s, Object... args) {

                }

                @Override
                public String readString() {
                    return null;
                }

                @Override
                public String readStringWithPrompt(String prompt) {
                    return null;
                }

                @Override
                public int readIntForRange(int min, int max, String errorMessage) {
                    return 3;
                }

                @Override
                public int readIntForRangeWithPrompt(int min, int max, String prompt, String errorMessage) {
                    return 3;
                }

                @Override
                public void printLineLocalized(String code) {

                }

                @Override
                public void printFormattedLineLocalized(String code, Object... args) {

                }

                @Override
                public String readStringWithPromptLocalized(String promptString) {
                    return "Student";
                }

                @Override
                public int readIntForRangeLocalized(int min, int max, String errorMessageCode) {
                    return 3;
                }

                @Override
                public int readIntForRangeWithPromptLocalized(int min, int max, String promptCode, String errorMessageCode) {
                    return 3;
                }

            };
        }
    }

}