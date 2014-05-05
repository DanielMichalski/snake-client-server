package pl.bestsoft.snake.events;

import pl.bestsoft.snake.snake.KeySetID;

import java.io.Serializable;

/**
 * Naciśnięcie strzałki w dół lub odpowiednika przez gracza.
 */
public class PressDownKeyEvent extends KeyEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Tworzy nowy event związany z naciśnięciem strzałki z dół.
     *
     * @param whichSetKeys
     */
    public PressDownKeyEvent(final KeySetID whichSetKeys) {
        super(whichSetKeys);
    }
}
