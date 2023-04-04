import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ServiceTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen=true;
        open("http://localhost:9999");
    }

    @Test
    void shouldTestValidValues(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Мария");
        form.$("[data-test-id=phone] input").setValue("+79990000000");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("button").click();
        $("[data-test-id=order-success]")
                .shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestOtherValidValues(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Анна-Мария");
        form.$("[data-test-id=phone] input").setValue("+23587447777");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("button").click();
        $("[data-test-id=order-success]")
                .shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestValidValuesWithLongName(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Иванов Вячеслав Петрович");
        form.$("[data-test-id=phone] input").setValue("+79278220000");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("button").click();
        $("[data-test-id=order-success]").
                shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestWithInvalidName(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("dfg");
        form.$("button").click();
        $("[data-test-id=name] .input__sub").
                shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestWithoutName(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("button").click();
        $("[data-test-id=name] .input__sub").
                shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWithoutPhone(){
        SelenideElement form = $("form");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("button").click();
        $("[data-test-id=name] .input__sub").
                shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWithInvalidPhone(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Анна-Мария");
        form.$("[data-test-id=phone] input").setValue("888888888");
        form.$("button").click();
        $("[data-test-id=phone] .input__sub").
                shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestWithoutAgreement(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Анна-Мария");
        form.$("[data-test-id=phone] input").setValue("+79012345678");
        form.$("button").click();
        $("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));
    }
}
