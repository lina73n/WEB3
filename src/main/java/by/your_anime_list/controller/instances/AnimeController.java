package by.your_anime_list.controller.instances;

import by.your_anime_list.bean.Anime;
import by.your_anime_list.bean.AnimeReview;
import by.your_anime_list.bean.User;
import by.your_anime_list.controller.JspPage;
import by.your_anime_list.controller.RequestAttribute;
import by.your_anime_list.controller.RequestParameter;
import by.your_anime_list.controller.SessionAttribute;
import by.your_anime_list.controller.exception.ControllerException;
import by.your_anime_list.service.AnimeService;
import by.your_anime_list.service.ImageService;
import by.your_anime_list.service.ReviewService;
import by.your_anime_list.service.UserService;
import by.your_anime_list.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;
import java.util.List;

@Controller
public class AnimeController {
    private AnimeService animeService;
    private ReviewService reviewService;
    private UserService userService;
    private ImageService imageService;

    private static final Logger logger = LogManager.getLogger(AnimeController.class);

    @GetMapping("/anime")
    public String getAnime(HttpServletRequest request) throws ControllerException {
        Anime anime;
        User user;
        AnimeReview userReview = null;
        List<AnimeReview> animeReviews;

        String animeIdParam = request.getParameter(
                RequestParameter.ID.name().toLowerCase());
        int animeId = Integer.parseInt(animeIdParam);

        try {
            anime = animeService.getAnime(animeId);
            animeReviews = reviewService.getAnimeReviews(animeId);

            HttpSession httpSession = request.getSession();
            user = (User) httpSession.getAttribute(SessionAttribute.USER.getName());
            if ( user != null ) {
                user = userService.getUser(user.getId());
                if ( user == null ) {
                    throw new ControllerException("There is no such user in db.");
                }
                httpSession.setAttribute(SessionAttribute.USER.getName(), user);
                userReview = reviewService.getReview(user.getId(), animeId);
            }
        } catch (ServiceException e) {
            logger.warn("Error in showing anime: {}", e.getMessage());
            throw new ControllerException(e.getMessage());
        }

        request.setAttribute(RequestAttribute.USER_REVIEW.name().toLowerCase(),
                userReview);
        request.setAttribute(RequestAttribute.ANIME_REVIEWS.name().toLowerCase(),
                animeReviews);
        request.setAttribute(RequestAttribute.USER.name().toLowerCase(), user);
        request.setAttribute(RequestAttribute.ANIME.name().toLowerCase(), anime);

        return JspPage.ANIME.name().toLowerCase();
    }

    @GetMapping("add_anime")
    public String addAnime() {
        return "add_anime";
    }

    @PostMapping(value = "do_add_anime")
    public String doAddAnime(HttpServletRequest request) throws ControllerException {
        String animeName = request.getParameter(
                RequestParameter.ANIME_NAME.name().toLowerCase());
        String authorName = request.getParameter(
                RequestParameter.AUTHOR_NAME.name().toLowerCase());
        String animeDescription = request.getParameter(
                RequestParameter.ANIME_DESCRIPTION.name().toLowerCase());

        String animeYearString = request.getParameter(
                RequestParameter.ANIME_YEAR.name().toLowerCase());

        int animeYear = Integer.parseInt(animeYearString);

        animeName = animeName.trim();
        authorName = authorName.trim();
        animeDescription = animeDescription.trim();

        String imageName;

        try {
            String directoryName = request
                    .getServletContext()
                    .getRealPath("/images/");

            Part part = request.getPart(RequestParameter.ANIME_IMAGE.name().toLowerCase());
            imageName = imageService.uploadImage(part, directoryName, animeName);
        } catch (ServiceException | ServletException | IOException e ) {
            throw new ControllerException(e.getMessage());
        }

        Anime newAnime = new Anime(
                animeName,
                authorName,
                Anime.RATING_STUB,
                imageName,
                animeYear,
                animeDescription
        );

        boolean result;
        try {
            result = animeService.addAnime(newAnime);
        } catch ( ServiceException e ) {
            logger.warn("Exception in adding anime: {}", e.getMessage());
            throw new ControllerException(e);
        }

        logger.info("Anime adding result = {}", result);
        return "redirect:/" + JspPage.ADD_ANIME.name().toLowerCase();
    }

    @Autowired
    public void setAnimeService(AnimeService animeService) {
        this.animeService = animeService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }
}
