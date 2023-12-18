package by.your_anime_list.service;

import by.your_anime_list.bean.AnimeReview;
import by.your_anime_list.service.exception.ServiceException;
import java.util.List;

/**
 * Interface that contains service methods for AnimeReview class.
 */
public interface ReviewService {
    /**
     * @param userId user id of the review.
     * @param animeId anime id of the review.
     * @return the review if such exists.
     * @throws ServiceException if some exception occur on DAO layer.
     */
    AnimeReview getReview(int userId, int animeId) throws ServiceException;

    /**
     * @param animeReview anime review for adding.
     * @return AnimeReview if adding successful, null otherwise.
     * @throws ServiceException if some exception occur on DAO layer.
     */
    AnimeReview addReview(AnimeReview animeReview) throws ServiceException;

    /**
     * @param animeId id of anime to get its reviews.
     * @return reviews of the chosen anime.
     * @throws ServiceException when something was wrong.
     */
    List<AnimeReview> getAnimeReviews(int animeId) throws ServiceException;
}
