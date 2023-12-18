package by.your_anime_list.bean;

import jakarta.persistence.*;

/**
 * The User class represents a user in the system.
 */
@Entity
@Table(name = "user")
public class User {
    /**
     * The ID of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * The login of the user.
     */
    private String login;

    @Column(name = "password_hash")
    private String passwordHash;

    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * The privilege role of the user.
     */
    @Column(name = "role_id")
    private UserPrivilegeRole userPrivilegeRole;
    /**
     * The status value of the user.
     */
    @Column(name = "status_value")
    private float statusValue;
    /**
     * Indicates whether the user is banned or not.
     */
    @Column(name = "ban")
    private boolean isBanned;

    /**
     * Constructs a User object with the specified properties.
     *
     * @param login             the login of the user
     * @param userPrivilegeRole the privilege role of the user
     * @param statusValue       the status value of the user
     * @param isBanned          true if the user is banned, false otherwise
     */
    public User(String login, String passwordHash, UserPrivilegeRole userPrivilegeRole, float statusValue, boolean isBanned) {
        this.login = login;
        this.userPrivilegeRole = userPrivilegeRole;
        this.statusValue = statusValue;
        this.isBanned = isBanned;
        this.passwordHash = passwordHash;
    }

    public User() {
    }

    /**
     * Returns the privilege role of the user.
     *
     * @return the privilege role
     */
    public UserPrivilegeRole getUserPrivilegeRole() {
        return userPrivilegeRole;
    }

    /**
     * Returns the ID of the user.
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the status value of the user.
     *
     * @return the status value
     */
    public float getStatusValue() {
        return statusValue;
    }

    /**
     * Returns the login of the user.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    /**
     * Checks if the user is banned.
     *
     * @return true if the user is banned, false otherwise
     */
    public boolean isBanned() {
        return isBanned;
    }
}
