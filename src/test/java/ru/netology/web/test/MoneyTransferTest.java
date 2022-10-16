package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV3;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenCards() {
        var loginPage = open("http://localhost:9999/", LoginPageV3.class);
        var authInfo = DataHelper.getAuthInfo(); // получили логин и пароль, положили в authInfo
        var verificationPage = loginPage.validLogin(authInfo); // заполнили логин пароль, нажали кнопку
        var verificationCode = DataHelper.getVerificationCode(); // получили код из смс
        var dashboardPage = verificationPage.validVerify(verificationCode); // вставили в поле код из смс
        var firstCardInfo = getFirstCardInfo(); // получили номер и testId первой карты
        var secondCardInfo = getSecondCardInfo();
        var currentFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo); // узнали баланс (число) первой карты
        var currentSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(currentFirstCardBalance); // определили валидную сумму для перевода
        var expectedFirstCardBalance = currentFirstCardBalance - amount;
        var expectedSecondCardBalance = currentSecondCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo); // выбрали для карту перевода
        // на странице трансфера заполнили поля Сумма, Откуда, Куда и Перевели (ниже):
        dashboardPage = transferPage.shouldMakeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void shouldNotTransferMoneyIfAmountOfTransferMoreThanBalance() {
        var loginPage = open("http://localhost:9999/", LoginPageV3.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var currentFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var currentSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateInvalidAmount(currentSecondCardBalance); //задаем сумму перевода выше, чем баланс карты
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        transferPage.shouldMakeAnyTransfer(String.valueOf(amount), secondCardInfo);
        transferPage.shouldFindErrorMessage("Баланс на карте не достаточный");
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(currentFirstCardBalance, actualFirstCardBalance);
        assertEquals(currentSecondCardBalance, actualBalanceSecondCard);
    }
}