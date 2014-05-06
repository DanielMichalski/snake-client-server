package pl.bestsoft.snake.view.main_frame;

import pl.bestsoft.snake.events.*;
import pl.bestsoft.snake.snake.KeySetID;
import pl.bestsoft.snake.util.Const;
import pl.bestsoft.snake.util.ImageLoader;
import pl.bestsoft.snake.view.ClientNetwork;
import pl.bestsoft.snake.view.choose_game.ChooseGameTypeWindow;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;

/**
 * Ramka w której są wyświetlane poszczególne elementy składowe GUI.
 */
public class MainFrame extends JFrame {

    /**
     * Moduł sieciowy obsługujący poczenie z serwerem.
     */
    private final ClientNetwork clientNetwork;

    public MainFrame(final ClientNetwork clientNetwork) {
        this.clientNetwork = clientNetwork;

        setupFrame();
        initializeComponents();
    }

    private void setupFrame() {
        setSize(520, 550);
        setResizable(false);
        setUndecorated(true);
        addKeyListener(new BoardKeyListener());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setUndecorated(true);
        getContentPane().setBackground(Const.Colors.BACKGROUND_COLOR);
        setWindowMoveble();
    }

    private void initializeComponents() {
        JLabel closeBtn = createCloseBtn();
        add(closeBtn);
    }

    private JLabel createCloseBtn() {
        final ImageIcon imgUn = new ImageIcon(ImageLoader.load("close_btn.png"));
        final JLabel button = new JLabel(imgUn);
        button.addMouseListener(new CloseBtnAction());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBounds(475, 5, 40, 40);
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
     * Nasuchiwacz klawiszy. Wysya do serewera informacje o naciniciu klawiszy przez gracza.
     */
    private class BoardKeyListener implements KeyListener {
        @Override
        public void keyPressed(final KeyEvent arg0) {
            if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
                clientNetwork.sendEvent(new PressLeftKeyEvent(new KeySetID(1)));
            }
            if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
                clientNetwork.sendEvent(new PressRightKeyEvent(new KeySetID(1)));
            }
            if (arg0.getKeyCode() == KeyEvent.VK_UP) {
                clientNetwork.sendEvent(new PressUpKeyEvent(new KeySetID(1)));
            }
            if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
                clientNetwork.sendEvent(new PressDownKeyEvent(new KeySetID(1)));
            }
            if (arg0.getKeyCode() == KeyEvent.VK_W) {
                clientNetwork.sendEvent(new PressUpKeyEvent(new KeySetID(2)));
            }
            if (arg0.getKeyCode() == KeyEvent.VK_S) {
                clientNetwork.sendEvent(new PressDownKeyEvent(new KeySetID(2)));
            }
            if (arg0.getKeyCode() == KeyEvent.VK_D) {
                clientNetwork.sendEvent(new PressRightKeyEvent(new KeySetID(2)));
            }
            if (arg0.getKeyCode() == KeyEvent.VK_A) {
                clientNetwork.sendEvent(new PressLeftKeyEvent(new KeySetID(2)));
            }
            if (arg0.getKeyCode() == KeyEvent.VK_H) {
                clientNetwork.sendEvent(new PressUpKeyEvent(new KeySetID(3)));
            }
            if (arg0.getKeyCode() == KeyEvent.VK_N) {
                clientNetwork.sendEvent(new PressDownKeyEvent(new KeySetID(3)));
            }
            if (arg0.getKeyCode() == KeyEvent.VK_M) {
                clientNetwork.sendEvent(new PressRightKeyEvent(new KeySetID(3)));
            }
            if (arg0.getKeyCode() == KeyEvent.VK_B) {
                clientNetwork.sendEvent(new PressLeftKeyEvent(new KeySetID(3)));
            }
        }

        @Override
        public void keyReleased(final KeyEvent arg0) {
        }

        @Override
        public void keyTyped(final KeyEvent arg0) {
        }

    }

    /**
     * Listener przycisku nowa gra. Wysya informacje do serwera o tym e gracz nacisn przycisk.
     */
    private class NewGameAction implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent arg0) {
            clientNetwork.sendEvent(new NewGameEvent());
        }
    }
}
