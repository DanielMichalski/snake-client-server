package pl.bestsoft.snake;

import pl.bestsoft.snake.view.choose_game.ChooseGameTypeWindow;

import java.awt.*;

public class Runner {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChooseGameTypeWindow frame =
                        new ChooseGameTypeWindow();
                frame.display();
            }
        });
    }
}