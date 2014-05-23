package pl.bestsoft.snake.model.model;

/**
 * Zawiera informacje o stanie w którym znajduje się wąż.
 */
enum SnakeStatus {
    /**
     * wąż żyje
     */
    ALIVE(1) {
        @Override
        boolean isDead() {
            return false;
        }
    },
    /**
     * wąż nie żyje
     */
    DEAD(0) {
        @Override
        boolean isDead() {
            return true;
        }
    };

    /**
     * Określa stan w którym znajduje się wąż.
     */
    private final int stan;

    private SnakeStatus(final int stan) {
        this.stan = stan;
    }

    /**
     * Zwraca stan węża.
     */
    int getStan() {
        return stan;
    }

    /**
     * Zwraca informacje o tym czy wąż nie żyje.
     *
     * @return jesli wąż nie żyje true w przeciwnym przypadku false
     */
    abstract boolean isDead();

    /**
     * Zmienia status węża na DEAD
     */
    SnakeStatus uderzyl() {
        return DEAD;
    }
}
