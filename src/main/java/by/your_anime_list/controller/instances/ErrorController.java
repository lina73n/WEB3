package by.your_anime_list.controller.instances;

import by.your_anime_list.controller.JspPage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class ErrorController {

    @GetMapping(value = "/error")
    @ExceptionHandler(Exception.class)
    public String error() {
        return JspPage.ERROR.name().toLowerCase();
    }
}
