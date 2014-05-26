package pl.bestsoft.snake.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageLoader {
    public static Image load(String imageTitle) {
        Class<ImageLoader> aClass = ImageLoader.class;
        URL resource = aClass.getResource("/images/" + imageTitle);
        return new ImageIcon(resource).getImage();
    }
}
