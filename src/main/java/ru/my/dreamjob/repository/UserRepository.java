package ru.my.dreamjob.repository;

import ru.my.dreamjob.model.User;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.6. Database в Web
 * 4. Многопоточность в базе данных [#504860 #285783]
 * UserRepository интерфейс описывает поведение хранилища модели User.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 01.02.2023
 */
public interface UserRepository {
    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean deleteById(int id);

    Collection<User> findAll();
}
