package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement buttonCode = $("[data-test-id='action-verify']");

    public VerificationPage(){
        codeField.should(Condition.visible);
    }

    public PersonalAccountPage verificationUser(String verificationCode){
        codeField.setValue(verificationCode);
        buttonCode.click();
        return new PersonalAccountPage();
    }
}
