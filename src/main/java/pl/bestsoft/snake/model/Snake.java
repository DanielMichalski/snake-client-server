package pl.bestsoft.snake.model;

import pl.bestsoft.snake.fakes.BodyFake;
import pl.bestsoft.snake.fakes.FakeMap;
import pl.bestsoft.snake.fakes.FakePoint;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Zawiera informacje o wężu.
 */
class Snake {
    /**
     * Zawiera liste czśćci ciała z których składa się wąż.
     */
    private final LinkedList<SnakeBody> snake = new LinkedList<SnakeBody>();
    /**
     * Kierunek w którym porusza się wąż.
     */
    private Direction direction;
    /**
     * Stan w którym znajduje się wąż.
     */
    private SnakeStatus stanSnake = SnakeStatus.ALIVE;
    /**
     * Liczba zdobytych punktów przez w꿹.
     */
    private int score = 0;
    /**
     * Kąt o jaki porusza się wąż
     */
    private static int angle = 20;
    /**
     * Numer identyfikacyjny w꿹
     */
    private final SnakeNumber snakeNumber;

    /**
     * Tworzy nowego węża.
     *
     * @param alfa        kąt alfa ogonu
     * @param beta        kąt beta ogonu
     * @param snakeNumber id Węża
     * @param emptyPoints lista pustych punktów na planszy
     */
    Snake(final int alfa, final int beta, final SnakeNumber snakeNumber, final ArrayList<EmptyPoint> emptyPoints) {
        direction = Direction.EAST;
        makeSnake(alfa, beta, emptyPoints);
        this.snakeNumber = snakeNumber;

    }

    /**
     * Zwraca kąt o jaki porusza się wąż.
     *
     * @return kąt ruchu
     */
    static int getAngle() {
        return angle;
    }

    /**
     * Sprawdza czy dane pole jest zajęte przez węża.
     *
     * @param snakeBody referencja na nowe pole
     * @return
     */
    boolean isBusy(final SnakeBody snakeBody) {
        if (inGame() == 0) {
            return false;
        }
        return snake.contains(snakeBody);
    }

    /**
     * Wpisuje do mapy fake'ów wszystkie punkty które zajmuje wąż.
     *
     * @param mapFake fake mapa do której wpisujemy częci ciała węża
     */
    void fillFake(final FakeMap mapFake) {
        if (inGame() == 0) {
            return;
        }
        for (SnakeBody p : snake) {
            mapFake.setFake(new FakePoint(p.getCoordinates()), new BodyFake(snakeNumber, p.getFrom(), p.getWhere(), p.getBodySize()));
        }
    }

    /**
     * Zwraca kierunek w którym porusza się wąż.
     */
    Direction getDirection() {
        return direction;
    }

    /**
     * Zwraca głowę węża.
     *
     * @return referencje na głowę węża
     */
    SnakeBody getFirst() {
        return snake.getFirst();
    }

    /**
     * Zwraca liste częćci ciała z których składa się wąż.
     */
    LinkedList<SnakeBody> getList() {
        return snake;
    }

    /**
     * Aktualna liczba punktów posiadanych przez węża.
     *
     * @return ilczba punktów posiadanych przez węża
     */
    int getScore() {
        return score;
    }

    /**
     * Zmienia kierunek węża na wschodni.
     */
    void goEast() {
        direction = direction.goEast();
    }

    /**
     * Zmienia kierunek węża na pónocny.
     */
    void goNorth() {
        direction = direction.goNorth();
    }

    /**
     * Zmienia kierunek węża na południowy.
     */
    void goSouth() {
        direction = direction.goSouth();
    }

    /**
     * Zmienia kierunek węża na zachodni.
     */
    void goWest() {
        direction = direction.goWest();
    }

    /**
     * Informuje o tym czy wąż bierze udział w rozgrywce.
     *
     * @return true jeśli wąż yje false w przeciwnym wypadku
     */
    int inGame() {
        return stanSnake.getStan();
    }

    /**
     * Zwraca informacje czy wąż nie żyje.
     *
     * @return
     */
    boolean isNotAlive() {
        if (stanSnake.isDead()) {
            return true;
        }
        return false;
    }

    /**
     * Sprawdza czy wąż nie uderzył w przeciwnika.
     *
     * @param anotherSnake referencja na przeciwnika
     * @param emptyPoints  lista pustych punktów na planszy.
     */
    void isCollisionBetweenSnakes(final Snake anotherSnake, final ArrayList<EmptyPoint> emptyPoints) {
        if (stanSnake.isDead()) {
            return;
        }
        LinkedList<SnakeBody> anotherSnakeList = anotherSnake.getList();
        for (SnakeBody p : anotherSnakeList) {
            if ((!(p.equals(anotherSnake.getFirst()))) && (snake.contains(p))) {
                stanSnake = stanSnake.uderzyl();
                for (SnakeBody snakeBody : snake) {
                    if (!(snakeBody == snake.getFirst())) {
                        emptyPoints.add(new EmptyPoint(snakeBody.getCoordinates()));
                    }
                }
                break;
            }
        }
    }

    /**
     * Zmienia stan węża na nieżywy.
     */
    void killSnake() {
        stanSnake = stanSnake.uderzyl();
    }

    /**
     * Przemieszcza węża wraz ze zjedzeniem jabłka.
     *
     * @param emptyPoints referencja na liste wolnych punktów na planszy
     */
    void moveSnakeWithEatApple(final ArrayList<EmptyPoint> emptyPoints) {
        score += 10;
        moveSnakeHead(emptyPoints);
    }

    /**
     * Przemieszcza węża bez zjadania jabłka.
     *
     * @param emptyPoints lista pustych punktów na planszy
     */
    void moveSnakeWithoutEatApple(final ArrayList<EmptyPoint> emptyPoints) {
        if (stanSnake.isDead()) {
            return;
        }
        emptyPoints.add(new EmptyPoint(snake.getLast().getCoordinates()));

        snake.removeLast();
        (snake.getLast()).setFrom(Direction.UNKNOW);


        moveSnakeHead(emptyPoints);
    }

    /**
     * Zwraca informacje o tym czy wąż żyje.
     *
     * @return true jeśli żyje false w przeciwnym wypadku
     */
    boolean snakeIsAlive() {
        return stanSnake.getStan() == 1;
    }

    /**
     * Oblicza współrzędne nowego położenia częci ciała węża.
     *
     * @return nowa część ciała
     */
    SnakeBody newBody() {
        SnakeBody tmp = snake.getFirst();
        int alfa = tmp.getCoordinates().getAlfa();
        int beta = tmp.getCoordinates().getBeta();
        alfa = getNewAngle(alfa, direction.getVectorAlfa());
        beta = getNewAngle(beta, direction.getVectorBeta());
        return new SnakeBody(new Coordinates(alfa, beta), direction, Direction.UNKNOW);
    }

    /**
     * Oblicza nowy kąt.
     *
     * @param oldAngle
     * @param isMove   określa czy wąż przemieszcza sie w tym kierunku
     * @return
     */
    private static int getNewAngle(final int oldAngle, final int isMove) {
        if (isMove == 0) {
            return oldAngle;
        }
        int newAngle = oldAngle + isMove * angle;
        double x, y;
        x = Math.sin(Math.toRadians(newAngle));
        y = Math.cos(Math.toRadians(newAngle));
        if ((x + 2e-10) >= 0) {
            if (y < 0) {
                newAngle = 180 - (int) Math
                        .round((Math.toDegrees(Math.asin(x))));
                return newAngle;
            } else {
                return (int) Math.round((Math.toDegrees(Math.asin(x))));
            }
        } else {
            if (y < 0) {
                newAngle = 360 - (int) Math.round(Math.toDegrees(Math.acos(y)));
                return newAngle;
            } else {
                newAngle = 360 + (int) Math.round(Math.toDegrees(Math.asin(x)));
                return newAngle;
            }
        }
    }

    /**
     * Przemieszcza głowę węa
     *
     * @param emptyPoints lista pustych punktów na planszy
     */
    private void moveSnakeHead(final ArrayList<EmptyPoint> emptyPoints) {
        if (stanSnake.isDead()) {
            return;
        }
        SnakeBody tmp = newBody();
        if (isBusy(tmp)) {
            killSnake();
            for (SnakeBody snakeBody : snake) {
                emptyPoints.add(new EmptyPoint(snakeBody.getCoordinates()));
            }
            return;
        }
        (snake.getFirst()).setTo(direction);

        snake.addFirst(tmp);
        emptyPoints.remove(new EmptyPoint(tmp.getCoordinates()));
    }

    /**
     * Tworzy węża.
     *
     * @param alfa        wartośc kąta alfa ogonu węa
     * @param beta        wartość kąta beta ogonu węża
     * @param emptyPoints lista pustych punktów na planszy
     */
    private void makeSnake(final int alfa, final int beta, final ArrayList<EmptyPoint> emptyPoints) {
        setTail(alfa, beta, emptyPoints);
        setBody(alfa + angle, beta, emptyPoints);
        setHead(alfa + 2 * angle, beta, emptyPoints);
    }

    /**
     * Dodaje część ciaŁa do listy częci ciała węża.
     *
     * @param alfa        kąt alfa częci ciała
     * @param beta        kąt beta częci ciała
     * @param emptyPoints lista pustych punktów na planszy
     */
    private void setBody(final int alfa, final int beta, final ArrayList<EmptyPoint> emptyPoints) {
        emptyPoints.remove(new EmptyPoint(new Coordinates(alfa, beta)));
        snake.addFirst(new SnakeBody(new Coordinates(alfa, beta), direction, direction));
    }

    /**
     * Dodaje głowe do listy częci ciała węża
     *
     * @param alfa        kąt alfa głowy
     * @param beta        kąt beta głowy
     * @param emptyPoints lista pustych punktów na planszy
     */
    private void setHead(final int alfa, final int beta, final ArrayList<EmptyPoint> emptyPoints) {
        emptyPoints.remove(new EmptyPoint(new Coordinates(alfa, beta)));
        snake.addFirst(new SnakeBody(new Coordinates(alfa, beta), direction, Direction.UNKNOW));
    }

    /**
     * Dodaje ogon do listy części ciała węża
     *
     * @param alfa        kąt alfa ogonu węża
     * @param beta        kąt beta ogonu ęża
     * @param emptyPoints lista pustych punktów na planszy
     */
    private void setTail(final int alfa, final int beta, final ArrayList<EmptyPoint> emptyPoints) {
        emptyPoints.remove(new EmptyPoint(new Coordinates(alfa, beta)));
        snake.addFirst(new SnakeBody(new Coordinates(alfa, beta), Direction.UNKNOW, direction));
    }
}
