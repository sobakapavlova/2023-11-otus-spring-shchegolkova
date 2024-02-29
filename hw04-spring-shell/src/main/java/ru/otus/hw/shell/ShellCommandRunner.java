package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.ResultService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandRunner {

    private final StudentService studentService;

    private final TestService testService;

    private final ResultService resultService;

    private Student student;

    private TestResult testResult;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public void login(@ShellOption(defaultValue = "Anonymous") String defaultName) {
        determineOrDefault(defaultName);
    }

    private void determineOrDefault(String defaultName) {
        this.student = studentService.determineCurrentStudent();
        if ((this.student.firstName().isBlank() && this.student.lastName().isBlank())) {
            this.student = new Student(defaultName, defaultName);
        }
    }

    @ShellMethod(value = "Run test command", key = {"run", "r",})
    private void executeTest() {
        this.testResult = testService.executeTestFor(student);
    }

    @ShellMethod(value = "Show score command", key = {"score", "s"})
    @ShellMethodAvailability(value = "isShowScoreCommandAvailable")
    private void showScore() {
        resultService.showResult(testResult);
    }

    private Availability isShowScoreCommandAvailable() {
        return testResult == null ? Availability.unavailable("test not finished") : Availability.available();
    }
}
