package pl.bestsoft.snake.message;

import pl.bestsoft.snake.fakes.FakeMap;

import java.io.Serializable;

/**
 * Klasa która opakowuje fake mape w celu wysłania przez sieć.
 */
public class BoardMessage extends GameMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Obiekt Fake zawierający plansze gry.
     */
    private final FakeMap fakeMap;

    public BoardMessage(final FakeMap fakeMap) {
        this.fakeMap = fakeMap;
    }

    /**
     * Zwraca obiekt fake całej planszy.
     *
     * @return plansza gry
     */
    public FakeMap getFakeMap() {
        return fakeMap;
    }
}
