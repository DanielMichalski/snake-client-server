package pl.bestsoft.snake.events;

import pl.bestsoft.snake.snake.KeySetID;

import java.io.Serializable;

/**
 * Naciśniecie strzałki w góre lub odpowiednika przez gracza.
 */
public class PressUpKeyEvent extends KeyEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Tworzy nowy obiekt związany z naciśnięciem klawisza w góre.
     *
     * @param whichSetKeys
     */
    public PressUpKeyEvent(final KeySetID whichSetKeys) {
        super(whichSetKeys);
    }
}
