package pl.bestsoft.snake.view.choose_clients;

import pl.bestsoft.snake.controler.Controler;
import pl.bestsoft.snake.dao.TextsDao;
import pl.bestsoft.snake.events.GameEvent;
import pl.bestsoft.snake.model.Model;
import pl.bestsoft.snake.view.main_frame.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Okno umożliwiające wybór iloóci graczy na serwerze.
 */
public class NumberOfClientsFrame extends JFrame {

    /**
     * Tworzy okno do wyboru ilości graczy.
     */
    private static final long serialVersionUID = 1L;
    private final LinkedBlockingQueue<GameEvent> blockingQueue;

    public NumberOfClientsFrame(final LinkedBlockingQueue<GameEvent> blockingQueue) {
        super(TextsDao.getText("ChooseNumberOfClientsWindow.0"));
        setSize(200, 200);
        this.blockingQueue = blockingQueue;
        setLayout(new GridLayout(4, 1));
        add(new JLabel(TextsDao.getText("ChooseNumberOfClientsWindow.1"), SwingConstants.CENTER));
        JButton twoClients = new JButton(TextsDao.getText("ChooseNumberOfClientsWindow.2"));
        twoClients.addActionListener(new TwoClientsAction());
        JButton threeClients = new JButton(TextsDao.getText("ChooseNumberOfClientsWindow.3"));
        threeClients.addActionListener(new ThreeClientsAction());
        JButton fourClients = new JButton(TextsDao.getText("ChooseNumberOfClientsWindow.4"));
        fourClients.addActionListener(new FourClientsAction());
        add(twoClients);
        add(threeClients);
        add(fourClients);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
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
     * Ukrywa okno.
     */
    public void hideWindow() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                setVisible(false);
            }
        });
    }

    /**
     * Listener przycisku dla dwóch graczy.
     */
    private class TwoClientsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            hideWindow();
            new MakeServer(2).start();
        }
    }

    /**
     * Listener przycisku dla trzech graczy.
     */
    private class ThreeClientsAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            hideWindow();
            new MakeServer(3).start();
        }

    }

    /**
     * Listener pzycisku dla cztere
     */
    private class FourClientsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            hideWindow();
            new MakeServer(4).start();
        }
    }

    /**
     * Klasa odpowiedzialna za utworzenie serwera z wybrana liczbą klientów.
     */
    private class MakeServer extends Thread {
        /**
         * Liczba klientów na serwerze.
         */
        private final int howManyClients;

        MakeServer(final int howManyClients) {
            this.howManyClients = howManyClients;
        }

        /**
         * Uruchamia wątek serwera oraz tworzy jednego klienta.
         */
        @Override
        public void run() {
            Model model = new Model();
            Controler controler = new Controler(model, blockingQueue, howManyClients, howManyClients);
            View view = new View();
            view.display(TextsDao.getText("ChooseNumberOfClientsWindow.5"));
            controler.begin();
        }
    }
}
