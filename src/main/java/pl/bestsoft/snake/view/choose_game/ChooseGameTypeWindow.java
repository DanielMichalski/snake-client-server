package pl.bestsoft.snake.view.choose_game;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.bestsoft.snake.controler.Controler;
import pl.bestsoft.snake.log.AutowiredLogger;
import pl.bestsoft.snake.model.events.GameEvent;
import pl.bestsoft.snake.model.model.Model;
import pl.bestsoft.snake.spring.ContextProvider;
import pl.bestsoft.snake.util.Const;
import pl.bestsoft.snake.util.ImageLoader;
import pl.bestsoft.snake.util.KeymapUtil;
import pl.bestsoft.snake.view.choose_clients.NumberOfClientsFrame;
import pl.bestsoft.snake.view.choose_ip.GetIPNumberWindow;
import pl.bestsoft.snake.view.main_frame.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Wyświetla okno z moliwością wyboru rodzaju gry.
 */
@Component
public class ChooseGameTypeWindow extends JFrame {

    @AutowiredLogger
    private Logger logger;

    private static final int BUTTON_WIDTH = 275;

    private static final int BUTTON_HEIGHT = 78;

    @Value("${ChooseGameTypeWindow.gameType}")
    private String frameTitle;

    @Value("${ChooseGameTypeWindow.OnePlayer}")
    private String player1Text;

    @Value("${ChooseGameTypeWindow.TwoPlayers}")
    private String player2Text;

    @Value("${ChooseGameTypeWindow.ThreePlayers}")
    private String player3Text;

    @Value("${ChooseGameTypeWindow.MakeServer}")
    private String createServerText;

    @Value("${ChooseGameTypeWindow.JoinGame}")
    private String joinServerText;

    @Value("${ChooseGameTypeWindow.DefaultIP}")
    private String defaultIpNumber;

    /**
     * Kolejka blokująca eventy
     */
    private LinkedBlockingQueue<GameEvent> blockingQueue;

    /**
     * Tworzy nowe okno wyboru gry.
     */
    public void init() {
        blockingQueue = new LinkedBlockingQueue<GameEvent>();

        setupFrame();
        initializeComponents();
    }

    private void setupFrame() {
        setTitle(frameTitle);
        setSize(700, 410);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Const.Colors.BACKGROUND_COLOR);
        setUndecorated(true);
        setWindowRemoveble();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeComponents() {
        JButton player1 = createBtn("player1.png", 50, 100, player1Text, new NewGamePlayer1Action());
        JButton player2 = createBtn("player2.png", 50, 200, player2Text, new NewGamePlayer2Action());
        JButton player3 = createBtn("player3.png", 50, 300, player3Text, new NewGamePlayer3Action());

        JButton createServer = createBtn("create.png", 370, 100, createServerText, new MakeServer());
        JButton joinServer = createBtn("join.png", 370, 200, joinServerText, new JoinGame());

        JLabel titleLabel = createTitleLabel();
        JLabel closeBtn = createCloseBtn();

        add(player1);
        add(player2);
        add(player3);
        add(createServer);
        add(joinServer);
        add(titleLabel);
        add(closeBtn);
    }

    private JLabel createCloseBtn() {
        final ImageIcon imgUn = new ImageIcon(ImageLoader.load("close_btn.png"));
        final JLabel button = new JLabel(imgUn);
        button.addMouseListener(new CloseBtnAction());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBounds(655, 5, 40, 40);
        return button;
    }

    private JLabel createTitleLabel() {
        final JLabel button = new JLabel("Snake 2D");
        button.setForeground(Const.Colors.LABEL_COLOR);
        button.setFont(new Font("Ravie", Font.PLAIN, 40));
        button.setBounds(240, 20, 300, 40);
        return button;
    }

    private void setWindowRemoveble() {
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

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        Toolkit tool = Toolkit.getDefaultToolkit();

        URL url = getClass().getResource("/images/snake.png");

        Image img = tool.getImage(url);
        g.drawImage(img, 460, 290, this);
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
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
            ApplicationContext context = ContextProvider.getSpringContext();
            GetIPNumberWindow frame = context.getBean("getIPNumberWindow", GetIPNumberWindow.class);
            hideWindow();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.display();
        }
    }

    /**
     * Utworzenia okienka do wyboru liczby klientów na serwerze.
     */
    private class MakeServer implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            ApplicationContext springContext = ContextProvider.getSpringContext();
            NumberOfClientsFrame frame =
                    springContext.getBean("numberOfClientsFrame", NumberOfClientsFrame.class);
            frame.setBlockingQueue(blockingQueue);
            frame.display();
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

    private class CloseBtnAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.exit(0);
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
            KeymapUtil.showDefaultKeymap();
            Model model = new Model();
            Controler controler = new Controler(model, blockingQueue, 1, howManySnakes, true);
            View view = new View(true);
            view.display(defaultIpNumber);
            controler.begin();
        }
    }
}
