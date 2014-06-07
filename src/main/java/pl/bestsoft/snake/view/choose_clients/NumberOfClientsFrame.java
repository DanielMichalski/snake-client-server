package pl.bestsoft.snake.view.choose_clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.bestsoft.snake.controler.Controler;
import pl.bestsoft.snake.model.events.GameEvent;
import pl.bestsoft.snake.model.model.Model;
import pl.bestsoft.snake.util.Const;
import pl.bestsoft.snake.util.ImageLoader;
import pl.bestsoft.snake.view.choose_game.ChooseGameTypeWindow;
import pl.bestsoft.snake.view.main_frame.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Okno umożliwiające wybór iloóci graczy na serwerze.
 */
@Component
public class NumberOfClientsFrame extends JFrame {

    private static final int BUTTON_WIDTH = 275;

    private static final int BUTTON_HEIGHT = 78;

    @Value("${ChooseGameTypeWindow.0}")
    private String frameTitle;

    @Value("${ChooseGameTypeWindow.2}")
    private String player2Text;

    @Value("${ChooseGameTypeWindow.3}")
    private String player3Text;

    @Value("${ChooseGameTypeWindow.4}")
    private String player4Text;

    @Value("${ChooseNumberOfClientsWindow.5}")
    private String defaultIPNumber;

    /**
     * Tworzy okno do wyboru ilości graczy.
     */
    private LinkedBlockingQueue<GameEvent> blockingQueue;

    public void init() {
        setupFrame();
        initializeComponents();
    }

    private void setupFrame() {
        setTitle(frameTitle);
        setSize(340, 380);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Const.Colors.BACKGROUND_COLOR);
        setUndecorated(true);
        setWindowMoveble();
    }

    private void initializeComponents() {
        JButton player2 = createBtn("player2.png", 30, 70, player2Text, new TwoClientsAction());
        JButton player3 = createBtn("player3.png", 30, 170, player3Text, new ThreeClientsAction());
        JButton player4 = createBtn("player3.png", 30, 270, player4Text, new FourClientsAction());
        JLabel titleLabel = createTitleLabel();
        JLabel closeBtn = createCloseBtn();

        add(player2);
        add(player3);
        add(player4);
        add(titleLabel);
        add(closeBtn);
    }

    private JButton createBtn(String imageTitle, int x, int y, String label, ActionListener listener) {
        JButton btn = new JButton(label);
        btn.addActionListener(listener);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setFocusable(false);
        btn.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setForeground(Color.darkGray);
        Icon icon = new ImageIcon(getClass().getResource("/images/" + imageTitle));
        btn.setIcon(icon);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JLabel createCloseBtn() {
        final ImageIcon imgUn = new ImageIcon(ImageLoader.load("close_btn.png"));
        final JLabel button = new JLabel(imgUn);
        button.addMouseListener(new CloseBtnAction());
        button.setBounds(295, 5, 40, 40);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JLabel createTitleLabel() {
        final JLabel button = new JLabel("Liczba graczy");
        button.setForeground(Const.Colors.LABEL_COLOR);
        button.setFont(new Font("Ravie", Font.PLAIN, 22));
        button.setBounds(50, 10, 240, 40);
        return button;
    }

    private void setWindowMoveble() {
        final Point point = new Point();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!e.isMetaDown()) {
                    point.x = e.getX();
                    point.y = e.getY();
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!e.isMetaDown()) {
                    Point p = getLocation();
                    setLocation(p.x + e.getX() - point.x,
                            p.y + e.getY() - point.y);
                }
            }
        });
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

    public void setBlockingQueue(LinkedBlockingQueue<GameEvent> blockingQueue) {
        this.blockingQueue = blockingQueue;
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

    private class CloseBtnAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dispose();
                    ChooseGameTypeWindow frame = new ChooseGameTypeWindow();
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
            });
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
            Controler controler = new Controler(model, blockingQueue, howManyClients, howManyClients, true);
            View view = new View(false);
            view.display(defaultIPNumber);
            controler.begin();
        }
    }
}
