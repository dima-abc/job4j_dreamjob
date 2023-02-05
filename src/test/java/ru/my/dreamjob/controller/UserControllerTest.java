package ru.my.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.my.dreamjob.model.User;
import ru.my.dreamjob.service.UserService;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.8. Spring Test, Mockito
 * 2. Тестируем VacancyController [#504867 #288430]
 * Test UserController
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 05.02.2023
 */
public class UserControllerTest {
    private UserService userService;
    private UserController userController;


    @BeforeEach
    public void initService() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void whenRequestGetLoginPageThenReturnLoginPage() {
        var view = userController.getLoginPage();

        assertThat(view).isEqualTo("users/login");
    }

    @Test
    public void whenRequestPostLoginUserThenRedirectIndexPage() {
        var user = new User(1, "mail", "name", "123");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model);

        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    public void whetRequestPostLoginUserThenReturnLoginPageAndSameMessage() {
        var expectMessage = "Почта или пароль введены не верно";
        var user = new User(22, "mail22", "name22", "pass22");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model);
        var actualMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualMessage).isEqualTo(expectMessage);
    }

    @Test
    public void whenRequestGetRegisterPageThenReturnRegistrationPage() {
        var view = userController.getRegistrationPage();
        assertThat(view).isEqualTo("users/registration");
    }

    @Test
    public void whenRequestPostRegisterUserThenRedirectIndexPage() {
        var user = new User(1, "mail", "name", "pass");
        when(userService.save(user)).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(model, user);

        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    public void whenRequestPostRegisterThenReturnErrorsPageAndSameMessage() {
        var expectedMessage = "Пользователь с такой почтой уже существует";
        var user = new User(1, "mail", "name", "pass");
        when(userService.save(user)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void whenRequestLogoutUserThenRedirectLoginPage() {
        HttpSession httpSession = mock(HttpSession.class);
        var view = userController.logout(httpSession);

        assertThat(view).isEqualTo("redirect:/users/login");
    }
}