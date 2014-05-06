package pl.bestsoft.snake.view.main_frame;

import pl.bestsoft.snake.message.BoardMessage;
import pl.bestsoft.snake.message.GameMessage;
import pl.bestsoft.snake.message.InfoMessage;
import pl.bestsoft.snake.message.ScoreMessage;
import pl.bestsoft.snake.view.ClientNetwork;

import javax.swing.*;

/**
 * Buduje GUI gry.
 */
public class View {
    /**
     * Ramka w któórej się umieszczane elementy.
     */
    private MainFrame mainFrame;
    /**
     * Główna plansza na której pełzają węże.
     */
    private MainBoard mainBoard;
    /**
     * Panel z wynikami graczy.
     */
    private ScorePanel scoreFrame;
    /**
     * Moduł sieciowy który obsługuje połączenie z serwerem.
     */
    private final ClientNetwork clientNetwork;

    public View() {
        clientNetwork = new ClientNetwork(this);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                scoreFrame = new ScorePanel();
                mainFrame = new MainFrame(clientNetwork);
                mainBoard = new MainBoard();






                mainFrame.add(mainBoard);
                mainFrame.add(scoreFrame);
                mainFrame.setLocationRelativeTo(null);
            }
        });
    }

    /**
     * Aktualizuje wyniki graczy.
     *
     * @param scoreMessage
     */
    public void actScores(final ScoreMessage scoreMessage) {

        scoreFrame.actScore(scoreMessage);
    }

    /**
     * Nazwiązuje połączenie z serwerem oraz wyświetla ramke GUI.
     *
     * @param IPNumber numer IP serwera
     */
    public void display(final String IPNumber) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                mainFrame.setVisible(true);
            }
        });
        clientNetwork.conectToServer(IPNumber);
    }

    /**
     * Wyświetla informacje na ekranie.
     *
     * @param infoMessage
     */
    public void showInfoMessage(final InfoMessage infoMessage) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JOptionPane.showMessageDialog(mainFrame,
                        infoMessage.getMessage());
            }
        });
    }

    /**
     * Uaktualnia widok planszy na której poruszają się węże
     *
     * @param message informacje o fakeMapie oraz id węża
     */
    public void updateBoard(final GameMessage message) {
        final BoardMessage boradMessage = (BoardMessage) message;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                mainBoard.setFake(boradMessage.getFakeMap());
                mainBoard.repaint();
            }
        });
    }
}