package pl.bestsoft.snake.events;

import pl.bestsoft.snake.snake.KeySetID;

import java.io.Serializable;

/**
 * Naciśniecie strzałki w prawo lub odpowiednika przez gracza.
 */
public class PressRightKeyEvent extends KeyEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Tworzy nowy obiek związany z naciśnięciem strzałki w prawo.
     *
     * @param whichSetKeys
     */
    public PressRightKeyEvent(final KeySetID whichSetKeys) {
        super(whichSetKeys);
    }
}
