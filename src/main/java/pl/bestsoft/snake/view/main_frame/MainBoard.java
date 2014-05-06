package pl.bestsoft.snake.view.main_frame;

import pl.bestsoft.snake.fakes.*;
import pl.bestsoft.snake.model.Direction;
import pl.bestsoft.snake.model.SnakeNumber;
import pl.bestsoft.snake.util.ImageLoader;
import pl.bestsoft.snake.view.BodyDirection;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Plansza gry
 */
public class MainBoard extends JPanel {

    private static final long serialVersionUID = 1L;
    /**
     * Plansza gry.
     */
    private FakeMap orginFakeMap = null;
    /**
     * Akcje zwiazane z rysoaniem obiektów
     */
    final private HashMap<Class<? extends GameFake>, PaintAction> paintAction;
    /**
     * Mapa zawierająca obrazki dla każdego węża
     */
    final private Map<SnakeNumber, Map<BodyDirection, Image>> pictures;
    /**
     * Obrazek jabłka.
     */
    final private Image apple;
    /**
     * Obrazek tłaa.
     */
    final private Image empty;
    /**
     * Obrazek startowy.
     */
    final private Image startImage;

    private final Class<? extends MainBoard> aClass;

    /**
     * Tworzy nowy Main Board.
     */
    MainBoard() {
        aClass = this.getClass();
        setBounds(50, 50, 360, 360);
        paintAction = new HashMap<Class<? extends GameFake>, PaintAction>();
        apple = ImageLoader.load("Apple.png");
        empty = ImageLoader.load("Empty.png");
        startImage = ImageLoader.load("StartWindow.png");
        fillPaintAction();
        pictures = new HashMap<SnakeNumber, Map<BodyDirection, Image>>();
        pictures.put(SnakeNumber.FIRST, fillBodyImagePlayer1());
        pictures.put(SnakeNumber.SECOND, fillBodyImagePlayer2());
        pictures.put(SnakeNumber.THIRD, fillBodyImagePlayer3());
        pictures.put(SnakeNumber.FOURTH, fillBodyImagePlayer4());
    }

    /**
     * Rysuje plansze na ekranie.
     */
    @Override
    protected void paintComponent(final Graphics g) {

        if (orginFakeMap != null) {
            HashMap<FakePoint, GameFake> mapa = orginFakeMap.getFakeMap();
            for (FakePoint point : mapa.keySet()) {

                g.drawImage((paintAction.get((mapa.get(point)).getClass()))
                        .getRightImage(mapa.get(point)), point.getCoordinates().getAlfa(), point.getCoordinates()
                        .getBeta(), null);
            }
        } else {
            g.drawImage(startImage, 0, 0, this);
        }
    }

    /**
     * Wczytanie nowej mapy fake'ów.
     */
    void setFake(final FakeMap fakeMap) {
        if (orginFakeMap == null) {
            this.orginFakeMap = fakeMap;
        } else {
            HashMap<FakePoint, GameFake> mapa = orginFakeMap.getFakeMap();
            HashMap<FakePoint, GameFake> newMap = fakeMap.getFakeMap();
            mapa.putAll(newMap);
            orginFakeMap.setMap(mapa);
        }
    }

    /**
     * Wypełnienie mapy akcji ryswoania.
     */
    private void fillPaintAction() {
        paintAction.put(BodyFake.class, new PaintBodyAction());
        paintAction.put(EmptyFake.class, new PaintEmpty());
        paintAction.put(AppleFake.class, new PaintApple());
    }

    /**
     * Wypełnienie mapy obrazków pierwszego węża
     */
    private Map<BodyDirection, Image> fillBodyImagePlayer1() {

        final Map<BodyDirection, Image> bodyImagePlayer1 = new HashMap<BodyDirection, Image>();
        bodyImagePlayer1.put(new BodyDirection(Direction.UNKNOW, Direction.EAST), ImageLoader.load("TailEastPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.UNKNOW, Direction.WEST), ImageLoader.load("TailWestPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.UNKNOW, Direction.NORTH), ImageLoader.load("TailNorthPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.UNKNOW, Direction.SOUTH), ImageLoader.load("TailSouthPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.WEST, Direction.WEST), ImageLoader.load("BodyWestEastPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.EAST, Direction.EAST), ImageLoader.load("BodyWestEastPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.NORTH, Direction.NORTH), ImageLoader.load("BodyNorthSouthPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.SOUTH, Direction.SOUTH), ImageLoader.load("BodyNorthSouthPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.SOUTH, Direction.EAST), ImageLoader.load("BodyNorthEastPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.EAST, Direction.SOUTH), ImageLoader.load("BodyWestSouthPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.EAST, Direction.NORTH), ImageLoader.load("BodyWestNorthPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.NORTH, Direction.EAST), ImageLoader.load("BodySouthEastPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.NORTH, Direction.WEST), ImageLoader.load("BodyWestSouthPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.WEST, Direction.NORTH), ImageLoader.load("BodyNorthEastPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.WEST, Direction.SOUTH), ImageLoader.load("BodySouthEastPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.SOUTH, Direction.WEST), ImageLoader.load("BodyWestNorthPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.WEST, Direction.UNKNOW), ImageLoader.load("HeadWestPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.EAST, Direction.UNKNOW), ImageLoader.load("HeadEastPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.SOUTH, Direction.UNKNOW), ImageLoader.load("HeadSouthPlayer1.png"));
        bodyImagePlayer1.put(new BodyDirection(Direction.NORTH, Direction.UNKNOW), ImageLoader.load("HeadNorthPlayer1.png"));
        return Collections.unmodifiableMap(bodyImagePlayer1);
    }

    /**
     * Wypełnienie mapy obrazków drugiego gracza.
     */
    private Map<BodyDirection, Image> fillBodyImagePlayer2() {
        final Map<BodyDirection, Image> bodyImagePlayer2 = new HashMap<BodyDirection, Image>();
        bodyImagePlayer2.put(new BodyDirection(Direction.UNKNOW, Direction.WEST), ImageLoader.load("TailWestPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.UNKNOW, Direction.EAST), ImageLoader.load("TailEastPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.UNKNOW, Direction.SOUTH), ImageLoader.load("TailSouthPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.UNKNOW, Direction.NORTH), ImageLoader.load("TailNorthPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.WEST, Direction.WEST), ImageLoader.load("BodyWestEastPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.EAST, Direction.EAST), ImageLoader.load("BodyWestEastPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.NORTH, Direction.NORTH), ImageLoader.load("BodyNorthSouthPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.SOUTH, Direction.SOUTH), ImageLoader.load("BodyNorthSouthPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.SOUTH, Direction.EAST), ImageLoader.load("BodyNorthEastPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.EAST, Direction.SOUTH), ImageLoader.load("BodyWestSouthPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.EAST, Direction.NORTH), ImageLoader.load("BodyWestNorthPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.NORTH, Direction.EAST), ImageLoader.load("BodySouthEastPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.NORTH, Direction.WEST), ImageLoader.load("BodyWestSouthPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.WEST, Direction.NORTH), ImageLoader.load("BodyNorthEastPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.WEST, Direction.SOUTH), ImageLoader.load("BodySouthEastPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.SOUTH, Direction.WEST), ImageLoader.load("BodyWestNorthPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.EAST, Direction.UNKNOW), ImageLoader.load("HeadEastPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.WEST, Direction.UNKNOW), ImageLoader.load("HeadWestPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.NORTH, Direction.UNKNOW), ImageLoader.load("HeadNorthPlayer2.png"));
        bodyImagePlayer2.put(new BodyDirection(Direction.SOUTH, Direction.UNKNOW), ImageLoader.load("HeadSouthPlayer2.png"));
        return Collections.unmodifiableMap(bodyImagePlayer2);
    }

    /**
     * Wypełnia mape obrazków trzeciego węża.
     *
     * @return
     */
    private Map<BodyDirection, Image> fillBodyImagePlayer3() {
        final Map<BodyDirection, Image> bodyImagePlayer3 = new HashMap<BodyDirection, Image>();
        bodyImagePlayer3.put(new BodyDirection(Direction.UNKNOW, Direction.EAST), ImageLoader.load("TailEastPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.UNKNOW, Direction.WEST), ImageLoader.load("TailWestPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.UNKNOW, Direction.NORTH), ImageLoader.load("TailNorthPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.UNKNOW, Direction.SOUTH), ImageLoader.load("TailSouthPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.WEST, Direction.WEST), ImageLoader.load("BodyWestEastPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.EAST, Direction.EAST), ImageLoader.load("BodyWestEastPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.NORTH, Direction.NORTH), ImageLoader.load("BodyNorthSouthPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.SOUTH, Direction.SOUTH), ImageLoader.load("BodyNorthSouthPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.SOUTH, Direction.EAST), ImageLoader.load("BodyNorthEastPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.EAST, Direction.SOUTH), ImageLoader.load("BodyWestSouthPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.EAST, Direction.NORTH), ImageLoader.load("BodyWestNorthPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.NORTH, Direction.EAST), ImageLoader.load("BodySouthEastPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.NORTH, Direction.WEST), ImageLoader.load("BodyWestSouthPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.WEST, Direction.NORTH), ImageLoader.load("BodyNorthEastPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.WEST, Direction.SOUTH), ImageLoader.load("BodySouthEastPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.SOUTH, Direction.WEST), ImageLoader.load("BodyWestNorthPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.WEST, Direction.UNKNOW), ImageLoader.load("HeadWestPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.EAST, Direction.UNKNOW), ImageLoader.load("HeadEastPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.SOUTH, Direction.UNKNOW), ImageLoader.load("HeadSouthPlayer3.png"));
        bodyImagePlayer3.put(new BodyDirection(Direction.NORTH, Direction.UNKNOW), ImageLoader.load("HeadNorthPlayer3.png"));
        return Collections.unmodifiableMap(bodyImagePlayer3);
    }

    /**
     * Wypełnia mape obrazków czwartego węża.
     *
     * @return
     */
    private Map<BodyDirection, Image> fillBodyImagePlayer4() {
        final Map<BodyDirection, Image> bodyImagePlayer4 = new HashMap<BodyDirection, Image>();
        bodyImagePlayer4.put(new BodyDirection(Direction.UNKNOW, Direction.EAST), ImageLoader.load("TailEastPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.UNKNOW, Direction.WEST), ImageLoader.load("TailWestPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.UNKNOW, Direction.NORTH), ImageLoader.load("TailNorthPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.UNKNOW, Direction.SOUTH), ImageLoader.load("TailSouthPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.WEST, Direction.WEST), ImageLoader.load("BodyWestEastPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.EAST, Direction.EAST), ImageLoader.load("BodyWestEastPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.NORTH, Direction.NORTH), ImageLoader.load("BodyNorthSouthPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.SOUTH, Direction.SOUTH), ImageLoader.load("BodyNorthSouthPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.SOUTH, Direction.EAST), ImageLoader.load("BodyNorthEastPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.EAST, Direction.SOUTH), ImageLoader.load("BodyWestSouthPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.EAST, Direction.NORTH), ImageLoader.load("BodyWestNorthPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.NORTH, Direction.EAST), ImageLoader.load("BodySouthEastPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.NORTH, Direction.WEST), ImageLoader.load("BodyWestSouthPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.WEST, Direction.NORTH), ImageLoader.load("BodyNorthEastPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.WEST, Direction.SOUTH), ImageLoader.load("BodySouthEastPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.SOUTH, Direction.WEST), ImageLoader.load("BodyWestNorthPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.WEST, Direction.UNKNOW), ImageLoader.load("HeadWestPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.EAST, Direction.UNKNOW), ImageLoader.load("HeadEastPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.SOUTH, Direction.UNKNOW), ImageLoader.load("HeadSouthPlayer4.png"));
        bodyImagePlayer4.put(new BodyDirection(Direction.NORTH, Direction.UNKNOW), ImageLoader.load("HeadNorthPlayer4.png"));
        return Collections.unmodifiableMap(bodyImagePlayer4);
    }

    /**
     * Interfejs odpowiedzialny za wykonanie akcji wysowania obrazka na ekranie.
     */
    private interface PaintAction {
        public Image getRightImage(GameFake gameFake);
    }

    /**
     * Narysowanie jabłka.
     */
    private class PaintApple implements PaintAction {
        @Override
        public Image getRightImage(final GameFake gameFake) {
            return apple;
        }
    }

    /**
     * Narysowanie czesci ciala.
     */
    private class PaintBodyAction implements PaintAction {
        @Override
        public Image getRightImage(final GameFake gameFake) {
            BodyFake bodyFake = (BodyFake) gameFake;
            return pictures.get(bodyFake.getWhichPlayer()).get(new BodyDirection(bodyFake.getFrom(), bodyFake.getTo()));
        }
    }

    /**
     * Narysowanie pustego pola.
     */
    private class PaintEmpty implements PaintAction {
        @Override
        public Image getRightImage(final GameFake gameFake) {
            return empty;
        }
    }
}
