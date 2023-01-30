package ru.my.dreamjob.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 4. Thymeleaf, Циклы. [#504841 #281377]
 * Vacancy модель данных вакансий.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 09.01.2023
 */
public class Vacancy {
    private int id;
    private String title;
    private String description;
    private boolean visible;
    private int cityId;
    private int fileId;
    private LocalDateTime createDate = LocalDateTime.now().withNano(0);

    public Vacancy() {
    }

    public Vacancy(int id, String title,
                   String description,
                   boolean visible,
                   int cityId,
                   int fileId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.visible = visible;
        this.cityId = cityId;
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Vacancy{id=" + id + ", title='" + title + '\''
                + ", description='" + description + '\'' + ", visible=" + visible
                + ", cityId=" + cityId + ", fileId=" + fileId + ", createDate=" + createDate + '}';
    }
}
