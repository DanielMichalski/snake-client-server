package pl.bestsoft.snake.message;

import pl.bestsoft.snake.fakes.ScoreFakeMap;

import java.io.Serializable;

/**
 * Zawiera informacje o zdobytej liczbie punktów przez graczy.
 */
public class ScoreMessage extends GameMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Mapa zawierająca wyniki graczy.
     */
    private final ScoreFakeMap scoreFakeMap;

    public ScoreMessage(ScoreFakeMap scoreFakeMap) {
        this.scoreFakeMap = scoreFakeMap;
    }

    /**
     * Zwraca mape wyników graczy.
     *
     * @return mapa wyników graczy.
     */
    public ScoreFakeMap getScoreFakeMap() {
        return scoreFakeMap;
    }
}
