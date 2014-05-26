package pl.bestsoft.snake.network;

import pl.bestsoft.snake.model.events.PlayerEvent;
import pl.bestsoft.snake.model.messages.BoardMessage;
import pl.bestsoft.snake.model.messages.GameMessage;
import pl.bestsoft.snake.model.messages.InfoMessage;
import pl.bestsoft.snake.model.messages.ScoreMessage;
import pl.bestsoft.snake.view.main_frame.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * Klasa odpowiedzialna za komunikację z serwerem.
 */
public class ClientNetwork {
    /**
     * Widok klienta.
     */
    final private View view;
    /**
     * Socket z którym komunikuje się klient.
     */
    private Socket clientSocket;
    /**
     * Strumeń obiektów wyjściowych do serwera.
     */
    private ObjectOutputStream objectOutputStream;

    public ClientNetwork(final View view) {
        this.view = view;
    }

    /**
     * Nawiązanie połączenia z serwerem.
     *
     * @param IpNumber numer IP serwera
     */
    public void conectToServer(final String IpNumber) {
        try {
            clientSocket = new Socket(IpNumber, 5555);
            objectOutputStream = new ObjectOutputStream(
                    clientSocket.getOutputStream());
            Thread t = new Thread(new InputReader(clientSocket));
            t.start();

        } catch (Exception e) {
            view.showInfoMessage(new InfoMessage("Nie mozna polaczyc z serwerem"));
        }
    }

    /**
     * Wysyła informacje o zdarzeniu do serwera.
     *
     * @param playerEvent
     */
    public void sendEvent(final PlayerEvent playerEvent) {
        if (objectOutputStream == null) {
            view.showInfoMessage(new InfoMessage("Brak połączenia z serwerem"));
            return;
        }
        try {
            objectOutputStream.writeObject(playerEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Nasłuchiwacz obiektów z serwera.
     */
    private class InputReader implements Runnable {
        /**
         * Strumień danych wejściowych z serwera.
         */
        private ObjectInputStream objectInputStream;
        /**
         * Mapa akcji podejmowanych zgodnie z nadchodzącymi obiektami message.
         */
        private final HashMap<Class<? extends GameMessage>, GameAction> actions;

        public InputReader(final Socket socket) {
            actions = new HashMap<Class<? extends GameMessage>, ClientNetwork.InputReader.GameAction>();
            try {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            actions.put(BoardMessage.class, new FakeAction());
            actions.put(InfoMessage.class, new InformationAction());
            actions.put(ScoreMessage.class, new ActScore());
        }

        /**
         * Obsluga nadchodzących informacji z serwera.
         */
        @Override
        public void run() {
            while (true) {
                try {
                    GameMessage gameMessage = (GameMessage) objectInputStream.readObject();
                    actions.get(gameMessage.getClass()).perform(gameMessage);
                } catch (IOException e) {
                    view.showInfoMessage(new InfoMessage("Utracono polaczenie z serwerem"));
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                objectOutputStream.close();
                objectInputStream.close();
                objectInputStream = null;
                objectOutputStream = null;
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /**
         * Abstrakcyjna klasa odpowiedzialna za wykonywanie akcji.
         */
        private abstract class GameAction {
            abstract void perform(GameMessage gameMessage);
        }

        /**
         * Aktualizacja wyników graczy.
         */
        private class ActScore extends GameAction {
            @Override
            void perform(final GameMessage gameMessage) {
                view.actScores((ScoreMessage) gameMessage);
            }
        }

        /**
         * Uaktualnienie widoku głównej planszy.
         */
        private class FakeAction extends GameAction {
            @Override
            void perform(final GameMessage gameMessage) {
                view.updateBoard(gameMessage);
            }
        }

        /**
         * Wyświetlenie okna z informacją.
         */
        private class InformationAction extends GameAction {
            @Override
            void perform(final GameMessage gameMessage) {
                view.showInfoMessage((InfoMessage) gameMessage);
            }
        }
    }
}
