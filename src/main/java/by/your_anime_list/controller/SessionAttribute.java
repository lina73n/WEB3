package by.your_anime_list.controller;

/*
 * SessionAttribute enum.
 * Defines session attribute names.
 */
public enum SessionAttribute {
    LANGUAGE("lang"),
    USER("user")
    ;

    private final String name;

    /**
     * Constructs a SessionAttribute enum constant with the specified name.
     *
     * @param name the name of the session attribute
     */
    SessionAttribute(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the session attribute.
     *
     * @return the name of the session attribute
     */
    public String getName() {
        return name;
    }
}
