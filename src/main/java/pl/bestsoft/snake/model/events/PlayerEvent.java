package pl.bestsoft.snake.model.events;

import pl.bestsoft.snake.snake.PlayerID;

import java.io.Serializable;

/**
 * Wydarzenia związane z graczem.
 */
public abstract class PlayerEvent extends GameEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID gracza
     */
    private PlayerID ID;

    /**
     * Tworzy nowe zdarzenie dotyczące gracza.
     */
    public PlayerEvent() {
        ID = new PlayerID(0);
    }

    /**
     * Zwraca numer ID gracza.
     *
     * @return numer ID gracza
     */
    public PlayerID getID() {
        return ID;
    }

    /**
     * Nadanie graczowi numeru ID.
     *
     * @param ID numer ID gracza
     */
    public void setID(final PlayerID ID) {
        this.ID = ID;
    }
}
