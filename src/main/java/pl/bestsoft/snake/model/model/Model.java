package pl.bestsoft.snake.model.model;

import pl.bestsoft.snake.model.fakes.FakeMap;
import pl.bestsoft.snake.model.fakes.ScoreFakeMap;

/**
 * Model gry.
 */
public class Model {
    /**
     * Plansza na której trwa rozgrywka
     */
    private final Board board;

    /**
     * Tworzy model.
     */
    public Model() {
        board = new Board();
    }

    /**
     * Kończy gre.
     */
    public void endGame() {
        board.endGame();
    }

    /**
     * Zwraca informace czy gra została zakończona.
     */
    public boolean gameIsEnd() {
        return board.gameIsEnd();
    }

    /**
     * Zwraca całą plansze fake'ów.
     */
    public FakeMap getFake() {
        return board.getFake();
    }

    /**
     * Zmienia kierunek węża na wschodni.
     *
     * @param id węża
     */
    public void goEast(SnakeNumber snakeNumber) {
        board.goEast(snakeNumber);
    }

    /**
     * Zmienia kierunek węża na pónocny.
     *
     * @param id węża
     */
    public void goNorth(SnakeNumber snakeNumber) {
        board.goNorth(snakeNumber);
    }

    /**
     * Zmienia kierunek węża na południowy.
     *
     * @param id węża
     */
    public void goSouth(SnakeNumber snakeNumber) {
        board.goSouth(snakeNumber);
    }

    /**
     * Zmienia kierunek węża na zachodni.
     *
     * @param id węża
     */
    public void goWest(SnakeNumber snakeNumber) {
        board.goWest(snakeNumber);
    }

    /**
     * Zwraca informacje o tym czy gra się toczy.
     */
    public boolean inGame() {
        return board.inGame();
    }

    /**
     * Rozpoczyna gre z okrełlona liczbą węży.
     */
    public void startGame(final int howManySnakes) {
        board.startGame(howManySnakes);
    }

    /**
     * Wykonuje ruch węży, przemieszcza węże
     */
    public void moveSnakes() {
        board.moveSnakes();
    }

    /**
     * Zwraca informacje o wynikach węży
     *
     * @return mapa wyników
     */
    public ScoreFakeMap getScoreFakeMap() {
        return board.getScoreFakeMap();
    }

}
