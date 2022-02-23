package guru.qa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class NetflixParametrizedTests {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void precondition() {
                open("https://www.netflix.com/by/login");
    }

    @AfterEach
    void closedBrowser() {
        closeWebDriver();
    }

    @ValueSource(strings = {"abc", "de", "r"})
    @ParameterizedTest(name = "Проверка валидности логина")
    void validationLoginTest(String login) {
    //Ввод пароля
    $("#id_password").setValue("qwerty");
    //Ввод логина
    $("#id_userLoginId").setValue(login);
    $(".login-button").click();
    $$(".hybrid-login-form-main").find(text("Please enter a valid email.")).shouldBe(visible);
    }

    @CsvSource(value = {
            "google@gmail.com | qwerty | Incorrect password.",
            "477@rel.com | 1258962222 | Sorry, we can't find an account with this email address."
    }, delimiter = '|')
    @ParameterizedTest(name = "Проверка аутентификации")
    void loginAndPasswordAuthenticationTest(String login, String password, String message) {
        $("#id_password").setValue(password);
        $("#id_userLoginId").setValue(login);
        $(".login-button").click();
        $$(".hybrid-login-form-main").find(text(message)).shouldBe(visible);
    }

}
