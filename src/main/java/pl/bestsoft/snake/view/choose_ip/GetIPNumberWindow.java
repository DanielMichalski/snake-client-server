package pl.bestsoft.snake.view.choose_ip;

import pl.bestsoft.snake.dao.TextsDao;
import pl.bestsoft.snake.view.main_frame.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Okienko które umożliwia podanie IP serwera.
 */
public class GetIPNumberWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    /**
     * Numer IP serwera.
     */
    private String ipNumber;
    /**
     * Pole w którym można podać numer IP serwera.
     */
    private JTextField ipNumberField;

    /**
     * Tworzy nowe okno do wpisania numeru IP serwera.
     */
    public GetIPNumberWindow() {
        setupFrame();
        initializeComponents();
    }

    private void setupFrame() {
        setTitle(TextsDao.getText("GetIPNumberWindow.0"));
        setSize(250, 150);
        setLayout(new GridLayout(3, 1));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initializeComponents() {
        JLabel information = new JLabel(TextsDao.getText("GetIPNumberWindow.1"));
        add(information);
        ipNumberField = new JTextField(15);
        ipNumberField.setText(TextsDao.getText("GetIPNumberWindow.2"));
        add(ipNumberField);
        JButton okKey = new JButton(TextsDao.getText("GetIPNumberWindow.3"));
        okKey.addActionListener(new OkKeyLinstener());
        add(okKey);
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
    private void hideWindow() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                setVisible(false);
            }
        });
    }

    /**
     * Listener dla przycisku OK w oknie.
     */
    private class OkKeyLinstener implements ActionListener {
        @Override
        /**
         * Utworzenie nowego widoku dla gracza i podanie mu numeru IP
         */
        public void actionPerformed(final ActionEvent e) {
            ipNumber = ipNumberField.getText();
            hideWindow();
            NewView newView = new NewView(ipNumber);
            newView.start();
        }
    }

    private class NewView extends Thread {
        private final String ipNumber;

        NewView(String ipNumber) {
            this.ipNumber = ipNumber;
        }

        @Override
        public void run() {
            View view = new View();
            view.display(ipNumber);
        }
    }
}
