package pl.bestsoft.snake;

import pl.bestsoft.snake.game.ChooseGameTypeWindow;

import javax.swing.*;
import java.awt.*;

public class Runner {
    public static void main(String[] args) {
        try {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ChooseGameTypeWindow frame =
                            new ChooseGameTypeWindow();
                    frame.display();
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}