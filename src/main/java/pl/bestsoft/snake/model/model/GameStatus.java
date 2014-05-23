package pl.bestsoft.snake.model.model;

enum GameStatus {
    /**
     * Gra trwa.
     */
    INGAME {
        @Override
        GameStatus endGame() {
            return ENDED;
        }

        @Override
        boolean inGame() {
            return true;
        }

        @Override
        GameStatus newGame() {
            return INGAME;
        }
    },
    /**
     * Gra została zakończona.
     */
    ENDED {
        @Override
        GameStatus endGame() {
            return ENDED;
        }

        @Override
        boolean inGame() {
            return false;
        }

        @Override
        GameStatus newGame() {
            return INGAME;
        }
    };

    /**
     * Zmienia status gry na zakończona.
     */
    abstract GameStatus endGame();

    /**
     * Zwraca informace o tym czy gra się toczy.
     */
    abstract boolean inGame();

    /**
     * Zmienia status gry na INGAME.
     */
    abstract GameStatus newGame();
}
