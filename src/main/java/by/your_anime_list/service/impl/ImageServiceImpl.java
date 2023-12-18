package by.your_anime_list.service.impl;

import by.your_anime_list.service.ImageService;
import by.your_anime_list.service.exception.ServiceException;
import com.google.common.io.Files;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This Class contains the implementation of the ImageService interface.
 * It provides methods for uploading images.
 */
@Component
public class ImageServiceImpl implements ImageService {
    /**
     * The logger object used for logging messages.
     */
    private static final Logger logger = LogManager.getLogger(ImageServiceImpl.class);
    /**
     * The number of bytes to read at a time during the image upload process.
     */
    private static final int N_READ_BYTES = 1024;

    /**
     * Gets the file name from the Part object's header.
     *
     * @param part the Part object
     * @return the file name
     */
    private String getUploadFileName(Part part) {
        String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Uploads an image file to a specified directory.
     *
     * @param part the Part object
     * @param directoryName the directory name
     * @param fileName the file name
     * @return the name of the uploaded file with its extension
     * @throws ServiceException if an error occurs during the upload
     */
    @Override
    public String uploadImage(Part part, String directoryName, String fileName) throws ServiceException {
        String uploadFileName = getUploadFileName(part);
        if (uploadFileName == null) {
            logger.log(Level.INFO, "Error in uploading image: no file name.");
            throw new ServiceException("Error in getting upload file name!");
        }

        String fileExtension = Files.getFileExtension(uploadFileName);
        String fileNameWithExtension = (fileName.replace(" ", "_")
                + "." + fileExtension).toLowerCase();
        Path filePath = Paths.get(directoryName, fileNameWithExtension);
        try ( OutputStream out = new FileOutputStream(filePath.toFile());
             InputStream fileContent = part.getInputStream() ) {
            byte[] bytes = new byte[N_READ_BYTES];
            int read = fileContent.read(bytes);
            while (read != -1) {
                out.write(bytes, 0, read);
                read = fileContent.read(bytes);
            }
        } catch (IOException e) {
            logger.log(Level.WARN, "Error during uploading image.");
            throw new ServiceException(e.getMessage());
        }

        return fileNameWithExtension;
    }
}
