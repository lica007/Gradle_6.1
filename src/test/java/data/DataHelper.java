package data;

import lombok.Value;

public class DataHelper {

    private DataHelper(){
    }

    public static AuthUser getAuthUser() {
        return new AuthUser("vasya","qwerty123");
    }

    public static VerificationCode getverificationCode() {
        return new VerificationCode("12345");
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    @Value
    public static class AuthUser {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String verificationCode;
    }

    @Value
    public static class CardInfo {
        String number;
        String cardId;
    }
}