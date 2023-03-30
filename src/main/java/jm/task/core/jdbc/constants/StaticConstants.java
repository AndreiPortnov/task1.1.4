package jm.task.core.jdbc.constants;

public enum StaticConstants {
    TABLE_COLUMN_ID("id"),
    TABLE_COLUMN_NAME("name"),
    TABLE_COLUMN_LASTNAME("last_name"),
    TABLE_COLUMN_AGE("age"),
    USER_ADDING_ERROR("не удалось добавить пользователя"),
    GETTING_USERS_ERROR("не удалось создать список"),
    URL_KEY("jdbc:mysql://localhost:3306/task_1_1_4"),
    USERNAME_KEY("root"),
    PASSWORD_KEY("root");

    private String title;

    StaticConstants(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "SConstants{" +
               "title='" + title + '\'' +
               '}';
    }
}
