package pl.bestsoft.snake.game;

import pl.bestsoft.snake.controler.Controler;
import pl.bestsoft.snake.events.GameEvent;
import pl.bestsoft.snake.model.Model;
import pl.bestsoft.snake.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Wyświetla okno z moliwością wyboru rodzaju gry.
 */
public class ChooseGameTypeWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    /**
     * Kolejka blokująca eventy
     */
    private final LinkedBlockingQueue<GameEvent> blockingQueue;

    /**
     * Tworzy nowe okno wyboru gry.
     */
    public ChooseGameTypeWindow() {
        super(Messages.getString("ChooseGameTypeWindow.0")); //$NON-NLS-1$
        blockingQueue = new LinkedBlockingQueue<GameEvent>();
        setSize(300, 200);
        setResizable(false);
        setLayout(new GridLayout(3, 2));
        JButton newGamePlayer1 = new JButton(Messages.getString("ChooseGameTypeWindow.1")); //$NON-NLS-1$
        newGamePlayer1.addActionListener(new NewGamePlayer1Action());
        JButton newGamePlayer2 = new JButton(Messages.getString("ChooseGameTypeWindow.2")); //$NON-NLS-1$
        newGamePlayer2.addActionListener(new NewGamePlayer2Action());
        JButton newGamePlayer3 = new JButton(Messages.getString("ChooseGameTypeWindow.3"));  //$NON-NLS-1$
        newGamePlayer3.addActionListener(new NewGamePlayer3Action());
        JButton joinGame = new JButton(Messages.getString("ChooseGameTypeWindow.4")); //$NON-NLS-1$
        joinGame.addActionListener(new JoinGame());
        JButton makeServer = new JButton(Messages.getString("ChooseGameTypeWindow.5")); //$NON-NLS-1$
        makeServer.addActionListener(new MakeServer());
        add(newGamePlayer1);
        add(newGamePlayer2);
        add(newGamePlayer3);
        add(joinGame);
        add(makeServer);
        add(new JLabel(Messages.getString("ChooseGameTypeWindow.6"), SwingConstants.CENTER)); //$NON-NLS-1$
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Wyświetla okno.
     */
    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });
    }

    /**
     * Ukrywa okno po wyborze odpowiedniego rodzaju gry.
     */
    private void hideWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(false);
            }
        });
    }

    /**
     * Utworzenie okna z możliwością wpisania numeru IP serwera.
     */
    private class JoinGame implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            GetIPNumberWindow getIPNumberWindow = new GetIPNumberWindow();
            hideWindow();
            getIPNumberWindow.display();
        }
    }

    /**
     * Utworzenia okienka do wyboru liczby klientów na serwerze.
     */
    private class MakeServer implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            ChooseNumberOfClientsWindow chooseNumberOfClientsWindow = new ChooseNumberOfClientsWindow(blockingQueue);
            chooseNumberOfClientsWindow.display();
            hideWindow();
        }
    }

    /**
     * Rozpoczęcie nowej rozgrywki 1 węże na 1 oknie.
     */
    private class NewGamePlayer1Action implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            hideWindow();
            new NewGameOneClient(1).start();
        }
    }

    /**
     * Rozpoczęcie nowej rozgrywki 2 węże na 1 okienku
     */
    private class NewGamePlayer2Action implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            hideWindow();
            new NewGameOneClient(2).start();
        }

    }

    /**
     * Rzpoczęcie nowej rozgrywki 3 węże na 1 oknie.
     */
    private class NewGamePlayer3Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            hideWindow();
            new NewGameOneClient(3).start();
        }

    }

    /**
     * Rozpoczęcie nowej gry z jednym klientem.
     */
    private class NewGameOneClient extends Thread {
        /**
         * Lczba węży biorących udział w grze.
         */
        private final int howManySnakes;

        public NewGameOneClient(final int howManySnakes) {
            this.howManySnakes = howManySnakes;
        }

        @Override
        public void run() {
            Model model = new Model();
            Controler controler = new Controler(model, blockingQueue, 1, howManySnakes);
            View view = new View();
            view.display(Messages.getString("ChooseGameTypeWindow.7")); //$NON-NLS-1$
            controler.begin();
        }
    }
}
