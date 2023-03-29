package PartIV;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Random;

public class RollDice extends JFrame {
	// Label and button parameters
	JLabel label;
    JButton button;
    
    final int MIN_DICE = 1;
    final int MAX_DICE = 6;
    
    int dice1value = 1;
    int dice2value = 1;
    int cumsum = 0;
    boolean finished = false;
    
    // Dice parameters
    String dice1 = "die1.png";
    String dice2 = "die2.png";
    String dice3 = "die3.png";
    String dice4 = "die4.png";
    String dice5 = "die5.png";
    String dice6 = "die6.png";
    
    ImagePanel ip11;
    ImagePanel ip12;
    ImagePanel ip13;
    ImagePanel ip14;
    ImagePanel ip15;
    ImagePanel ip16;
    
    ImagePanel ip21;
    ImagePanel ip22;
    ImagePanel ip23;
    ImagePanel ip24;
    ImagePanel ip25;
    ImagePanel ip26;
    
    // Grid parameters
    private static final int ROWS = 5;
    private static final int COLS = 5;
    private static final int CELL_SIZE = 50;
    
    // Dot parameters
    private int dotX = 120;
    private int dotY = 320;
    private static final int DOT_SIZE = 10;
    
    // Constructor
    public RollDice() {
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 900);
        
        Container container = this.getContentPane();
        container.setLayout(null);
        
        // Initialize the instances for choosing (Dice 1)
        ip11 = new ImagePanel(dice1);
        ip12 = new ImagePanel(dice2);
        ip13 = new ImagePanel(dice3);
        ip14 = new ImagePanel(dice4);
        ip15 = new ImagePanel(dice5);
        ip16 = new ImagePanel(dice6);
        // Set the locations and sizes
        ip11.setBounds(25, 0, 200, 200);
        ip12.setBounds(25, 0, 200, 200);
        ip13.setBounds(25, 0, 200, 200);
        ip14.setBounds(25, 0, 200, 200);
        ip15.setBounds(25, 0, 200, 200);
        ip16.setBounds(25, 0, 200, 200);
        
        // Initialize the instances for choosing (Dice 2)
        ip21 = new ImagePanel(dice1);
        ip22 = new ImagePanel(dice2);
        ip23 = new ImagePanel(dice3);
        ip24 = new ImagePanel(dice4);
        ip25 = new ImagePanel(dice5);
        ip26 = new ImagePanel(dice6);
        // Set the locations and sizes
        ip21.setBounds(275, 0, 200, 200);
        ip22.setBounds(275, 0, 200, 200);
        ip23.setBounds(275, 0, 200, 200);
        ip24.setBounds(275, 0, 200, 200);
        ip25.setBounds(275, 0, 200, 200);
        ip26.setBounds(275, 0, 200, 200);
        
        // Add the dices images to container
        container.add(ip11);
        container.add(ip12);
        container.add(ip13);
        container.add(ip14);
        container.add(ip15);
        container.add(ip16);
        
        container.add(ip21);
        container.add(ip22);
        container.add(ip23);
        container.add(ip24);
        container.add(ip25);
        container.add(ip26);
        
        // Initialize the dot
        /*
         * This will not take good effect
        JPanel dpanel = new JPanel() {
        	@Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // It does not take effect
                g.setColor(Color.BLACK);
                g.fillOval(DOT_X, DOT_Y, DOT_SIZE, DOT_SIZE);
            }
        };
        dpanel.setBounds(120, 320, 10, 10);
        dpanel.setBackground(Color.BLACK);
        // dpanel.setVisible(true);
        container.add(dpanel);
        */
        JPanel dotPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillOval(dotX, dotY, DOT_SIZE, DOT_SIZE);
            }
        };
        dotPanel.setBounds(0, 0, 500, 900);
        dotPanel.setOpaque(false);
        container.add(dotPanel);
        
        // Initialize the grid
        JPanel gpanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int row = 0; row < ROWS; row++) {
                    for (int col = 0; col < COLS; col++) {
                        int x = col * CELL_SIZE;
                        int y = row * CELL_SIZE;
                        g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                    }
                }
                // display"finished"
                if (finished == true) {
                	g.drawString("Finished!", 100, 130);
                }
            }
        };
        // left upper point is (100,300) and the height and length are both 251
        gpanel.setBounds(100, 300, 251, 251);
        gpanel.setVisible(true);
        container.add(gpanel);
        
        // The label for result
        label = new JLabel();
        label.setText("Result: ");
        label.setBounds(200, 750, 100, 25);
        container.add(label);
        
        // The button to trigger rollDicex functions
        button = new JButton();
        button.setText("Roll Dice");
        button.setBounds(170, 775, 100, 25);
        container.add(button);
        
        // Action listener for the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice1();
                rollDice2();
                // Update dot position
                updateDotPosition();
                // Repaint the dot
                dotPanel.repaint();
            }
        });
        
        // Initial images
        ip11.setVisible(true);
        ip21.setVisible(true);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        RollDice rollDice = new RollDice () ;
    }
    
    // Set Visibility of Dice 1 images
    public void setDice1Visible(boolean visible) {
    	ip11.setVisible(visible);
    	ip12.setVisible(visible);
    	ip13.setVisible(visible);
    	ip14.setVisible(visible);
    	ip15.setVisible(visible);
    	ip16.setVisible(visible);
    }
    
    // Set Visibility of Dice 2 images
    public void setDice2Visible(boolean visible) {
    	ip21.setVisible(visible);
    	ip22.setVisible(visible);
    	ip23.setVisible(visible);
    	ip24.setVisible(visible);
    	ip25.setVisible(visible);
    	ip26.setVisible(visible);
    }
    
    // Function to Roll the Dice 1
    public void rollDice1() {
        // Get Random value from 1 to 6
        Random random = new Random();
        int value = random.nextInt(MAX_DICE) + MIN_DICE;
        
        // Hide the dice 1
        setDice1Visible(false);
        
        // Show which value of dice 1 is generated
        switch (value) {
            case 1: ip11.setVisible(true); break;
            case 2: ip12.setVisible(true); break;
            case 3: ip13.setVisible(true); break;
            case 4: ip14.setVisible(true); break;
            case 5: ip15.setVisible(true); break;
            case 6: ip16.setVisible(true); break;
        }
        
        dice1value = value;
        updateResult();
    }
    
    // Function to Roll the Dice 1
    public void rollDice2() {
        // Get Random value from 1 to 6
        Random random = new Random();
        int value = random.nextInt(MAX_DICE) + MIN_DICE;
        
        // Hide the dice 2
        setDice2Visible(false);
        
        // Show which value of dice 2 is generated
        switch (value) {
            case 1: ip21.setVisible(true); break;
            case 2: ip22.setVisible(true); break;
            case 3: ip23.setVisible(true); break;
            case 4: ip24.setVisible(true); break;
            case 5: ip25.setVisible(true); break;
            case 6: ip26.setVisible(true); break;
        }
        
        dice2value = value;
        updateResult();
        
        // Because the ip1x and ip2x has time difference, the cumulative sum will be calculated at this place once
        cumsum = cumsum + dice1value + dice2value;
        // Test metrics
        // System.out.println(cumsum);
    }
    
    // This function takes responsibility to update dotX and dotY (member variables)
    public void updateDotPosition() {
    	if (cumsum < 25) {
    		if (cumsum % 5 != 0) {
    			int row = cumsum / 5 + 1;
    	    	int col = cumsum % 5;
    	    	dotX = 120 + (col - 1) * 50;
    	    	dotY = 320 + (row - 1) * 50;
    		} else {
    			int row = cumsum / 5;
    			int col = 5;
    			dotX = 120 + (col - 1) * 50;
    	    	dotY = 320 + (row - 1) * 50;
    		}
    	} else {
    		dotX = 320;
	    	dotY = 520;
	    	finished = true;
    	}
    }
    
    
    // Updates the label Result from the value of dice 1 and dice 2
    public void updateResult() {
        int result = dice1value + dice2value;
        label.setText("Result: " + String.valueOf(result));
    }
}