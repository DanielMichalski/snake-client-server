package pl.bestsoft.snake.network;

import pl.bestsoft.snake.controler.Timer;
import pl.bestsoft.snake.model.events.EndGameEvent;
import pl.bestsoft.snake.model.events.GameEvent;
import pl.bestsoft.snake.model.events.NewGameEvent;
import pl.bestsoft.snake.model.events.PlayerEvent;
import pl.bestsoft.snake.model.fakes.*;
import pl.bestsoft.snake.model.messages.BoardMessage;
import pl.bestsoft.snake.model.messages.GameMessage;
import pl.bestsoft.snake.model.messages.InfoMessage;
import pl.bestsoft.snake.model.messages.ScoreMessage;
import pl.bestsoft.snake.model.model.SnakeNumber;
import pl.bestsoft.snake.snake.PlayerID;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Moduł sieciowy umożliwia komunikację z klientami
 */
public class NetworkModule {
    /**
     * Kolejka blokująca do której moduł sieciowy wrzuca zdarzenia.
     */
    private final BlockingQueue<GameEvent> blockingQueue;

    /**
     * Mapa zawierająca sockety podłączonych klientów.
     */
    private final ConcurrentMap<PlayerID, Socket> sockets = new ConcurrentHashMap<PlayerID, Socket>();

    /**
     * Mapa zawierająca objectOutputStreamy podłączonych klientów.
     */
    private final ConcurrentMap<PlayerID, ObjectOutputStream> objectOutputStreams = new ConcurrentHashMap<PlayerID, ObjectOutputStream>();

    /**
     * Maksymalna liczba klientów podłączonych do serwera.
     */
    private int numberOfClients;

    /**
     * Ostatnio wysłana Fake Mapa.
     */
    private HashMap<FakePoint, GameFake> prevFakeMap = null;

    /**
     * Ostatnio wysłana mapa wyników.
     */
    private Map<SnakeNumber, ScoreFake> prevScoreMap = null;

    /**
     * Tworzy moduł sieciowy.
     *
     * @param blockingQueue kolejka blokująca do której są wrzucane eventy przez moduł sieciowy.
     */
    public NetworkModule(final BlockingQueue<GameEvent> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    /**
     * Uruchamia wątek serwera.
     *
     * @param howManyClients liczba klientów podłączonych do serwera.
     */
    public void begin(final int howManyClients) {
        Server server = new Server(howManyClients);
        Thread t = new Thread(server);
        t.start();
    }

    /**
     * Wysyła wszystkim graczom zmiany jakie zaszły na planszy.
     *
     * @param fakeMap nowa fake mapa planszy porównywana jest z mapą wysłaną wcześniej.
     */
    public void sendAllPlayersFakeMap(final FakeMap fakeMap) {
        HashMap<FakePoint, GameFake> newFakeMap = fakeMap.getFakeMap();
        if (newFakeMap.isEmpty()) {
            return;
        }
        if (prevFakeMap == null) {
            prevFakeMap = newFakeMap;
            sendAllPlayersMessage(new BoardMessage(fakeMap));
            return;
        }
        HashMap<FakePoint, GameFake> changes = new HashMap<FakePoint, GameFake>();
        for (FakePoint fakePoint : prevFakeMap.keySet()) {
            if ((!(prevFakeMap.get(fakePoint).getClass().equals(newFakeMap.get(fakePoint).getClass())))) {
                changes.put(fakePoint, newFakeMap.get(fakePoint));
            }
            if (newFakeMap.get(fakePoint).getClass().equals(BodyFake.class) && (!prevFakeMap.get(fakePoint).equals(newFakeMap.get(fakePoint)))) {
                changes.put(fakePoint, newFakeMap.get(fakePoint));
            }
        }
        prevFakeMap = newFakeMap;
        sendAllPlayersMessage(new BoardMessage(new FakeMap(changes)));
    }

    /**
     * Wysyła informacje do wszystkich graczy.
     *
     * @param gameMessage
     */
    public void sendAllPlayersMessage(final GameMessage gameMessage) {
        for (ObjectOutputStream objectOutputStream : objectOutputStreams.values()) {
            try {
                objectOutputStream.writeObject(gameMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Wysyła informacje wybranemu klientowi.
     *
     * @param gameMessage informacja do gracza
     * @param whichClient id klienta
     */
    void sendPlayerMessage(final GameMessage gameMessage, final PlayerID whichClient) {
        try {
            if (objectOutputStreams.containsKey(whichClient)) {
                objectOutputStreams.get(whichClient).writeObject(gameMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Wysyła wszystkim graczom informacje o wynikach.
     * Nowa mapa wyników prównywana jest z mapą wysłaną wcześniej.
     *
     * @param scoreMessage
     */
    public void sendAllPlayersScore(ScoreMessage scoreMessage) {
        if (prevScoreMap == null) {
            prevScoreMap = scoreMessage.getScoreFakeMap().getScoreMap();
            sendAllPlayersMessage(scoreMessage);
            return;
        }
        Map<SnakeNumber, ScoreFake> newScore = scoreMessage.getScoreFakeMap().getScoreMap();
        Map<SnakeNumber, ScoreFake> changesScore = new HashMap<SnakeNumber, ScoreFake>();
        for (SnakeNumber snakeNumber : newScore.keySet()) {
            if (!(prevScoreMap.get(snakeNumber).equals(newScore.get(snakeNumber)))) {
                changesScore.put(snakeNumber, newScore.get(snakeNumber));
            }
        }
        prevScoreMap = newScore;
        if (!changesScore.isEmpty()) {
            sendAllPlayersMessage(new ScoreMessage(new ScoreFakeMap(changesScore)));
        }
    }

    /**
     * Zwraca liczbe aktualnie podłączonych klientów.
     *
     * @return liczba aktualnie podłączonych klientów do serwera.
     */
    int howManyPlayerAct() {
        return objectOutputStreams.size();
    }

    /**
     * Informuje o tym czy podłączony jest więcej niż jeden klient
     */
    public boolean isMoreThanOnePlayer() {
        return (getNumberOfClients() > 1);
    }

    /**
     * Informuje o tym czy wszyscy klienci są podłączeni do serwera.
     */
    public boolean allPlayersAreConected() {
        return getNumberOfClients() == objectOutputStreams.size();
    }

    /**
     * Zwraca maksymalną liczbę klientów podłączonych do serwera.
     *
     * @return
     */
    private synchronized int getNumberOfClients() {
        return numberOfClients;
    }

    /**
     * Ustawia maksymalną liczbę klientów podłączonych do serwera.
     *
     * @param numberOfClients
     */
    private synchronized void setNumberOfClients(final int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    /**
     * Odpowiada za komunikację serwera z klientami.
     */
    private class PlayerConected implements Runnable {
        /**
         * ID klienta
         */
        private final PlayerID playerID;
        /**
         * Socket klienta
         */
        private final Socket socket;

        PlayerConected(final PlayerID playerID, final Socket socket) {
            this.playerID = playerID;
            this.socket = socket;
        }

        /**
         * Oczekuje na informacje od klientów
         */
        @Override
        public void run() {
            ObjectInputStream objectInputStream;
            try {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            while (sockets.containsValue(socket)) {
                PlayerEvent playerEvent;
                try {
                    playerEvent = (PlayerEvent) objectInputStream.readObject();
                    playerEvent.setID(playerID);
                    blockingQueue.add(playerEvent);
                } catch (IOException e) {
                    objectOutputStreams.remove(playerID);
                    sockets.remove(playerID);
                    sendAllPlayersMessage(new InfoMessage("Gracz " + playerID.getPlayerID() + " zakonczył gre "));
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                blockingQueue.put(new EndGameEvent());
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Serwer gry Snake
     */
    private class Server implements Runnable {
        /**
         * Socket serwera
         */
        private ServerSocket serverSocket;

        Server(final int howManyPlayers) {
            setNumberOfClients(howManyPlayers);
        }

        /**
         * Nasluchuje na porcie 5555 po czym gdy wszyscy gracze się podłączą uruchamia gre.
         */
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(5555);
            } catch (Exception e) {
                showErrorMessageAndExit();
            }
            for (int i = 1; i <= getNumberOfClients(); ++i) {
                Socket tmp;
                try {
                    tmp = serverSocket.accept();
                    sockets.put(new PlayerID(i), tmp);
                    objectOutputStreams.put(new PlayerID(i), new ObjectOutputStream(tmp.getOutputStream()));
                    PlayerConected newPlayer = new PlayerConected(new PlayerID(i), tmp);
                    Thread t = new Thread(newPlayer);
                    t.start();
                } catch (IOException e) {
                    showErrorMessageAndExit();
                }
            }
            Thread timer = new Thread(new Timer(blockingQueue));
            timer.start();
            try {
                blockingQueue.put(new NewGameEvent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void showErrorMessageAndExit() {
            JOptionPane.showMessageDialog(
                    null,
                    "Nie można utworzyć serwera",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(-1);
        }
    }
}
