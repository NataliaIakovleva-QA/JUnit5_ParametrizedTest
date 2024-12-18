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
        open("/" + language.urlSuffix + "/ueber-uns/botschaft");
        $("a.is-abbr-language")
                .shouldHave(text(language.name()))
                .click();
        $("h1.heading__title").shouldHave(text(language.description));
    }

    static Stream<Arguments> patternSiteShouldBeCorrectButtonsTest() {
        return Stream.of(
                Arguments.of(Language.RU,
                        List.of("О нас", "Визовые и консульско-правовые вопросы", "Германия и Россия", "Открыть поиск")
                ),

                Arguments.of(Language.DE,
                        List.of("Über uns", "Service", "Deutschland und Russland", "Suche öffnen")
                )
        );
    }

    @MethodSource
    @Tag("WEB")
    @ParameterizedTest(name = "Проверка наличия кнопок {1} на соответствующем языке {0}")
    void patternSiteShouldBeCorrectButtonsTest(Language language, List<String> expectedButtons) {
        open("/" + language.urlSuffix + "/ueber-uns/botschaft");
        $("a.is-abbr-language")
                .shouldHave(text(language.name()))
                .click();
        $$(".nav-primary__list-item").shouldHave(texts(expectedButtons));

    }

    @CsvFileSource(resources = "/test_data/checkTitleOnOtherLanguagesWithFile.csv")
    @Tag("SMOKE")
    @ParameterizedTest(name = "Проверка заголовка {1} на соответствующем языке {0}")
    void checkTitleOnOtherLanguagesWithCSVFileTest(String language, String title) {
        Language lang = Language.valueOf(language);
        open("/" + lang.urlSuffix + "/ueber-uns/botschaft");
        $("a.is-abbr-language")
                .shouldHave(text(language))
                .click();
        $("h1.heading__title").shouldHave(text(title));
    }
}