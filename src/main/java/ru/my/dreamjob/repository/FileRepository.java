package ru.my.dreamjob.repository;

import ru.my.dreamjob.model.File;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.5. Формы
 * 4. Формы. Загрузка файла на сервер. [#504855 #284295]
 * FileRepository интерфейс описывает поведение хранилища файлов.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 30.01.2023
 */
public interface FileRepository {
    File save(File file);

    Optional<File> findById(int id);

    boolean deleteById(int id);
}
