package ru.my.dreamjob.service;

import ru.my.dreamjob.model.User;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.6. Database в Web
 * 4. Многопоточность в базе данных [#504860 #285783]
 * UserService интерфейс описывает поведение слоя сервиса модели User.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 01.02.2023
 */
public interface UserService {
    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);
}
