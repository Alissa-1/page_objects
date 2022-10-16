package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    // TransferPage - страница перевода с данными о картах пользователя (Пополнить/Пополнить)
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement amountInput = $("[data-test-id=amount] input");
    private SelenideElement fromInput = $("[data-test-id=from] input");

    private SelenideElement transferHead = $(byText("Пополнение карты"));
    private SelenideElement errorMessage = $("[data-test-id=error-message]");

    public DashboardPage shouldMakeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        shouldMakeAnyTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    } // метод только для трансферов, где сумма перевода >0 и меньше баланса исх карты
    // amountToTransfer получается при запуске соотв метода из DataHelper

    public void shouldMakeAnyTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(amountToTransfer); // вставляем значения сумма/куда/пополнить
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    } // метод для любых трансферов

    public void shouldFindErrorMessage(String expectedText) {
        errorMessage.shouldBe(Condition.exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}