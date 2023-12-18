package by.your_anime_list.dao;

import by.your_anime_list.bean.User;
import by.your_anime_list.dao.exception.DAOException;

/**
 * UserDAO interface defines methods for accessing and manipulating user data in the database.
 */
public interface UserDAO {
    /**
     * Retrieves a user from the database based on the provided login and password.
     *
     * @param login    the user's login
     * @param password the user's password
     * @return the User object representing the authenticated user
     * @throws DAOException if an error occurs while accessing the database
     */
    User signIn(String login, String password) throws DAOException;

    /**
     * Registers a new user with the provided login and password.
     *
     * @param login    the user's login
     * @param password the user's password
     * @return the User object representing the registered user
     * @throws DAOException if an error occurs while accessing the database
     */
    User register(String login, String password) throws DAOException;

    /**
     * Retrieves a user from the database based on the provided user ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the User object representing the requested user
     * @throws DAOException if an error occurs while accessing the database
     */
    User getUser(int userId) throws DAOException;

    /**
     * Bans a user with the provided user ID.
     *
     * @param userId the ID of the user to ban
     * @throws DAOException if an error occurs while accessing the database
     */
    void ban(int userId) throws DAOException;

    /**
     * Unbans a user with the provided user ID.
     *
     * @param userId the ID of the user to unban
     * @throws DAOException if an error occurs while accessing the database
     */
    void unban(int userId) throws DAOException;
}
