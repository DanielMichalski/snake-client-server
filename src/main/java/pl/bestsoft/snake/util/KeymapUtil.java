package pl.bestsoft.snake.util;

import javax.swing.*;
import java.io.InputStream;
import java.util.Scanner;

public class KeymapUtil {
    public static void showDefaultKeymap() {
        InputStream resourceAsStream = KeymapUtil.class
                .getResourceAsStream("/keymap/keymap.txt");

        Scanner scanner = new Scanner(resourceAsStream);

        StringBuilder sb = new StringBuilder();

        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }

        JOptionPane.showMessageDialog(
                null,
                sb,
                "Sterowanie",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
