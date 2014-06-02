package pl.bestsoft.snake.dao;

import java.util.ResourceBundle;

/**
 * Author: Daniel
 */
public class TextsDao {

    public static String getText(String key) {
        String propertiesPath = "properties/messages";
        ResourceBundle resource = ResourceBundle.getBundle(propertiesPath);
        return resource.getString(key);
    }
}
