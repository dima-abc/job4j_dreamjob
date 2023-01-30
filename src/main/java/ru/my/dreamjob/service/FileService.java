package ru.my.dreamjob.service;

import ru.my.dreamjob.model.File;
import ru.my.dreamjob.model.dto.FileDto;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 3.2.5. Формы
 * 4. Формы. Загрузка файла на сервер. [#504855 #284295]
 * FileService интерфейс слоя бизнес логики модели File.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 30.01.2023
 */
public interface FileService {
    File save(FileDto fileDto);

    Optional<FileDto> getFileById(int id);

    boolean deleteById(int id);
}
