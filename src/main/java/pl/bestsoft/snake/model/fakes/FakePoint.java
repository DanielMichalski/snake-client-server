package pl.bestsoft.snake.model.fakes;

import pl.bestsoft.snake.model.model.Coordinates;

import java.io.Serializable;

/**
 * Obiekt fake reprezentujący punkt na planszy.
 */
public class FakePoint implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Opisuje położenie punktu na planszy
     */
    private final Coordinates coordinates;

    /**
     * Tworzy nowy fake punkt.
     *
     * @param coordinates
     */
    public FakePoint(final Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Zwraca koordynate opisującą dany punkt.
     *
     * @return
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((coordinates == null) ? 0 : coordinates.hashCode());
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
        FakePoint other = (FakePoint) obj;
        if (coordinates == null) {
            if (other.coordinates != null)
                return false;
        } else if (!coordinates.equals(other.coordinates))
            return false;
        return true;
    }
}
