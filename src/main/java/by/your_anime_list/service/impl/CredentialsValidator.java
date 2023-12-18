package by.your_anime_list.service.impl;

import org.glassfish.api.admin.CommandAspect;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The CredentialsValidator class provides methods to validate login and password credentials.
 */
@Component
class CredentialsValidator {
    /**
     * Validates a login string.
     *
     * @param login the login string to validate
     * @return true if the login is valid, false otherwise
     */
    public boolean validLogin(String login) {
        if (login == null) {
            return false;
        }

        String loginRegex = "[a-zA-Z][a-zA-Z0-9]{5,25}";
        Pattern pattern = Pattern.compile(loginRegex);
        Matcher matcher = pattern.matcher(login);

        return matcher.matches();
    }

    /**
     * Validates a password string.
     *
     * @param password the password string to validate
     * @return true if the password is valid, false otherwise
     */
    public boolean validPassword(String password) {
        if (password == null) {
            return false;
        }

        String passwordRegex = "[a-zA-Z0-9]{6,26}";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
