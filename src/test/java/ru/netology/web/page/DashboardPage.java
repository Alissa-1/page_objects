package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    // дашборд - страница, где видны карты пользователя, кот открывается после авторизации
    private SelenideElement heading = $("[data-test-id=dashboard]"); // фиксируем состояние страницы
    private ElementsCollection cards = $$(".list__item div"); // в DvTools в ".list__item div" хранятся данные
    // о всех картах, отображаемых на странице дашборда
    private final String balanceStart = "баланс: "; // данные для extract
    private final String balanceFinish = " р."; // данные для extract

    public DashboardPage() {
        heading.shouldBe(visible);
    } // конструктор с необязательной проверкой

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        var text = cards.findBy(Condition.text(cardInfo.getCardNumber().substring(12, 16))).getText();
        return shouldExtractBalance(text);
    } // из текстовой информации в ".list__item div" вырезаем символы. относящиеся к балансу:
    // с помощью getCardBalance(..) мы получили вырезанный текст вида **** **** **** 0001, баланс: 10000 р.\nПополнить
    // substring - вырезает подстроку из строки (последние 4 цифры карты)
    // cardInfo.getCardNumber() - см DataHelper

    private int shouldExtractBalance(String text) { // text получиили методом getCardBalance
        var start = text.indexOf(balanceStart); // поля - точка начала для вырезания текста (int!)
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish); // внутри text идём по № символов
        return Integer.parseInt(value); // преобразуем подстроку в целочисленное значение
    }
    // indexOf - возвращает номер позиции, с которой начинается подстрока в строке

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        cards.findBy(attribute("data-test-id", cardInfo.getTestId())).$("button").click();
        return new TransferPage();
    }
    // на странице трансфера мы видим две карты и хотим выбрать, какую из них Пополнить (кнопка Пополнить)
    // сообщаем методу данные карты, куда хотим сделать перевод и жмем кнопку
}