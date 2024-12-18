package data;

public enum Language {
    RU("Посольство Германии в Москве", "ru-de"),
    DE ("Deutsche Botschaft Moskau", "ru-ru");

    public final String description;
    public final String urlSuffix;

    Language(String description, String urlSuffix) {
        this.description = description;
        this.urlSuffix = urlSuffix;
    }
}