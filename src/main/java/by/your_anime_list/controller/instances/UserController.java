package by.your_anime_list.controller.instances;

import by.your_anime_list.bean.AnimeReview;
import by.your_anime_list.bean.User;
import by.your_anime_list.controller.*;
import by.your_anime_list.controller.exception.ControllerException;
import by.your_anime_list.service.ReviewService;
import by.your_anime_list.service.UserService;
import by.your_anime_list.service.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    private UserService userService;
    private ReviewService reviewService;

    @GetMapping("/login")
    public String login() {
        return JspPage.LOGIN.name().toLowerCase();
    }

    @GetMapping("/register")
    public String register() {
        return JspPage.REGISTER.name().toLowerCase();
    }

    @PostMapping("/do_login")
    public String doLogin(HttpServletRequest request) throws ControllerException {
        String login = request.getParameter(
                RequestParameter.LOGIN.name().toLowerCase());
        String password = request.getParameter(
                RequestParameter.PASSWORD.name().toLowerCase());

        User user;
        try {
            user = userService.login(login, password);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        HttpSession httpSession = request.getSession();
        if (user != null) {
            httpSession.setAttribute(SessionAttribute.USER.getName(), user);
            return "redirect:/" + JspPage.HOME.name().toLowerCase();
        }

        return "redirect:/" + JspPage.LOGIN.name().toLowerCase();
    }

    @PostMapping("/do_register")
    public String doRegister(HttpServletRequest request) throws ControllerException {
        User user;
        String login = request.getParameter(
                RequestParameter.LOGIN.name().toLowerCase());
        String password = request.getParameter(
                RequestParameter.PASSWORD.name().toLowerCase());
        String confirmedPassword = request.getParameter(
                RequestParameter.CONFIRM_PASSWORD.name().toLowerCase());

        try {
            user = userService.register(login, password, confirmedPassword);
        } catch (ServiceException e) {
            logger.warn("Service register exception: {}", e.getMessage());
            throw new ControllerException(e);
        }

        if (user == null) {
            logger.info("Registration failed.");
            return "redirect:/" + JspPage.REGISTER.name().toLowerCase();
        }

        logger.info("Registration success.");
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(SessionAttribute.USER.getName(), user);
        return "redirect:/" + JspPage.HOME.name().toLowerCase();
    }

    @PostMapping("/ban")
    public String ban(HttpServletRequest request) throws ControllerException {
        String userIdStr = request.getParameter(RequestParameter.USER_ID.name().toLowerCase());
        int userId = Integer.parseInt(userIdStr);
        try {
            userService.ban(userId);
        } catch (ServiceException e) {
            logger.warn("Exception in user banning: {}", e.getMessage());
            throw new ControllerException(e);
        }
        logger.info("User successfully banned.");
        return "redirect:/" + JspPage.PROFILE.name().toLowerCase() + "?"
                + RequestParameter.USER_ID.name().toLowerCase() + "=" + userId;
    }

    @PostMapping("/unban")
    public String unban(HttpServletRequest request) throws ControllerException {
        String userIdStr = request.getParameter(RequestParameter
                .USER_ID.name().toLowerCase());
        int userId = Integer.parseInt(userIdStr);
        try {
            userService.unban(userId);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return "redirect:/" + JspPage.PROFILE.name().toLowerCase() + "?"
                + RequestParameter.USER_ID.name().toLowerCase() + "=" + userId;
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) throws ControllerException {
        User user = null;
        String userId = request.getParameter(
                RequestParameter.USER_ID.name().toLowerCase());

        int id;
        if ( userId != null ) {
            id = Integer.parseInt(userId);
        } else {
            HttpSession session = request.getSession();
            User savedUser = (User) session.getAttribute(SessionAttribute
                    .USER.getName().toLowerCase());
            id = savedUser.getId();
        }

        try {
            user = userService.getUser(id);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        if (user == null) {
            throw new ControllerException("Unknown user id!");
        }

        request.setAttribute(RequestAttribute
                .CURRENT_USER.name().toLowerCase(), user);

        return JspPage.PROFILE.name().toLowerCase();
    }

    @PostMapping("/change_lang")
    public String changeLanguage(HttpServletRequest request) throws ControllerException {
        String languageName = request.getParameter(RequestParameter
                .LANGUAGE.name().toLowerCase());
        String pageName = request.getParameter(RequestParameter
                .CURR_PAGE_NAME.name().toLowerCase());

        if (languageName == null || pageName == null) {
            logger.info("Uncorrected parameters in changing language");
            throw new ControllerException("Change language parameters are incorrect!");
        }

        Language language = Language.valueOf(languageName.toUpperCase());
        HttpSession httpSession = request.getSession();
        logger.info("Setting language = {}", languageName);
        httpSession.setAttribute(SessionAttribute.LANGUAGE.getName(), language);
        return "redirect:/" + pageName;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ControllerException {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(SessionAttribute.USER.getName(), null);
        return "redirect:/" + JspPage.HOME.name().toLowerCase();
    }

    @PostMapping("/next")
    public String nextPage(HttpServletRequest request) {
        int nextPageNum;
        String pageNumStr = request.getParameter(
                RequestParameter.PAGE_NUM.name().toLowerCase());

        if ( pageNumStr.equals("") ) {
            nextPageNum = 2;
        } else {
            nextPageNum = Integer.parseInt(pageNumStr) + 1;
        }

        return "redirect:/" + JspPage.HOME.name().toLowerCase()
                + "?" + RequestParameter.PAGE_NUM.name().toLowerCase() + "=" + nextPageNum;
    }

    @PostMapping("/prev")
    public String prevPage(HttpServletRequest request) {
        String pageNum = request.getParameter(
                RequestParameter.PAGE_NUM.name().toLowerCase());

        int prevPageNum = Integer.parseInt(pageNum) - 1;
        return "redirect:/" + JspPage.HOME.name().toLowerCase()
                + "?" + RequestParameter.PAGE_NUM.name().toLowerCase() + "=" + prevPageNum;
    }

    @PostMapping("/add_review")
    public String addReview(HttpServletRequest request) throws ControllerException {
        String rateString = request.getParameter(
                RequestParameter.REVIEW_RATE.name().toLowerCase());
        float rate = Float.parseFloat(rateString);

        String comment = request.getParameter(
                RequestParameter.REVIEW_COMMENT.name().toLowerCase());

        String animeIdString = request.getParameter(
                RequestParameter.ANIME_ID.name().toLowerCase());
        int animeId = Integer.parseInt(animeIdString);

        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute(RequestParameter
                .USER.name().toLowerCase());

        try {
            user = userService.getUser(user.getId());
            if ( user == null ) {
                throw new ControllerException("No such user in db.");
            }

            if ( user.isBanned() ) {
                throw new ControllerException("Banned user can't add a review");
            }

            httpSession.setAttribute(SessionAttribute.USER.getName(),
                    user);

            AnimeReview newAnimeReview = new AnimeReview(
                    user.getId(),
                    user.getLogin(),
                    animeId,
                    rate,
                    comment
            );
            reviewService.addReview(newAnimeReview);
        } catch (ServiceException e) {
            logger.warn("Exception in reviewService.addReview: {}", e.getMessage());
            throw new ControllerException(e);
        }

        logger.info("Success in adding review.");
        return "redirect:/" + JspPage.ANIME.name().toLowerCase() + "?"
                + RequestParameter.ID.name().toLowerCase() + "=" + animeIdString;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
}
