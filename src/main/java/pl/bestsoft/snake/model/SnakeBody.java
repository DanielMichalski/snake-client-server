package pl.bestsoft.snake.model;

import java.io.Serializable;

/**
 * Część ciała węża.
 */
public class SnakeBody implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Położenie czeęci ciała na planszy.
     */
    final private Coordinates coordinates;
    /**
     * opis kierunku częci ciała, kierunek przy wejściu na dane pole
     */
    private Direction fromDirection;
    /**
     * opis kierunku części ciała, kierune przy wyjściu z danego pola
     */
    private Direction toDirection;
    /**
     * Rozmiar części ciała
     */
    private BodySize bodySize = BodySize.NORMAL;

    /**
     * Tworzy nową część ciała
     *
     * @param coordinates położenie czci ciaa
     * @param from        kierunek skąd wąż przyszedł na pole
     * @param to          kierunek dokąd wąż idzie z tego pola
     */
    public SnakeBody(final Coordinates coordinates, final Direction from, final Direction to) {
        this.coordinates = coordinates;
        this.fromDirection = from;
        this.toDirection = to;
    }

    /**
     * Informacja o położeniu częci ciała.
     *
     * @return
     */
    Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * informacja o rozmiarze częci ciała.
     *
     * @return BIG jeśli w tym miejscu znajdowało się jabłko zjedzone przez węża
     */
    BodySize getBodySize() {
        return bodySize;
    }

    /**
     * Informacja o tym skąd wąż przyszedł na to miejsce na planszy.
     *
     * @return
     */
    Direction getFrom() {
        return fromDirection;
    }

    /**
     * Informacja o tym dokąd wąż idzie z tego miejsca na planszy.
     *
     * @return
     */
    Direction getWhere() {
        return toDirection;
    }

    /**
     * Wzrost części ciała po zjedzeniu jabłka.
     */
    void grow() {
        bodySize = bodySize.grow();
    }

    /**
     * Ustawienie kierunku z którego przyszedł wąż na dane miejsze na planszy.
     *
     * @param from
     */
    void setFrom(final Direction from) {
        this.fromDirection = from;
    }

    /**
     * Ustawienie kierunku w którym wąż idzie z tego miejsca na planszy.
     *
     * @param to
     */
    void setTo(final Direction to) {
        this.toDirection = to;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((coordinates == null) ? 0 : coordinates.hashCode());
        result = prime * result
                + ((fromDirection == null) ? 0 : fromDirection.hashCode());
        result = prime * result
                + ((toDirection == null) ? 0 : toDirection.hashCode());
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
        SnakeBody other = (SnakeBody) obj;
        if (coordinates == null) {
            if (other.coordinates != null)
                return false;
        } else if (!coordinates.equals(other.coordinates))
            return false;
        return true;
    }
}
