package pl.bestsoft.snake;

import org.springframework.context.ApplicationContext;
import pl.bestsoft.snake.spring.ContextProvider;
import pl.bestsoft.snake.view.choose_game.ChooseGameTypeWindow;

import java.awt.*;

public class Runner {
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationContext context =
                        ContextProvider.getSpringContext();

                ChooseGameTypeWindow frame =
                        context.getBean("chooseGameTypeWindow", ChooseGameTypeWindow.class);

                frame.display();
            }
        });
    }
}