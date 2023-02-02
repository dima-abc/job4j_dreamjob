package ru.my.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.my.dreamjob.model.User;
import ru.my.dreamjob.repository.UserRepository;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.6. Database в Web
 * 4. Многопоточность в базе данных [#504860 #285783]
 * SimpleUserService реализация слоя сервиса модели User.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 02.02.2023
 */
@Service
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;

    public SimpleUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
