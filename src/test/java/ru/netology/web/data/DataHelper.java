package ru.netology.web.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    } // обозначили значения для элементов AuthInfo (сейчас две составляющих)
    // они могут быть любыми полученными, в нашей задаче хардкод (ниже)
    // при вызове getAuthInfo() будут даны не любые значения, а только заданные. Они соответствуют описанию AuthInfo

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;

    } // логика задания и getter аналогично AuthInfo

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String testId;

    } // обозначили значения для элементов CardInfo (сейчас две составляющих)
    // нам известно про две карты - исп два разных getter для заданных значений

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559000000000001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559000000000002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    // сумма перевода (amount) не должна превышать баланс по карте (при задании метода указывам баланс)
    public static int generateValidAmount(int balance) {
        return new Random().nextInt(balance) + 1;
    }
    // Math.abs - значение по модулю
    public static int generateInvalidAmount(int balance) {
        return Math.abs(balance) + new Random().nextInt(10000);
    }
}