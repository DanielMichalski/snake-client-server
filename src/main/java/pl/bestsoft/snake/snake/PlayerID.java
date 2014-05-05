package pl.bestsoft.snake.snake;

import java.io.Serializable;

/**
 * Informacje o numerze identyfikacyjnym klienta
 */
public class PlayerID implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Numer identyfikacyjny klienta.
     */
    final private int playerID;

    public PlayerID(final int playerID) {
        this.playerID = playerID;
    }

    /**
     * Informuje o numerze ID klienta.
     *
     * @return PlayerID
     */
    public int getPlayerID() {
        return playerID;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PlayerID other = (PlayerID) obj;
        if (playerID != other.playerID) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + playerID;
        return result;
    }
}
