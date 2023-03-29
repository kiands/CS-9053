// 基于这个进行修改即可，不进行clear，但是可以拓展发送信息的模块

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingSquare extends JFrame {
    private static final long serialVersionUID = 1L;
    private static boolean square_status = false;
    private static boolean listened_status = false;
    private static int tick_rate = 4;
    private JTextField inputTickRate;
    private JButton startButton;
    private JButton clearButton;
    private static JPanel panel;
    // The toggle for threads.
    private static boolean running;
    // Indicator for restart.
    private static boolean again = false;

    /*
     * This is the constructor.
     */
    public SwingSquare() {
        setTitle("Swing Square");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Text field for inputing tick rate.
        inputTickRate = new JTextField("4", 5);
        // Buttons.
        startButton = new JButton("Start");
        startButton.addActionListener(new StartButtonListener());
        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearButtonListener());
        
        // Create panel.
        panel = new JPanel() {
            private static final long serialVersionUID = 1L;
            private int width = 100;
            private int height = 200;
            private int x_position = 0;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (x_position < 299) {
                	if (listened_status) {
                        height = 100;
                        x_position = x_position + 100/tick_rate;
                    } else {
                        height = 200;
                        x_position = x_position + 100/tick_rate;
                    }
                    g.drawRect(x_position, 150, width, height);
                } else {
                	if (again == false) {
                		g.drawRect(x_position, 150, width, height);
                	} else {
                		/*
                		 * To achieve a correct clear and restart, ClearButtonListener will trigger panel.repaint().
                		 * This will call g.drawRect() again so we need an indicator to distinct restart situation.
                		 * When the restart is finished, we should recover the indicator.
                		 */
                		g.drawRect(0, 150, width, height);
                		x_position = 0;
                		again = false;
                	}
                }
            }
        };

        add(inputTickRate, BorderLayout.NORTH);
        add(startButton, BorderLayout.WEST);
        add(clearButton, BorderLayout.EAST);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    /*
     * This listener is responsible for triggering the following three threads.
     */
    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                tick_rate = Integer.parseInt(inputTickRate.getText());
            } catch (NumberFormatException ex) {
                tick_rate = 4;
            }
            // Switch the toggle for threads to let them run.
            running = true;
            // A kind of notation.
            SquareStatusListener sqrListener = new SquareStatusListener();
            sqrListener.start();
            // Another kind of notation.
            new Thread(new SquareStatusReader()).start();
            new Thread(new SquareDrawer()).start();
            startButton.setEnabled(false);
        }
    }

    /*
     * This listener is responsible for recovering initial status.
     */
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tick_rate = 4;
            startButton.setEnabled(true);
            running = false;
            // System.out.println("restart triggered");
            again = true;
            // This will collaborate with g.drawRect().
            panel.repaint();
        }
    }

    /*
     * This class defines the way this program respond to keyboard events and how it changes square_status
     */
    private static class SquareStatusListener extends Thread {
        @Override
        public void run() {
    		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
                @Override
                public boolean dispatchKeyEvent(KeyEvent e) {
                    if (running && e.getKeyCode() == KeyEvent.VK_CONTROL) {
                        if (e.getID() == KeyEvent.KEY_PRESSED) {
                            square_status = true;
                        } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                            square_status = false;
                        }
                        System.out.println(square_status);
                    }
                    return false;
                }
            });
        }
    }

    /*
     * This class controls the sample rate to read square_status.
     */
    private static class SquareStatusReader implements Runnable {
        @Override
        public void run() {
            while (true) {
                listened_status = square_status;
                try {
                    Thread.sleep(1000 / tick_rate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * This class controls the the fresh rate of the rectangle.
     */
    private static class SquareDrawer extends Thread {
        @Override
        public void run() {
            while (running) {
                panel.repaint();
                try {
                    Thread.sleep(1000 / tick_rate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new SwingSquare();
    }
}
