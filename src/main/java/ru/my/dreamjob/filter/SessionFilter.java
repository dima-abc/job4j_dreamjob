package ru.my.dreamjob.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.my.dreamjob.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 3.2.7. Авторизация и аутентификация
 * 3. Filter [#504865 #287514]
 * SessionFilter фильтр авторизации 2 @Order(2)
 * определяет имя пользователя в системе.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 03.02.2023
 */
@Component
@Order(2)
public class SessionFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var session = request.getSession();
        addUserToSession(session, request);
        chain.doFilter(request, response);
    }

    private void addUserToSession(HttpSession session, HttpServletRequest request) {
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        request.setAttribute("user", user);
    }
}
