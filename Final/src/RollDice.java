import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RollDice extends JFrame {
    private JPanel panel;
    private int x = 50;
    private int y = 50;

    public RollDice() {
        super("Roll Dice");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillOval(x, y, 10, 10);
            }
        };
        getContentPane().add(panel);
        JButton button = new JButton("Move");
        button.addActionListener(e -> {
            x += 50;
            panel.repaint();
        });
        getContentPane().add(button, "South");
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new RollDice();
    }
}
