package data;

public enum Language {
    RU("Твоя зона комфорта в путешествиях по европе"),
    EN("Your comfort zone across Europe"),
    DE ("Deine komfortzone in Europareisen");


    public final String description;

    Language(String description) {
        this.description = description;
    }
}