package pl.bestsoft.snake.view.choose_ip;

import pl.bestsoft.snake.dao.TextsDao;
import pl.bestsoft.snake.util.Const;
import pl.bestsoft.snake.util.ImageLoader;
import pl.bestsoft.snake.view.choose_game.ChooseGameTypeWindow;
import pl.bestsoft.snake.view.main_frame.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Okienko które umożliwia podanie IP serwera.
 */
public class GetIPNumberWindow extends JFrame {
    private static final long serialVersionUID = 1L;
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
        setSize(340, 200);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        getContentPane().setBackground(Const.Colors.BACKGROUND_COLOR);
        setWindowMoveble();
    }

    private void initializeComponents() {
        ipNumberField = createIpTF();
        JButton okBtn = createOkBtn();
        JLabel titleLabel = createTitleLabel();
        JLabel closeBtn = createCloseBtn();

        add(ipNumberField);
        add(okBtn);
        add(titleLabel);
        add(closeBtn);
    }

    private JTextField createIpTF() {
        JTextField ipTF = new JTextField("127.0.0.1");
        ipTF.setBounds(70, 90, 200, 25);
        ipTF.setFont(Const.Fonts.BTN_FONT);
        return ipTF;
    }

    private JButton createOkBtn() {
        JButton okBtn = new JButton("Polacz");
        okBtn.setBounds(120, 160, 100, 30);
        okBtn.setFont(Const.Fonts.BTN_FONT);
        okBtn.addActionListener(new OkKeyLinstener());
        return okBtn;
    }

    private JLabel createCloseBtn() {
        final ImageIcon imgUn = new ImageIcon(ImageLoader.load("close_btn.png"));
        final JLabel button = new JLabel(imgUn);
        button.addMouseListener(new CloseBtnAction());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBounds(300, 5, 40, 40);
        return button;
    }

    private JLabel createTitleLabel() {
        final JLabel button = new JLabel("Podaj IP serwera");
        button.setForeground(Const.Colors.LABEL_COLOR);
        button.setFont(new Font("Ravie", Font.PLAIN, 20));
        button.setBounds(20, 20, 300, 40);
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
    private void hideWindow() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                setVisible(false);
            }
        });
    }

    class CloseBtnAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dispose();
                    ChooseGameTypeWindow frame = new ChooseGameTypeWindow();
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    frame.display();
                }
            });
        }
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
            String ipNumber = ipNumberField.getText();
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
