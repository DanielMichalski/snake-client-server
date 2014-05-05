package pl.bestsoft.snake.model;

/**
 * Opisuje kierunek w którym porusza się wąż
 */
public enum Direction {
    /**
     * Wąż porusza się na pónoc
     */
    NORTH(0, -1) {
        @Override
        Direction goEast() {
            return EAST;
        }

        @Override
        Direction goNorth() {
            return NORTH;
        }

        @Override
        Direction goSouth() {
            return NORTH;
        }

        @Override
        Direction goWest() {
            return WEST;
        }

        @Override
        Direction turnBack() {
            return SOUTH;
        }
    },

    /**
     * Wąż porusza się na południe
     */
    SOUTH(0, 1) {
        @Override
        Direction goEast() {
            return EAST;
        }

        @Override
        Direction goNorth() {
            return SOUTH;
        }

        @Override
        Direction goSouth() {
            return SOUTH;
        }

        @Override
        Direction goWest() {
            return WEST;
        }

        @Override
        Direction turnBack() {
            return NORTH;
        }

    },

    /**
     * Wąż porusza się na wschód
     */
    EAST(1, 0) {
        @Override
        Direction goEast() {
            return EAST;
        }

        @Override
        Direction goNorth() {
            return NORTH;
        }

        @Override
        Direction goSouth() {
            return SOUTH;
        }

        @Override
        Direction goWest() {
            return EAST;
        }

        @Override
        Direction turnBack() {
            return WEST;
        }
    },

    /**
     * Wąż porusza się na zachód
     */
    WEST(-1, 0) {
        @Override
        Direction goEast() {
            return WEST;
        }

        @Override
        Direction goNorth() {
            return NORTH;
        }

        @Override
        Direction goSouth() {
            return SOUTH;
        }

        @Override
        Direction goWest() {
            return WEST;
        }

        @Override
        Direction turnBack() {
            return EAST;
        }
    },

    /**
     * Wąż porusza się w kierunku nieznanym
     */
    UNKNOW(0, 0) {
        @Override
        Direction goEast() {
            return UNKNOW;
        }

        @Override
        Direction goNorth() {
            return UNKNOW;
        }

        @Override
        Direction goSouth() {
            return UNKNOW;
        }

        @Override
        Direction goWest() {
            return UNKNOW;
        }

        @Override
        Direction turnBack() {
            return UNKNOW;
        }
    };

    /**
     * Informacje o wektorze przemieszczenia
     */
    private int vectorAlfa, vectorBeta;

    private Direction(final int vectorAlfa, final int vectorBeta) {
        this.vectorAlfa = vectorAlfa;
        this.vectorBeta = vectorBeta;
    }

    /**
     * Zwraca informacje o wektorze alfa kierunku.
     */
    int getVectorAlfa() {
        return vectorAlfa;
    }

    /**
     * Zwraca informacje o wektorze beta kierunku.
     */
    int getVectorBeta() {
        return vectorBeta;
    }

    /**
     * Zmienia kierunek na wschodni w przypadku gdy jest to możliwe.
     */
    abstract Direction goEast();

    /**
     * Zmienia kierunek na północny w przypadku gdy jest to możliwe.
     */
    abstract Direction goNorth();

    /**
     * Zmienia kierunek na południowy w przypadku gdy jest to możliwe.
     */
    abstract Direction goSouth();

    /**
     * Zmienia kierunek na zachodni w przypadku gdy jest to możliwe.
     */
    abstract Direction goWest();

    /**
     * Zmienia kierunek na przeciwny.
     */
    abstract Direction turnBack();

}