package steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.bg.И;
import io.cucumber.java.Before;
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

    @Before
    public void setUp(){
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.loginUser(getAuthUser().getLogin(), getAuthUser().getPassword());
        personalAccountPage = verificationPage.verificationUser(String.valueOf(getVerificationCode()));
        reverseTransaction();
    }

    private void reverseTransaction() {
        personalAccountPage = new PersonalAccountPage();
        int balanceCard1 = personalAccountPage.getBalanceCard(1);
        int balanceCard2 = personalAccountPage.getBalanceCard(2);

        if (balanceCard1 != 10_000 || balanceCard2 != 10_000) {
            if (balanceCard1 != 10_000) {
                int sum = balanceCard1 - 10_000;
                if (sum > 0) {
                    personalAccountPage = personalAccountPage
                            .getReplenishCard(2)
                            .getMoneyTransfer(String.valueOf(sum), getFirstCardInfo().getNumber());
                } else {
                    personalAccountPage = personalAccountPage
                            .getReplenishCard(1)
                            .getMoneyTransfer(String.valueOf(-sum), getSecondCardInfo().getNumber());
                }
            }
        }
        clearTransactionForms();
    }

    private void clearTransactionForms() {
        replenishCardPage = personalAccountPage.getReplenishCard(1);
        replenishCardPage.clearField();
        replenishCardPage.buttonCancel();
        new PersonalAccountPage();
    }

    @И("пользователь залогинен с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password){
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.loginUser(login, password);
        personalAccountPage = verificationPage.verificationUser(String.valueOf(getVerificationCode()));
    }

    @И("пользователь переводит {} рублей с карты с номером {} на свою {} карту с главной страницы")
    public void login(String sum, String number, int index){
        personalAccountPage = new PersonalAccountPage();
        replenishCardPage =  personalAccountPage.getReplenishCard(index);
        personalAccountPage = replenishCardPage.getMoneyTransfer(sum,number);
    }

    @И("баланс его {} карты из списка на главной странице должен стать {} рублей")
    public void login2(int index, String sum){
        String sumWithoutSpaces = sum.replaceAll("\\s+", "");
        int expectedSum = Integer.parseInt(sumWithoutSpaces);

        int balanceFirstCard = personalAccountPage.getBalanceCard(index);
        assertEquals(expectedSum, balanceFirstCard);
    }
}