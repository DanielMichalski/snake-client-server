import pl.bestsoft.snake.view.choose_clients.NumberOfClientsFrame;

import java.awt.*;

/**
 * Author: Daniel
 */
public class HowManyClientsFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                NumberOfClientsFrame frame = new NumberOfClientsFrame(null);
                frame.setVisible(true);
            }
        });
    }
}
