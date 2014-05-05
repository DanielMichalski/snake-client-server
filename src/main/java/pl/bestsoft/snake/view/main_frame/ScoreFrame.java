package pl.bestsoft.snake.view.main_frame;

import pl.bestsoft.snake.fakes.ScoreFake;
import pl.bestsoft.snake.message.ScoreMessage;
import pl.bestsoft.snake.model.SnakeNumber;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Zawiera panele z informacjami o liczbie zdobytych punktów przez graczy.
 */

class ScoreFrame extends JPanel {

    private static final long serialVersionUID = 1L;
    /**
     * Lista paneli z wynikami graczy
     */
    private final Map<SnakeNumber, PlayerScore> players;

    public ScoreFrame() {
        setSize(40, 100);
        setPreferredSize(new Dimension(70, 360));
        setLayout(new GridLayout(5, 1));
        JLabel lab = new JLabel("Wyniki:");
        add(lab);
        players = new HashMap<SnakeNumber, PlayerScore>();
        players.put(SnakeNumber.FIRST, new PlayerScore(Color.RED));
        players.put(SnakeNumber.SECOND, new PlayerScore(Color.GREEN));
        players.put(SnakeNumber.THIRD, new PlayerScore(Color.YELLOW));
        players.put(SnakeNumber.FOURTH, new PlayerScore(Color.MAGENTA));
        add(players.get(SnakeNumber.FIRST));
        add(players.get(SnakeNumber.SECOND));
        add(players.get(SnakeNumber.THIRD));
        add(players.get(SnakeNumber.FOURTH));
    }

    /**
     * Aktualizacja wyników graczy.
     *
     * @param scoreMessage informacja o nomym wyniku oraz id weża
     */
    void actScore(final ScoreMessage scoreMessage) {
        Map<SnakeNumber, ScoreFake> scoreFakeMap = scoreMessage.getScoreFakeMap().getScoreMap();
        for (SnakeNumber snakeNumber : scoreFakeMap.keySet()) {
            players.get(snakeNumber).actScore(scoreFakeMap.get(snakeNumber).getScore());
        }
    }
}
