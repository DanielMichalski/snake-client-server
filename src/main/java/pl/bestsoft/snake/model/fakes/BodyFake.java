package pl.bestsoft.snake.model.fakes;

import pl.bestsoft.snake.model.model.BodySize;
import pl.bestsoft.snake.model.model.Direction;
import pl.bestsoft.snake.model.model.SnakeNumber;

import java.io.Serializable;

/**
 * Obiekt fake reprezentujący część ciała węża na planszy.
 */
public class BodyFake extends GameFake implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Id węża.
     */
    final private SnakeNumber snakeNumber;
    /**
     * Kierunek skąd przyszedł wąż na dane pole.
     */
    final private Direction from;
    /**
     * Kierunek w którym idzie wąż z danego pola.
     */
    final private Direction to;
    /**
     * Rozmiar węża na danym polu.
     */
    final private BodySize bodySize;

    /**
     * Tworzy nowy obiekt części ciała węża.
     *
     * @param snakeNumber id węa
     * @param from        kierunek skąd przyszedł wąż na dane pole.
     * @param to          kierunek dokąd idzie wąż z danego pola
     * @param bodySize    rozmiar części ciała na danym polu
     */
    public BodyFake(final SnakeNumber snakeNumber, final Direction from, final Direction to, final BodySize bodySize) {
        this.snakeNumber = snakeNumber;
        this.from = from;
        this.to = to;
        this.bodySize = bodySize;
    }

    /**
     * Zwraca rozmiar węża.
     *
     * @return rozmiar węża
     */
    public BodySize getBodySize() {
        return bodySize;
    }

    /**
     * Zwraca kierunek skąd przyszedł wąż.
     *
     * @return kierunek skąd wąż przyszedł na dane pole
     */
    public Direction getFrom() {
        return from;
    }

    /**
     * Zwraca kierunek do którego idzie wąż w wybranego pola.
     *
     * @return kierunek dokąd wąż idzie z wybranego pola
     */
    public Direction getTo() {
        return to;
    }

    /**
     * Zwraca ID węża.
     *
     * @return numer ID węża
     */
    public SnakeNumber getWhichPlayer() {
        return snakeNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((bodySize == null) ? 0 : bodySize.hashCode());
        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result + ((to == null) ? 0 : to.hashCode());
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
        BodyFake other = (BodyFake) obj;
        if (bodySize != other.bodySize)
            return false;
        if (from != other.from)
            return false;
        if (to != other.to)
            return false;
        if (snakeNumber != other.snakeNumber)
            return false;
        return true;
    }
}
