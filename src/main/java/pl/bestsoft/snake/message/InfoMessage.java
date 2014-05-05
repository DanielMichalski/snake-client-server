package pl.bestsoft.snake.message;

import java.io.Serializable;

/**
 * Komunikaty wysyłane prze sieć do gracza.
 */
public class InfoMessage extends GameMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Tekst zawierajacy wiadomość
     */
    private final String message;

    public InfoMessage(final String message) {
        this.message = message;
    }

    /**
     * @return wiadomość dla gracza.
     */
    public String getMessage() {
        return message;
    }
}
