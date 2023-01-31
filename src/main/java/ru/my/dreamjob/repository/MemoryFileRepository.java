package ru.my.dreamjob.repository;

import ru.my.dreamjob.model.File;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.5. Формы
 * 4. Формы. Загрузка файла на сервер. [#504855 #284295]
 * MemoryFileRepository хранилище файлов в памяти.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 30.01.2023
 */
public class MemoryFileRepository implements FileRepository {
    private final AtomicInteger nextId = new AtomicInteger(0);
    private final Map<Integer, File> files = new ConcurrentHashMap<>();

    @Override
    public File save(File file) {
        file.setId(nextId.incrementAndGet());
        files.put(file.getId(), file);
        return file;
    }

    @Override
    public Optional<File> findById(int id) {
        return Optional.ofNullable(files.get(id));
    }

    @Override
    public boolean deleteById(int id) {
        return files.remove(id) != null;
    }
}
