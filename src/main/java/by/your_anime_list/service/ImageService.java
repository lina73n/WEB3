package by.your_anime_list.service;

import by.your_anime_list.service.exception.ServiceException;
import jakarta.servlet.http.Part;

/**
 * This is the interface for the ImageService, which provides methods for uploading images.
 */
public interface ImageService {
    /**
     * Uploads an image to the specified directory with the given file name.
     *
     * @param part           the Part object representing the image file
     * @param directoryName  the name of the directory to upload the image to
     * @param fileName       the desired file name for the uploaded image
     * @return the URL of the uploaded image
     * @throws ServiceException if an error occurs during the image upload process
     */
    String uploadImage(Part part, String directoryName, String fileName) throws ServiceException;
}
