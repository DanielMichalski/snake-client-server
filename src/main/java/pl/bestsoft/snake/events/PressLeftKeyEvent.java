package pl.bestsoft.snake.events;

import pl.bestsoft.snake.snake.KeySetID;

import java.io.Serializable;

/**
 * Naciśniecie strzałki w lewo lub odpowiednika przez gracza.
 */
public class PressLeftKeyEvent extends KeyEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Tworzy nowy event związany z naciśnięciem strzałki w lewo.
     *
     * @param whichSetKeys
     */
    public PressLeftKeyEvent(final KeySetID whichSetKeys) {
        super(whichSetKeys);
    }
}
