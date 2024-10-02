package kbc;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class start extends JFrame {

    JLabel logo, lblKbc, Start, Inst, Abt, StartText, InstText, AbtText, l1, l2, l3, l4, l5, l6;

    // constructor
    public start() {
        kbcstart();
    }

    JLabel l[] = new JLabel[7];

    void kbcstart() {

        // sound("KBC_Theme.wav");
        setTitle("QUIZ MASTERS");
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);

        logo = new JLabel("");
        logo.setBounds(240, 35, 450, 410);
        add(logo);
        logo.setIcon(new ImageIcon("img_files/logo124-removebg-preview.png"));

        lblKbc = new JLabel("Ready to test your knowledge?");
        lblKbc.setBounds(0, 10, 1000, 80);
        lblKbc.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 40));
        lblKbc.setHorizontalAlignment(JLabel.CENTER);
        lblKbc.setForeground(Color.WHITE);
        add(lblKbc);

        int x = 0, y = 449;

        // lines
        for (int i = 1; i <= 6; i++) {
            if (i == 4) {
                x += 717;
            }
            if (i % 3 == 1) {
                y = 449;
            }
            if (i % 3 == 2) {
                y = 507;
            }
            if (i % 3 == 0) {
                y = 567;
            }
            l[i] = new JLabel();
            l[i].setBounds(x, y, 288, 2);
            add(l[i]);
            l[i].setOpaque(true);
            l[i].setBackground(Color.WHITE);
        }

        Start = new JLabel();
        Start.setBounds(273, 425, 460, 50);
        Start.setIcon(new ImageIcon("img_files/transbox1.png"));
        add(Start);
        Start.setHorizontalAlignment(JLabel.CENTER);
        Start.setVisible(true);

        StartText = new JLabel("Play");
        StartText.setBounds(285, 425, 436, 47);
        StartText.setFont(new Font("Rockwell", Font.PLAIN, 20));
        StartText.setForeground(Color.WHITE);
        StartText.setHorizontalAlignment(JLabel.CENTER);
        add(StartText);
        mouseaction(Start, StartText, 1, l[1], l[4]);

        Inst = new JLabel();
        Inst.setBounds(273, 483, 460, 50);
        Inst.setIcon(new ImageIcon("img_files/transbox1.png"));
        add(Inst);
        Inst.setHorizontalAlignment(JLabel.CENTER);
        Inst.setVisible(true);

        InstText = new JLabel("Instructions");
        InstText.setBounds(285, 485, 436, 47);
        InstText.setFont(new Font("Rockwell", Font.PLAIN, 20));
        InstText.setForeground(Color.WHITE);
        InstText.setHorizontalAlignment(JLabel.CENTER);
        mouseaction(Inst, InstText, 2, l[2], l[5]);
        add(InstText);

        Abt = new JLabel();
        Abt.setBounds(273, 543, 460, 50);
        Abt.setIcon(new ImageIcon("img_files/transbox1.png"));
        Abt.setHorizontalAlignment(JLabel.CENTER);
        add(Abt);
        Abt.setVisible(true);

        AbtText = new JLabel("About Us");
        AbtText.setBounds(285, 545, 436, 47);
        AbtText.setFont(new Font("Rockwell", Font.PLAIN, 20));
        AbtText.setHorizontalAlignment(JLabel.CENTER);
        AbtText.setForeground(Color.WHITE);
        add(AbtText);
        mouseaction(Abt, AbtText, 3, l[3], l[6]);

        setSize(1000, 720);
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    void mouseaction(JLabel op, JLabel op1, int a, JLabel l1, JLabel l2) {

        op.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {

                l1.setBounds(l1.getX(), l1.getY(), l1.getWidth() - 11, l1.getHeight());
                l2.setBounds(l2.getX() + 11, l2.getY(), l2.getWidth(), l2.getHeight());
                op.setIcon(new ImageIcon("img_files/transbox2.png"));
                op1.setFont(new Font("Rockwell", Font.PLAIN, 23));
                sound("beep.wav");

            }

            public void mouseExited(MouseEvent e) {

                l1.setBounds(l1.getX(), l1.getY(), l1.getWidth() + 11, l1.getHeight());
                l2.setBounds(l2.getX() - 11, l2.getY(), l2.getWidth(), l2.getHeight());
                op.setIcon(new ImageIcon("img_files/transbox1.png"));
                op1.setFont(new Font("Rockwell", Font.PLAIN, 20));

            }

            public void mouseClicked(MouseEvent e) {

                dispose();
                JFrame newFrame = null;

                if (a == 1) {
                    try {
                        newFrame = new interrface();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else if (a == 2) {
                    newFrame = new instructions();
                } else {
                    newFrame = new about();
                }

                if (newFrame != null) {
                    newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    newFrame.setResizable(false);
                    newFrame.setSize(1000, 720);
                    newFrame.setLocation(getLocation()); // Set new window to open at the current window's position
                    newFrame.setVisible(true);
                }

            }

        });
    }

    void sound(String filename) {
        try {
            filename = "sounds/" + filename;
            File soundFile = new File(filename);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
}
