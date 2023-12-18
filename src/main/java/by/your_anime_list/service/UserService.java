package by.your_anime_list.service;

import by.your_anime_list.bean.User;
import by.your_anime_list.service.exception.ServiceException;

/**
 * Interface that contains service method for User class
 */
public interface UserService {
    /**
     * Logs in a user with the provided login and password.
     *
     * @param login    the user's login
     * @param password the user's password
     * @return the logged-in user
     * @throws ServiceException if an error occurs during the login process
     */
    User login(String login, String password) throws ServiceException;

    /**
     * Registers a new user with the provided login, password, and confirmation password.
     *
     * @param login                the user's login
     * @param password             the user's password
     * @param confirmationPassword the confirmation password
     * @return the registered user
     * @throws ServiceException if an error occurs during the registration process
     */
    User register(String login, String password, String confirmationPassword) throws ServiceException;

    /**
     * Retrieves a user with the specified user ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the retrieved user
     * @throws ServiceException if an error occurs while retrieving the user
     */
    User getUser(int userId) throws ServiceException;

    /**
     * Bans a user with the specified user ID.
     *
     * @param userId the ID of the user to ban
     * @throws ServiceException if an error occurs while banning the user
     */
    void ban(int userId) throws ServiceException;

    /**
     * Unbans a user with the specified user ID.
     *
     * @param userId the ID of the user to unban
     * @throws ServiceException if an error occurs while unbanning the user
     */
    void unban(int userId) throws ServiceException;
}
