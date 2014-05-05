package pl.bestsoft.snake.events;

import pl.bestsoft.snake.snake.KeySetID;

import java.io.Serializable;

/**
 * Abstrakcyjna klasa reprezentująza wydarzenie związane z naciśnieniem klawisza przez gracza.
 */
public abstract class KeyEvent extends PlayerEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Set klawiszy z którego pochodzi event
     */
    private final KeySetID whichSetKeys;

    /**
     * Tworzy key Event.
     *
     * @param whichSetKeys określa set klawiszy z którego pochodzi zdarzenie.
     */
    public KeyEvent(final KeySetID whichSetKeys) {
        this.whichSetKeys = whichSetKeys;
    }

    /**
     * Zwraca ID setu klawiszy z którego pochodzi zdarzenie.
     *
     * @return ID setu klawiszy
     */
    public KeySetID getWhichSetKeys() {
        return whichSetKeys;
    }

    /**
     * Informuje czy zdareznie pochodzi z podstawowego zestawu klawiszy.
     *
     * @return true jeśli zdarzenie pochodzi z podstawowego zestawu klawiszy.
     */
    public boolean isBasicSet() {
        return whichSetKeys.equals(new KeySetID(1));
    }
}
