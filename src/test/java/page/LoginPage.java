package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id='login'] input");
    private final SelenideElement passwordField = $("[data-test-id='password'] input");
    private final SelenideElement buttonLogin = $("[data-test-id='action-login']");

    public VerificationPage loginUser(String login, String password){
        loginField.setValue(login);
        passwordField.setValue(password);
        buttonLogin.click();
        return new VerificationPage();
    }
}
