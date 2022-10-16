package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    // VerificationPage - страница, где просят ввести код подтверждения и смс, открывается после авторизации
    private SelenideElement codeField = $("[data-test-id=code] input"); // поле ввода кода из смс
    private SelenideElement verifyButton = $("[data-test-id=action-verify]"); // кнопка

    public VerificationPage() {
        codeField.shouldBe(visible); // конструктор и проверка
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    } // ввели код и нажали на кнопку
}