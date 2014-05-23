package pl.bestsoft.snake.view.main_frame;

import pl.bestsoft.snake.model.fakes.ScoreFake;
import pl.bestsoft.snake.model.messages.ScoreMessage;
import pl.bestsoft.snake.model.model.SnakeNumber;
import pl.bestsoft.snake.util.Const;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Zawiera panele z informacjami o liczbie zdobytych punktów przez graczy.
 */

class ScorePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    /**
     * Lista paneli z wynikami graczy
     */
    private final Map<SnakeNumber, PlayerScore> players;

    public ScorePanel() {
        setBounds(50, 440, 360, 90);
        setLayout(new GridLayout(1, 4));
        setBorder(BorderFactory.createTitledBorder("Wyniki"));
        players = new HashMap<SnakeNumber, PlayerScore>();
        players.put(SnakeNumber.FIRST, new PlayerScore(Const.Colors.RED));
        players.put(SnakeNumber.SECOND, new PlayerScore(Const.Colors.GREEN));
        players.put(SnakeNumber.THIRD, new PlayerScore(Const.Colors.YELLOW));
        players.put(SnakeNumber.FOURTH, new PlayerScore(Const.Colors.MAGENTA));
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
