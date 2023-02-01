package ru.my.dreamjob.model;

import java.util.Objects;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.6. Database в Web
 * 4. Многопоточность в базе данных [#504860 #285783]
 * User модель данных пользователя системы.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 01.02.2023
 */
public class User {
    private int id;
    private String email;
    private String name;
    private String password;

    public User() {
    }

    public User(int id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", email='" + email + '\''
                + ", name='" + name + '\'' + ", password='" + password + '\'' + '}';
    }
}
