package pl.bestsoft.snake.model;

/**
 * Identyfikuje numer węża.
 */
public enum SnakeNumber {
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4);

    /**
     * numer węża
     */
    private final int number;

    private SnakeNumber(final int number) {
        this.number = number;
    }

    /**
     * Zwraca id węża.
     */
    public int getNumber() {
        return number;
    }
}
