import pl.bestsoft.snake.view.choose_ip.GetIPNumberWindow;

import java.awt.*;

/**
 * Author: Daniel
 */
public class GetIPNumberFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GetIPNumberWindow frame = new GetIPNumberWindow();
                frame.setVisible(true);
            }
        });
    }
}
