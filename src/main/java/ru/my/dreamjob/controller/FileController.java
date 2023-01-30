package ru.my.dreamjob.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.my.dreamjob.service.FileService;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.5. Формы
 * 4. Формы. Загрузка файла на сервер. [#504855 #284295]
 * FileController возвращает файлы из каталога.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 30.01.2023
 */
@Controller
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        var contentOptional = fileService.getFileById(id);
        if (contentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok(contentOptional.get().getContent());
    }
}
