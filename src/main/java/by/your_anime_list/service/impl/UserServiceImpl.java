package by.your_anime_list.service.impl;

import by.your_anime_list.bean.User;
import by.your_anime_list.dao.UserDAO;
import by.your_anime_list.dao.exception.DAOException;
import by.your_anime_list.service.UserService;
import by.your_anime_list.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserServiceImpl is an implementation of the UserService interface.
 * It provides methods for user login, registration, retrieving user information, banning and unbanning users.
 * This class interacts with the UserDAO to perform database operations related to users.
 */
@Component
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private CredentialsValidator credentialsValidator;
    private UserDAO userDAO;

    /**
     * Authenticates a user by checking the login and password.
     *
     * @param login    the user's login
     * @param password the user's password
     * @return the authenticated user object, or null if the login or password is invalid
     * @throws ServiceException if an error occurs while performing the login operation
     */
    @Override
    public User login(String login, String password) throws ServiceException {
        if ( !credentialsValidator.validLogin(login) ) {
            logger.log(Level.WARN, "User login isn't valid.");
            return null;
        }

        if ( !credentialsValidator.validPassword(password) ) {
            logger.log(Level.WARN, "User password isn't valid.");
            return null;
        }

        User user;

        try {
            user = userDAO.signIn(login, password);
        } catch ( DAOException e ) {
            logger.log(Level.INFO, "Error in DAO sign in method");
            throw new ServiceException(e.getMessage());
        }
        return user;
    }

    /**
     * Registers a new user with the provided login, password, and confirmation password.
     *
     * @param login                the user's login
     * @param password             the user's password
     * @param confirmationPassword the confirmation password
     * @return the registered user object, or null if the login or password is invalid
     * @throws ServiceException if an error occurs while performing the registration operation
     */
    @Override
    public User register(String login, String password, String confirmationPassword) throws ServiceException {
        if ( !credentialsValidator.validLogin(login) ) {
            logger.log(Level.WARN, "User login isn't valid. {}", login);
            return null;
        }

        if ( !credentialsValidator.validPassword(password) || !password.equals(confirmationPassword)) {
            logger.log(Level.WARN, "User password isn't valid. {} : {}", password, confirmationPassword);
            return null;
        }

        User user;

        try {
            user = userDAO.register(login, password);
        } catch ( DAOException e ) {
            logger.log(Level.TRACE, "Error in DAO register method.");
            throw new ServiceException(e.getMessage());
        }
        return user;
    }

    /**
     * Retrieves the user with the specified user ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the user object with the specified ID
     * @throws ServiceException if an error occurs while retrieving the user
     */
    @Override
    public User getUser(int userId) throws ServiceException {
        User user;

        try {
            user = userDAO.getUser(userId);
        } catch ( DAOException e ) {
            logger.log(Level.TRACE, "Error in DAO getUser method.");
            throw new ServiceException(e.getMessage());
        }
        return user;
    }

    /**
     * Bans a user with the specified user ID.
     *
     * @param userId the ID of the user to ban
     * @throws ServiceException if an error occurs while banning the user
     */
    @Override
    public void ban(int userId) throws ServiceException {
        try {
            userDAO.ban(userId);
        } catch ( DAOException e ) {
            logger.log(Level.TRACE, "Error in DAO ban method.");
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Unbans a user with the specified user ID.
     *
     * @param userId the ID of the user to unban
     * @throws ServiceException if an error occurs while unbanning the user
     */
    @Override
    public void unban(int userId) throws ServiceException {
        try {
            userDAO.unban(userId);
        } catch ( DAOException e ) {
            logger.log(Level.TRACE, "Error in DAO unban method.");
            throw new ServiceException(e.getMessage());
        }
    }

    @Autowired
    public void setCredentialsValidator(CredentialsValidator credentialsValidator) {
        this.credentialsValidator = credentialsValidator;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
