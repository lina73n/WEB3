package by.your_anime_list.service.exception;

/**
 * The ServiceException class represents an exception specific to the service layer of the application.
 * It extends the Exception class.
 */
public class ServiceException extends Exception {
    /**
     * Constructs a new ServiceException with the specified error message.
     *
     * @param message the error message
     */
    public ServiceException(String message) {
        super(message);
    }
}
