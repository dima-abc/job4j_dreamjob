package ru.my.dreamjob.model;

import java.util.Objects;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.5. Формы
 * 4. Формы. Загрузка файла на сервер. [#504855 #284295]
 * File доменная модель описывает хранимы файл.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 30.01.2023
 */
public class File {
    private int id;
    private String name;
    private String path;

    public File() {
    }

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        File file = (File) o;
        return id == file.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "File{id=" + id + ", name='" + name + '\''
                + ", path='" + path + '\'' + '}';
    }
}
