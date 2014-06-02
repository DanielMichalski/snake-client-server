package pl.bestsoft.snake.controler;

import pl.bestsoft.snake.dao.TextsDao;
import pl.bestsoft.snake.model.events.*;
import pl.bestsoft.snake.model.fakes.FakeMap;
import pl.bestsoft.snake.model.messages.InfoMessage;
import pl.bestsoft.snake.model.messages.ScoreMessage;
import pl.bestsoft.snake.model.model.Model;
import pl.bestsoft.snake.model.model.SnakeNumber;
import pl.bestsoft.snake.network.NetworkModule;
import pl.bestsoft.snake.snake.KeySetID;
import pl.bestsoft.snake.snake.PlayerID;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Kontroler gry.
 */
public class Controler {
    /**
     * Model któreg metody wywołuje kontroler.
     */
    private final Model model;
    /**
     * Kolejka blokująca do której wrzucane są eventy.
     */
    private final BlockingQueue<GameEvent> blockingQueue;
    /**
     * Liczba węży w grze.
     */
    private final int snakes;
    /**
     * Moduł sieciowy.
     */
    private final NetworkModule networkModule;
    /**
     * Mapa akcji związanych z eventami wrzucanymi do kolejki blokującej.
     */
    private final Map<Class<? extends GameEvent>, GameAction> actions;
    /**
     * Mapa akcji dotyczących zmiany kierunku węża
     */
    private final Map<Class<? extends PlayerEvent>, TurningAction> turningActions;
    /**
     * Mapa zawierająca listy akcji dotyczących konkretnego węża
     */
    private final Map<SnakeNumber, LinkedList<GameEvent>> playerEvents;
    /**
     * Mapa zawierająca odwzorowania setów klawiszy na węże którymi sterują
     */
    private final Map<KeySetID, SnakeNumber> keySetIDMap;
    /**
     * Mapa zawierająca odwzorowania klientów na węże którymi sterują
     */
    private final Map<PlayerID, SnakeNumber> playerIDMap;

    /**
     * Tworzy nowy obiekt kontrolera.
     *
     * @param model          model
     * @param blockingQueue  kolejka blokująca
     * @param howManyClients liczba klientów biorących udział w rozgrywce
     * @param snakes         liczba węży na planszy
     */
    public Controler(final Model model, final BlockingQueue<GameEvent> blockingQueue, final int howManyClients, final int snakes) {
        this.model = model;
        this.blockingQueue = blockingQueue;
        this.snakes = snakes;
        this.networkModule = new NetworkModule(blockingQueue);
        actions = fillActions();
        turningActions = fillTurning();
        playerEvents = fillPlayerEvent();
        keySetIDMap = fillKeySetIDMap();
        playerIDMap = fillPlayerIDMap();
        networkModule.begin(howManyClients);
    }

    /**
     * Rozpoczyna działanie kontrolera.
     */
    public void begin() {
        while (true) {
            try {
                GameEvent gameEvent = blockingQueue.take();
                actions.get(gameEvent.getClass()).perform(gameEvent);
            } catch (Exception e) {
                System.out.println(TextsDao.getText("Controler.3"));
                e.printStackTrace();
            }
        }
    }

    /**
     * Wypełnia mape odwzorowań setów klawiszy na numery węży.
     *
     * @return keySetIDMap
     */
    private Map<KeySetID, SnakeNumber> fillKeySetIDMap() {
        Map<KeySetID, SnakeNumber> keySetIDMap = new HashMap<KeySetID, SnakeNumber>();
        keySetIDMap.put(new KeySetID(1), SnakeNumber.FIRST);
        keySetIDMap.put(new KeySetID(2), SnakeNumber.SECOND);
        keySetIDMap.put(new KeySetID(3), SnakeNumber.THIRD);
        keySetIDMap.put(new KeySetID(4), SnakeNumber.FOURTH);
        return Collections.unmodifiableMap(keySetIDMap);
    }

    /**
     * Wypełnia mape odwzorowań ID klientów na numery węży.
     *
     * @return playerIDMap
     */
    private Map<PlayerID, SnakeNumber> fillPlayerIDMap() {
        Map<PlayerID, SnakeNumber> playerIDMap = new HashMap<PlayerID, SnakeNumber>();
        playerIDMap.put(new PlayerID(1), SnakeNumber.FIRST);
        playerIDMap.put(new PlayerID(2), SnakeNumber.SECOND);
        playerIDMap.put(new PlayerID(3), SnakeNumber.THIRD);
        playerIDMap.put(new PlayerID(4), SnakeNumber.FOURTH);
        return Collections.unmodifiableMap(playerIDMap);
    }

    /**
     * Tworzy listy eventów dotyczących węży.
     * Dla każdego węża tworzona jest osobna kolejka w której są przechowywane
     * informacje o naciśnięciach klawiszy pomiędzy przerwaniami zegara.
     *
     * @return playerEvents
     */
    private Map<SnakeNumber, LinkedList<GameEvent>> fillPlayerEvent() {
        Map<SnakeNumber, LinkedList<GameEvent>> playerEvent = new HashMap<SnakeNumber, LinkedList<GameEvent>>();
        playerEvent.put(SnakeNumber.FIRST, new LinkedList<GameEvent>());
        playerEvent.put(SnakeNumber.SECOND, new LinkedList<GameEvent>());
        playerEvent.put(SnakeNumber.THIRD, new LinkedList<GameEvent>());
        playerEvent.put(SnakeNumber.FOURTH, new LinkedList<GameEvent>());
        return Collections.unmodifiableMap(playerEvent);
    }

    /**
     * Wypełnia mape akcji związanych z nadchodzącymi eventami.
     *
     * @return actions
     */
    private Map<Class<? extends GameEvent>, GameAction> fillActions() {
        Map<Class<? extends GameEvent>, GameAction> actions = new HashMap<Class<? extends GameEvent>, GameAction>();
        actions.put(PressDownKeyEvent.class, new PressDownAction());
        actions.put(PressLeftKeyEvent.class, new PressLeftAction());
        actions.put(PressRightKeyEvent.class, new PressRightAction());
        actions.put(PressUpKeyEvent.class, new PressUpAction());
        actions.put(TimerEvent.class, new TimerAction());
        actions.put(NewGameEvent.class, new NewGameAction());
        actions.put(EndGameEvent.class, new EndGameAction());
        return Collections.unmodifiableMap(actions);
    }

    /**
     * Wypełnia mape akcji związanych ze zmianą kierunku węża.
     *
     * @return turningActions
     */
    private Map<Class<? extends PlayerEvent>, TurningAction> fillTurning() {
        Map<Class<? extends PlayerEvent>, TurningAction> turningAction = new HashMap<Class<? extends PlayerEvent>, TurningAction>();
        turningAction.put(PressUpKeyEvent.class, new GoNorth());
        turningAction.put(PressDownKeyEvent.class, new GoSouth());
        turningAction.put(PressLeftKeyEvent.class, new GoWest());
        turningAction.put(PressRightKeyEvent.class, new GoEast());
        return Collections.unmodifiableMap(turningAction);
    }

    /**
     * Wysyła graczom informacje o zakończeniu gry.
     */
    private void sendEndInformation() {
        if (!model.inGame()) {
            networkModule.sendAllPlayersMessage(new InfoMessage(TextsDao.getText("Controler.2")));
        }
    }

    /**
     * Wysyła graczom mape fake'ów.
     */
    private void sendFakeBoardAllClients() {
        FakeMap fakeMap = model.getFake();
        networkModule.sendAllPlayersFakeMap(fakeMap);
    }

    /**
     * Wysyła graczom informacje o wynikach.
     */
    private void sendScoreInformation() {
        networkModule.sendAllPlayersScore(new ScoreMessage(model.getScoreFakeMap()));
    }

    /**
     * Klasa odpowiedzialna za wykonanie akcji związanych z eventami wrzucanymi do kolejki blokującej.
     */
    private abstract class GameAction {
        abstract void perform(final GameEvent gameEvent);
    }

    /**
     * Zakończenie gry.
     */
    private class EndGameAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            model.endGame();
        }
    }

    /**
     * Rozpoczęcie nowej gry.
     */
    private class NewGameAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            if (networkModule.isMoreThanOnePlayer() && (!networkModule.allPlayersAreConected())) {
                return;
            }
            for (LinkedList<GameEvent> liknedList : playerEvents.values()) {
                liknedList.clear();
            }
            model.startGame(snakes);
        }
    }

    /**
     * Naciśniecie strzałki w dół.
     */
    private class PressDownAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            if (networkModule.isMoreThanOnePlayer()) {
                PressDownKeyEvent pressDownKeyEvent = (PressDownKeyEvent) gameEvent;
                if (pressDownKeyEvent.isBasicSet()) {
                    playerEvents.get(playerIDMap.get(pressDownKeyEvent.getID())).addLast(pressDownKeyEvent);
                }
            } else {
                PressDownKeyEvent pressDownKeyEvent = (PressDownKeyEvent) gameEvent;
                playerEvents.get(keySetIDMap.get(pressDownKeyEvent.getWhichSetKeys())).addLast(pressDownKeyEvent);
            }
        }
    }

    /**
     * Naciśnięcie strzałki w lewo.
     */
    private class PressLeftAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            if (networkModule.isMoreThanOnePlayer()) {
                PressLeftKeyEvent pressLeftKeyEvent = (PressLeftKeyEvent) gameEvent;
                if (pressLeftKeyEvent.isBasicSet()) {
                    playerEvents.get(playerIDMap.get(pressLeftKeyEvent.getID())).addLast(pressLeftKeyEvent);
                }
            } else {
                PressLeftKeyEvent pressLeftKeyEvent = (PressLeftKeyEvent) gameEvent;
                playerEvents.get(keySetIDMap.get(pressLeftKeyEvent.getWhichSetKeys())).addLast(pressLeftKeyEvent);
            }
        }
    }

    /**
     * Naciśnięcie strzałki w prawo.
     */
    private class PressRightAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            if (networkModule.isMoreThanOnePlayer()) {
                PressRightKeyEvent pressRightKeyEvent = (PressRightKeyEvent) gameEvent;
                if (pressRightKeyEvent.isBasicSet()) {
                    playerEvents.get(playerIDMap.get(pressRightKeyEvent.getID())).addLast(pressRightKeyEvent);
                }
            } else {
                PressRightKeyEvent pressRightKeyEvent = (PressRightKeyEvent) gameEvent;
                playerEvents.get(keySetIDMap.get(pressRightKeyEvent.getWhichSetKeys())).addLast(pressRightKeyEvent);
            }
        }
    }

    /**
     * Naciśnięcie strzałki w góre.
     */
    private class PressUpAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            if (networkModule.isMoreThanOnePlayer()) {
                PressUpKeyEvent pressUpKeyEvent = (PressUpKeyEvent) gameEvent;
                if (pressUpKeyEvent.isBasicSet()) {
                    playerEvents.get(playerIDMap.get(pressUpKeyEvent.getID())).addLast(pressUpKeyEvent);
                }
            } else {
                PressUpKeyEvent pressUpKeyEvent = (PressUpKeyEvent) gameEvent;
                playerEvents.get(keySetIDMap.get(pressUpKeyEvent.getWhichSetKeys())).addLast(pressUpKeyEvent);
            }
        }
    }

    /**
     * Przerwanie zegarowe inforumjące o konieczności wykonania ruchu przez węże.
     */
    private class TimerAction extends GameAction {
        @Override
        void perform(final GameEvent gameEvent) {
            if (!model.inGame()) {
                return;
            }
            for (SnakeNumber snakeNumber : playerEvents.keySet()) {
                if (!(playerEvents.get(snakeNumber).isEmpty())) {
                    turningActions.get(playerEvents.get(snakeNumber).getFirst().getClass()).perform(snakeNumber);
                    playerEvents.get(snakeNumber).clear();
                }
            }
            model.moveSnakes();
            sendFakeBoardAllClients();
            sendEndInformation();
            sendScoreInformation();
        }
    }

    /**
     * Abstrakcyjna klasa odpowiedzialna za zmiane kierunku.
     */
    private abstract class TurningAction {
        abstract void perform(SnakeNumber snakeNumber);
    }

    /**
     * Zmiana kierunku na wschodni.
     */
    private class GoEast extends TurningAction {
        @Override
        void perform(final SnakeNumber snakeNumber) {
            model.goEast(snakeNumber);
        }
    }

    /**
     * Zmiana kierunku na północny.
     */
    private class GoNorth extends TurningAction {
        @Override
        void perform(final SnakeNumber snakeNumber) {
            model.goNorth(snakeNumber);
        }
    }

    /**
     * Zmiana kierunku na południowy.
     */
    private class GoSouth extends TurningAction {
        @Override
        void perform(final SnakeNumber snakeNumber) {
            model.goSouth(snakeNumber);
        }
    }

    /**
     * Zmiana kierunku na zachodni.
     */
    private class GoWest extends TurningAction {
        @Override
        void perform(final SnakeNumber snakeNumber) {
            model.goWest(snakeNumber);
        }
    }
}
