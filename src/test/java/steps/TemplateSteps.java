package steps;

import com.codeborne.selenide.Selenide;
import data.DataHelper;
import io.cucumber.java.After;
import io.cucumber.java.bg.И;
import page.LoginPage;
import page.PersonalAccountPage;
import page.ReplenishCardPage;
import page.VerificationPage;

import static data.DataHelper.*;
import static data.DataHelper.getSecondCardInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateSteps {
    private LoginPage loginPage;
    private VerificationPage verificationPage;
    private PersonalAccountPage personalAccountPage;
    private ReplenishCardPage replenishCardPage;

    @И("пользователь залогинен с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password){
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.loginUser(login, password);
        personalAccountPage = verificationPage.verificationUser(String.valueOf(DataHelper.getverificationCode()));
    }

    @И("пользователь переводит {} рублей с карты с номером {} на свою 1 карту с главной страницы")
    public void login(String sum, String number){
        replenishCardPage =  personalAccountPage.getReplenishCard(getFirstCardInfo().getCardId());
        var transfer = replenishCardPage.getMoneyTransfer(sum,number);
    }

    @И("баланс его 1 карты из списка на главной странице должен стать {} рублей")
    public void login2(String sum){
        String sumWithoutSpaces = sum.replaceAll("\\s+", "");
        int expectedSum = Integer.parseInt(sumWithoutSpaces);

        int balanceFirstCard = personalAccountPage.getBalanceCard(getFirstCardInfo().getCardId());
        assertEquals(expectedSum, balanceFirstCard);
    }

    @After
    public void reverseTransaction() {
        if (personalAccountPage != null) {
            int currentBalanceFirstCard = personalAccountPage.getBalanceCard(getFirstCardInfo().getCardId());

            if (currentBalanceFirstCard != 10_000) {
                int sum = currentBalanceFirstCard - 10_000;
                if (sum > 0) {
                    var replenishCardPage = personalAccountPage.getReplenishCard(getSecondCardInfo().getCardId());
                    replenishCardPage.clearField();
                    var transfer = replenishCardPage.getMoneyTransfer(String.valueOf(sum), getFirstCardInfo().getNumber());
                } else {
                    var replenishCardPage = personalAccountPage.getReplenishCard(getFirstCardInfo().getCardId());
                    replenishCardPage.clearField();
                    var transfer = replenishCardPage.getMoneyTransfer(String.valueOf(-sum), getSecondCardInfo().getNumber());
                }
            }
        }
    }
}