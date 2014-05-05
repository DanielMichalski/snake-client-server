package pl.bestsoft.snake.view.choose_game;

import pl.bestsoft.snake.controler.Controler;
import pl.bestsoft.snake.dao.TextsDao;
import pl.bestsoft.snake.events.GameEvent;
import pl.bestsoft.snake.model.Model;
import pl.bestsoft.snake.view.choose_clients.NumberOfClientsFrame;
import pl.bestsoft.snake.view.choose_ip.GetIPNumberWindow;
import pl.bestsoft.snake.view.main_frame.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Wyświetla okno z moliwością wyboru rodzaju gry.
 */
public class ChooseGameTypeWindow extends JFrame {
    private static final int BUTTON_WIDTH = 260;
    private static final int BUTTON_HEIGHT = 78;

    /**
     * Kolejka blokująca eventy
     */
    private final LinkedBlockingQueue<GameEvent> blockingQueue;

    /**
     * Tworzy nowe okno wyboru gry.
     */
    public ChooseGameTypeWindow() {
        blockingQueue = new LinkedBlockingQueue<GameEvent>();

        setupFrame();
        initializeComponents();
    }

    private void setupFrame() {
        setTitle(TextsDao.getText("ChooseGameTypeWindow.0"));
        setSize(560, 350);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        JButton player1 = createBtn("player1.png", 10, 10, "1 Gracz", new NewGamePlayer1Action());
        JButton player2 = createBtn("player2.png", 10, 120, "2 Graczy", new NewGamePlayer2Action());
        JButton player3 = createBtn("player3.png", 10, 230, "3 Graczy", new NewGamePlayer3Action());

        JButton createServer = createBtn("create.png", 280, 10, "Utworz gre", new MakeServer());
        JButton joinServer = createBtn("join.png", 280, 120, "Dolacz do gry", new JoinGame());

        add(player1);
        add(player2);
        add(player3);
        add(createServer);
        add(joinServer);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        Toolkit tool = Toolkit.getDefaultToolkit();

        URL url = getClass().getResource("/images/snake.png");

        Image img = tool.getImage(url);
        g.drawImage(img, 360, 240, this);
    }

    private JButton createBtn(String imageTitle, int x, int y, String label, ActionListener listener) {
        JButton btn = new JButton(label);
        btn.addActionListener(listener);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusable(false);
        btn.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setForeground(Color.darkGray);
        Icon icon = new ImageIcon(getClass().getResource("/images/" + imageTitle));
        btn.setIcon(icon);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        return btn;
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
            NumberOfClientsFrame chooseNumberOfClientsWindow = new NumberOfClientsFrame(blockingQueue);
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
            view.display(TextsDao.getText("ChooseGameTypeWindow.7"));
            controler.begin();
        }
    }
}
