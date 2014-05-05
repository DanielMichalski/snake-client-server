package pl.bestsoft.snake.model;

/**
 * Informacja o stanie w którym znajduje się jabłko.
 */
enum AppleStatus {
    /**
     * Jabłko zostało zjedzone przez węża
     */
    EATEN {
        @Override
        boolean isEaten() {
            return true;
        }
    },

    /**
     * Jabłko nie zostało zjedzone przez węża
     */
    NOEATEN {
        @Override
        boolean isEaten() {
            return false;
        }
    };

    /**
     * Zmienia stan jabłka na zjedzony.
     */
    AppleStatus eatApple() {
        return EATEN;
    }

    /**
     * Inforumje o tym czy jabłko zostało zjedzone.
     */
    abstract boolean isEaten();

    /**
     * Zmienia stan jabłka na nie zjedzone
     */
    AppleStatus newApple() {
        return NOEATEN;
    }
}
