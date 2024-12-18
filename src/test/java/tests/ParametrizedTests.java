package tests;

import data.Language;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ParametrizedTests extends TestBase {

    @EnumSource(Language.class)
    @Tag("SMOKE")
    @ParameterizedTest(name = "Проверка заголовка на соответствующем языке {0}")
    void checkTitleOnAppropriateLanguageTest(Language language) {
        open("/");
        $(".language-dropdown").click();
        $(".language-dropdown-item").$(byText(language.name())).click();
        $(".search-form-title").shouldHave(text(language.description));
    }

    static Stream<Arguments> patternSiteShouldBeCorrectButtonsTest() {
        return Stream.of(
                Arguments.of(Language.RU,
                        List.of("Информация", "Подключиться", "Специальные предложения", "Автобусы")
                ),
                Arguments.of(Language.EN,
                        List.of("Information", "Login", "Special offers", "Buses")
                ),
                Arguments.of(Language.DE,
                        List.of("Informationen", "Anmelden", "Sonderangebote", "Busse")
                )
        );
    }

    @MethodSource
    @Tag("WEB")
    @ParameterizedTest(name = "Проверка наличия кнопок {1} на соответствующем языке {0}")
    void patternSiteShouldBeCorrectButtonsTest(Language language, List<String> expectedButtons) {
        open("/");
        $(".language-dropdown").click();
        $(".language-dropdown-item").$(byText(language.name())).click();
        $$("div.header-menus").shouldHave(texts(expectedButtons));

    }

    @CsvFileSource(resources = "/test_data/checkTitleOnOtherLanguagesWithFile.csv")
    @Tag("SMOKE")
    @ParameterizedTest(name = "Проверка заголовка {1} на соответствующем языке {0}")
    void checkTitleOnOtherLanguagesWithCSVFileTest(String language, String title) {
        open("/");
        $(".language-dropdown").click();
        $(".language-dropdown-item").$(byText(language)).shouldBe(visible).click();
        $(".search-form-title").shouldHave(text(title));
    }
}