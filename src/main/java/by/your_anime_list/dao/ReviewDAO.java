package by.your_anime_list.dao;

import by.your_anime_list.bean.AnimeReview;
import by.your_anime_list.dao.exception.DAOException;

/**
 * This is the ReviewDAO interface, which provides methods for adding and retrieving anime reviews.
 */
public interface ReviewDAO {
    /**
     * Adds a new anime review.
     *
     * @param animeReview The AnimeReview object representing the review to be added.
     * @return The AnimeReview object that was added.
     * @throws DAOException If an error occurs while adding the review.
     */
    AnimeReview addReview(AnimeReview animeReview) throws DAOException;

    /**
     * Retrieves an anime review by user ID and anime ID.
     *
     * @param userId The ID of the user who wrote the review.
     * @param animeId The ID of the anime for which the review was written.
     * @return The AnimeReview object representing the retrieved review.
     * @throws DAOException If an error occurs while retrieving the review.
     */
    AnimeReview getReview(int userId, int animeId) throws DAOException;
}
