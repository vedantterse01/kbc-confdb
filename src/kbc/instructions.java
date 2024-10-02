package kbc;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class instructions extends JFrame {

    JLabel lblKbc;
    JTextArea text;
    boolean isSoundEnabled = true;  // Default to sound enabled

    // Constructor
    public instructions() {
        setLayout(null);
        getContentPane().setBackground(new Color(30, 60, 90));  // Light dark blue background
        setTitle("Kaun Banega Crorepati");

        lblKbc = new JLabel("INSTRUCTIONS");
        lblKbc.setBounds(10, 10, 1000, 80);
        lblKbc.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 40));
        lblKbc.setHorizontalAlignment(JLabel.CENTER);
        lblKbc.setForeground(Color.WHITE);
        add(lblKbc);

        // Back button
        backButton();

        // Embedded instructions text
        String instructionsText = "Get ready to flex your brainpower and test your skills in Java, SQL, and DSA with our Ultimate Tech Quiz!\n\n" +
                                  "Mind-bending questions stand between you and the title of Tech Crorepati – are you up for the challenge?\n\n" +
                                  "Here’s how it works:\n\n" +
                                  "Answer smart, win big – conquer each question and work your way up to a massive Rs. 1 Crore!\n\n" +
                                  "Two Lifelines to save the day – if you hit a roadblock, call in a lifeline:\n" +
                                  "'50-50' to knock out two wrong answers\n" +
                                  "'Skip the Question' to dodge the tough ones!\n\n" +
                                  "Two Safezones – secure your position at Rs. 10,000 and Rs. 3,20,000. \n" +
                                  "If you stumble after that, no worries, you'll fall back to the last safezone!\n\n" +
                                  "You can bow out anytime and keep what you’ve won so far – sometimes it’s smart to walk away!\n\n" +
                                  "Are you ready to prove you're the ultimate tech whiz? \n" +
                                  "Let’s see if you’ve got what it takes to become a Crorepati – knowledge-wise, of course!";

        // Scrollable text area
        text = new JTextArea(instructionsText);
        text.setBounds(10, 90, 950, 600);  // Adjust height to fit within the JFrame
        text.setFont(new Font("Roboto", Font.PLAIN, 20));
        text.setForeground(Color.WHITE);
        text.setBackground(Color.BLACK);
        text.setEditable(false);

        // Add scroll pane for the text area
        JScrollPane scrollPane = new JScrollPane(text);
        scrollPane.setBounds(10, 90, 950, 600);
        add(scrollPane);
    }

    void backButton() {
        JLabel backLabel = new JLabel();
        backLabel.setBounds(445, 600, 116, 32);
        add(backLabel);
        backLabel.setIcon(new ImageIcon("img_files/back.png"));
        backLabel.setBackground(Color.BLACK);
        backLabel.setOpaque(true);
        backLabel.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                backLabel.setIcon(new ImageIcon("img_files/back1.png"));
                playSound("beep.wav");
            }

            public void mouseExited(MouseEvent e) {
                backLabel.setIcon(new ImageIcon("img_files/back.png"));
            }

            public void mouseClicked(MouseEvent e) {
                dispose();
                start startFrame = new start();
                startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                startFrame.setResizable(false);
                startFrame.setSize(1000, 720);
                startFrame.setVisible(true);
            }
        });
    }

    void playSound(String filename) {
        if (!isSoundEnabled) return;  // If sound is disabled, do nothing

        try {
            filename = "sounds/" + filename;
            File soundFile = new File(filename);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
									  start obj=new start();
            // frame.setSize(1000, 720);
            // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // frame.setVisible(true);
        });
    }
}
