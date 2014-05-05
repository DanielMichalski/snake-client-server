package pl.bestsoft.snake.fakes;

import pl.bestsoft.snake.model.SnakeNumber;

import java.io.Serializable;
import java.util.Map;

/**
 * Zawiera mape wyników wszystkich graczy.
 */
public class ScoreFakeMap implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Mapa wyników wszystkich graczy.
     */
    private final Map<SnakeNumber, ScoreFake> scoreMap;

    /**
     * Tworzy nową mape wyników wszystkich graczy.
     *
     * @param scoreMap
     */
    public ScoreFakeMap(final Map<SnakeNumber, ScoreFake> scoreMap) {
        this.scoreMap = scoreMap;
    }

    /**
     * Zwraca mape wyników graczy.
     *
     * @return
     */
    public Map<SnakeNumber, ScoreFake> getScoreMap() {
        return scoreMap;
    }
}
