package pl.bestsoft.snake.view.main_frame;

import javax.swing.*;
import java.awt.*;

/**
 * Informacjie o liczbie zdobytych punktów przez gracza.
 */
class PlayerScore extends JPanel {

    private static final long serialVersionUID = 1L;
    /**
     * Label na której wyświetlane są informacje o liczbie zdobytych punktów przez graczy
     */
    private final JLabel score;

    PlayerScore(final Color color) {
        setSize(40, 50);
        setBackground(color);
        setLayout(null);
        score = new JLabel();
        score.setBounds(30, 20, 50, 30);
        score.setText("0");
        add(score);
    }

    /**
     * Aktualizacja liczby zdobytych punktw.
     *
     * @param newScore nowa liczba zdobytych punktw
     */
    void actScore(final Integer newScore) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                score.setText(newScore.toString());
            }
        });
    }

}
