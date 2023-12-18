package by.your_anime_list.controller;

import by.your_anime_list.bean.User;
import by.your_anime_list.bean.UserPrivilegeRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@WebFilter( urlPatterns = {"/*"})
public class ControllerFilter implements Filter {
    private static final HashMap<UserPrivilegeRole, Set<String>> possibleCommands = new HashMap<>();
    private static final Set<String> staticContentExtensions = Set.of(
            ".jpeg", ".jpg", ".css", ".js", ".png", ".webp");

    static {
        Set<String> visitorPossibleCommandNames = new HashSet<>() {{
            //add(CommandName.ANIME_LIST.name());
            //add(CommandName.ANIME.name());
            //add(CommandName.REGISTER.name());
            //add(CommandName.DO_REGISTER.name());
            //add(CommandName.LOGIN.name());
            //add(CommandName.DO_LOGIN.name());
            //add(CommandName.PROFILE.name());
            //add(CommandName.CHANGE_LANGUAGE.name());
            //add(CommandName.PREV_ANIME.name());
            //add(CommandName.NEXT_ANIME.name());
        }};

        Set<String> userPossibleCommandNames = new HashSet<>() {{
            //add(CommandName.ANIME_LIST.name());
            //add(CommandName.ANIME.name());
            //add(CommandName.LOGOUT.name());
            //add(CommandName.ADD_REVIEW.name());
            //add(CommandName.PROFILE.name());
            //add(CommandName.CHANGE_LANGUAGE.name());
            //add(CommandName.PREV_ANIME.name());
            //add(CommandName.NEXT_ANIME.name());
        }};

        Set<String> administratorPossibleCommandNames = new HashSet<>() {{
            //add(CommandName.ANIME_LIST.name());
            //add(CommandName.ANIME.name());
            //add(CommandName.LOGOUT.name());
            //add(CommandName.ADD_ANIME.name());
            //add(CommandName.DO_ADD_ANIME.name());
            //add(CommandName.ADD_REVIEW.name());
            //add(CommandName.PROFILE.name());
            //add(CommandName.CHANGE_LANGUAGE.name());
            //add(CommandName.BAN.name());
            //add(CommandName.UNBAN.name());
            //add(CommandName.PREV_ANIME.name());
            //add(CommandName.NEXT_ANIME.name());
        }};

        possibleCommands.put(UserPrivilegeRole.VISITOR, visitorPossibleCommandNames);
        possibleCommands.put(UserPrivilegeRole.USER, userPossibleCommandNames);
        possibleCommands.put(UserPrivilegeRole.ADMINISTRATOR, administratorPossibleCommandNames);

    }

    private void setCurrPageName(HttpServletRequest req) {
        String reqParameters = req.getQueryString();
        if (reqParameters == null) {
            reqParameters = "";
        } else {
            reqParameters = "?" + reqParameters;
        }

        String requestUrl = req.getRequestURL().toString() + reqParameters;
        String currPageName = requestUrl.substring(
                requestUrl.lastIndexOf('/') + 1
        );

        req.setAttribute(RequestParameter
                .CURR_PAGE_NAME.name().toLowerCase(), currPageName);
    }
    /**
     * Checks if the requested resource is a static content (image file).
     *
     * @param request The HttpServletRequest object representing the incoming request.
     * @return true if the requested resource is a static content, false otherwise.
     */
    private static boolean isStaticContentRequested(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());

        for (String extension :
                staticContentExtensions) {
            if (path.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the requested file exists.
     *
     * @param request The HttpServletRequest object representing the incoming request.
     * @return true if the requested file exists, false otherwise.
     */
    public static boolean fileExist(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());

        String realPath = request.getServletContext().getRealPath(path);
        File imageFile = new File(realPath);
        return imageFile.exists();
    }

    /**
     * Retrieves the selected language from the session, or sets it to English if not present.
     *
     * @param httpServletRequest The HttpServletRequest object representing the incoming request.
     * @return The selected language.
     */
    private Language getLocale(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        Language language = (Language) httpSession.getAttribute(SessionAttribute.LANGUAGE.getName());
        if (language == null) {
            language = Language.ENGLISH;
            httpSession.setAttribute(SessionAttribute.LANGUAGE.getName(), Language.ENGLISH);
        }
        return language;
    }

    /**
     * Filters the incoming request and controls access to commands based on user privileges.
     *
     * @param servletRequest  The ServletRequest object representing the incoming request.
     * @param servletResponse The ServletResponse object representing the outgoing response.
     * @param filterChain     The FilterChain object for invoking the next filter in the chain.
     * @throws IOException      If an I/O error occurs during the filtering process.
     * @throws ServletException If a servlet error occurs during the filtering process.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Language language = getLocale(httpServletRequest);
        httpServletRequest.setAttribute(SessionAttribute.LANGUAGE.getName(), language.getName());

        setCurrPageName(httpServletRequest);
        //if (isStaticContentRequested(httpServletRequest)) {
        //    if (!fileExist(httpServletRequest)) {
        //        ServletContext servletContext = httpServletRequest.getServletContext();
        //        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/controller?command=no_such_command");
        //        requestDispatcher.forward(httpServletRequest, httpServletResponse);
        //        return;
        //    }
        //    filterChain.doFilter(servletRequest, servletResponse);
        //    return;
        //}

        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute(
                SessionAttribute.USER.getName());
        UserPrivilegeRole userPrivilegeRole = UserPrivilegeRole.VISITOR;

        if (user != null) {
            userPrivilegeRole = user.getUserPrivilegeRole();
        }

        httpServletRequest.setAttribute(RequestAttribute.ROLE.name().toLowerCase(),
                userPrivilegeRole.getName());

        //String commandName = httpServletRequest.getParameter(
        //        RequestParameter.COMMAND.name().toLowerCase());

        //if (commandName == null) {
        //    ServletContext servletContext = httpServletRequest.getServletContext();
        //    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/controller?command=no_such_command");
        //    requestDispatcher.forward(httpServletRequest, httpServletResponse);
        //    return;
        //}

        //Set<String> possibleCommandNames = possibleCommands.get(userPrivilegeRole);
        //if (!possibleCommandNames.contains(commandName.toUpperCase())) {
        //    ServletContext servletContext = httpServletRequest.getServletContext();
        //    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/controller?command=no_such_command");
        //    requestDispatcher.forward(httpServletRequest, httpServletResponse);
        //    return;
        //}

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
