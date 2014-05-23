package pl.bestsoft.snake.model.model;

import java.util.ArrayList;

/**
 * Przechwuje informacje o jabłku znajdującym sie na planszy
 */
class Apple {
    /**
     * Współrzedne jabłka
     */
    private Coordinates coordinates;
    /**
     * Informacja o tym czy jabłko zostalo zjedzone przez weża
     */
    private AppleStatus appleStatus = AppleStatus.EATEN;

    /**
     * Tworzy nowe jabłko.
     */
    Apple() {
        coordinates = null;
    }

    /**
     * Wybiera nowe miejsce dla jabłka spośród listy wolnych punktów.
     *
     * @param emptyPoints lista wolnych punktów
     */
    void chooseNewPoint(final ArrayList<EmptyPoint> emptyPoints) {
        appleStatus = appleStatus.newApple();
        int chooseIndexEmptyPoint = (int) (Math.random() * emptyPoints.size());
        EmptyPoint chooseEmptyPoint = emptyPoints.get(chooseIndexEmptyPoint);
        coordinates = chooseEmptyPoint.getCoordinates();
        emptyPoints.remove(new EmptyPoint(coordinates));
    }

    /**
     * Zwraca obiekt opisujący połołenie jabłka.
     *
     * @return obiekt zawierajacy współrzędne jabłka.
     */
    Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Zwraca informacje o tym czy dane miejsce na planszy jest zajmowane przez
     * jabłko. W przypadku gdy jest zajęta zmienia stan jabłka na zjedzone.
     *
     * @param snakeBody
     * @return true jeżli dane miejsce jest zajete w przeciwnym wypadku false
     */
    boolean isApple(final SnakeBody snakeBody) {
        if (snakeBody.equals(new SnakeBody(coordinates, Direction.UNKNOW, Direction.UNKNOW))) {
            appleStatus = appleStatus.eatApple();
            return true;
        }
        return false;
    }

    /**
     * Inforumje o stanie w jakim znajduje sie jabłko.
     */
    boolean isEaten() {
        return appleStatus.isEaten();
    }
}
