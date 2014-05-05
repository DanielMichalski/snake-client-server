package pl.bestsoft.snake.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Author: Daniel
 */
public class ImageLoader {
    private static Class<? extends ImageLoader> aClass;

    static {
        aClass = ImageLoader.class;
    }

    public static Image load(String imageTitle) {
        URL resource = aClass.getResource("/images/" + imageTitle);
        return new ImageIcon(resource).getImage();
    }
}
