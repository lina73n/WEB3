package by.your_anime_list.controller;

/**
 * This class represents the language of the application.
 */
public enum Language {
    ENGLISH("en"),
    RUSSIAN("ru")
    ;

    /**
     * Short name of the language.
     */
    private final String name;

    /**
     * Constructs a Language object with the specified name.
     *
     * @param name the short name of the language
     */
    Language(String name) {
        this.name = name;
    }

    /**
     * Returns the short name of the language.
     *
     * @return the short name of the language
     */
    public String getName() {
        return name;
    }
}
