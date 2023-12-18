package by.your_anime_list.controller.instances;

import by.your_anime_list.bean.Anime;
import by.your_anime_list.controller.RequestAttribute;
import by.your_anime_list.controller.RequestParameter;
import by.your_anime_list.service.AnimeService;
import by.your_anime_list.service.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeController {
    private AnimeService animeService;

    @GetMapping("/home")
    public String home(HttpServletRequest request) throws ServiceException {
        String pageNumberString = request.getParameter(RequestParameter.PAGE_NUM.name().toLowerCase());
        int pageNumber = 1;
        if (pageNumberString != null) {
            pageNumber = Integer.parseInt(pageNumberString);
        }

        List<Anime> animeList = animeService.getAnimeList(pageNumber);
        int maxPageNum = animeService.getMaxPageNum();

        request.setAttribute(RequestAttribute.ANIME_LIST.name().toLowerCase(), animeList);
        request.setAttribute(RequestAttribute.MAX_PAGE_NUM.name().toLowerCase(), maxPageNum);
        request.setAttribute(RequestAttribute.PAGE_NUM.name().toLowerCase(), pageNumber);

        return "home";
    }

    @Autowired
    public void setAnimeService(AnimeService animeService) {
        this.animeService = animeService;
    }
}
