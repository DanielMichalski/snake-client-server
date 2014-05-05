package pl.bestsoft.snake.snake;

import java.io.Serializable;

/**
 * Zawiera informacje na temat zestawu klawiszy.
 */
public class KeySetID implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Okre la numer zestawu klawiszy
     */
    private final int setNumber;

    public KeySetID(int setNumber) {
        this.setNumber = setNumber;
    }

    /**
     * Zawraca numer zestwu klawiszy.
     *
     * @return
     */
    public int getSetNumber() {
        return setNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + setNumber;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeySetID other = (KeySetID) obj;
        if (setNumber != other.setNumber)
            return false;
        return true;
    }
}
