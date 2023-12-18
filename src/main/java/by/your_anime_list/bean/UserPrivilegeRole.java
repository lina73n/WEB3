package by.your_anime_list.bean;

/**
 * The UserPrivilegeRole enum represents the privilege roles that a user can have.
 */
public enum UserPrivilegeRole {
    VISITOR("Visitor"),
    USER("User"),
    ADMINISTRATOR("Administrator");

    private final String name;

    /**
     * Constructs a UserPrivilegeRole enum constant with the specified name.
     *
     * @param name the name of the privilege role
     */
    UserPrivilegeRole(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the privilege role.
     *
     * @return the name of the privilege role
     */
    public String getName() {
        return name;
    }
}
