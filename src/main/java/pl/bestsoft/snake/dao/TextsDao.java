package pl.bestsoft.snake.dao;

import java.util.ResourceBundle;

/**
 * Author: Daniel
 */
public class TextsDao {
    private static ResourceBundle resource;

    static {
        resource = ResourceBundle.getBundle("properties/messages");
    }

    public static String getText(String key) {
        return resource.getString(key);
    }
}
