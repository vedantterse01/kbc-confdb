package kbc;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

// Question class to represent a quiz question
class Question {
    private int id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int correctAnswer;

    public Question(int id, String question, String optionA, String optionB, String optionC, String optionD, int correctAnswer) {
        this.id = id;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() { return question; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public int getCorrectAnswer() { return correctAnswer; }
}

// Class to fetch questions from the database
class QuestionFetcher {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/kbc_quiz";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Vedant7605@!";

    public java.util.List<Question> fetchQuestions() {
        java.util.List<Question> questions = new ArrayList<>();
     String query = "SELECT * FROM questions";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String questionText = rs.getString("question");
                String option1 = rs.getString("option1");
                String option2 = rs.getString("option2");
                String option3 = rs.getString("option3");
                String option4 = rs.getString("option4");
                int correctOption = rs.getInt("correct_option");

                questions.add(new Question(id, questionText, option1, option2, option3, option4, correctOption));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
}

public class interrface extends JFrame {
    private int flag = 0;
    private int n_question = 15; // Initialize n_question with the total number of questions

    // Define the countdown method
    private void countdown(int i) {
        // Implement the countdown logic here
        System.out.println("Countdown started for option: " + i);
    }

    private QuestionFetcher questionFetcher;
    private List<Question> questions;
    private int currentQuestionIndex = 0;

    private JLabel questionLabel;
    private JLabel[] answerLabels = new JLabel[4];
    private JLabel o1, o2, o3, o4; // Define o1, o2, o3, o4 as class member variables
    private JLabel[] progressLabels = new JLabel[16];
    private String[] amounts = {
        "1,00,00,000", "50,00,000", "25,00,000", "12,50,000",
        "6,40,000", "3,20,000", "1,60,000", "80,000",
        "40,000", "20,000", "10,000", "5,000",
        "3,000", "2,000", "1,000"
    };
    private String[] questionNumbers = {
        "15", "14", "13", "12", "11", "10",
        "9", "8", "7", "6", "5", "4",
        "3", "2", "1"
    };
    private JLabel[] markLabels = new JLabel[16];

    private JLabel box = new JLabel();

    // Timer components
    private JLabel timerLabel;
    private Timer countdownTimer;
    private int countdownTime = 30; // Timer set to 30 seconds

    public interrface() throws Exception {
        setTitle("QUIZ MASTERS");
        setLayout(null);
        setSize(1000, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Initialize the timer label
        timerLabel = new JLabel();
        timerLabel.setBounds(270, 50, 300, 300);  // Adjust bounds for the circular timer
        add(timerLabel);
        updateTimerDisplay();

        // Fetch questions from the database
        questionFetcher = new QuestionFetcher();
        questions = questionFetcher.fetchQuestions();

        // Initialize UI components
        questionLabel = new JLabel("Question will appear here", JLabel.CENTER);
        questionLabel.setBounds(50, 390, 850, 100);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setForeground(Color.white);
        add(questionLabel);

        answerLabels[0] = new JLabel();
        answerLabels[0].setBounds(100, 510, 436, 47);
        answerLabels[0].setFont(new Font("Arial", Font.BOLD, 20));
        answerLabels[0].setForeground(Color.white);
        add(answerLabels[0]);

        answerLabels[1] = new JLabel();
        answerLabels[1].setBounds(540, 510, 436, 47);
        answerLabels[1].setFont(new Font("Arial", Font.BOLD, 20));
        answerLabels[1].setForeground(Color.white);
        add(answerLabels[1]);

        answerLabels[2] = new JLabel();
        answerLabels[2].setBounds(100, 580, 436, 47);
        answerLabels[2].setFont(new Font("Arial", Font.BOLD, 20));
        answerLabels[2].setForeground(Color.white);

        add(answerLabels[2]);

        answerLabels[3] = new JLabel();
        answerLabels[3].setBounds(540, 580, 436, 47);
        answerLabels[3].setFont(new Font("Arial", Font.BOLD, 20));
        answerLabels[3].setForeground(Color.white);
        add(answerLabels[3]);

        // Load the first question
        if (questions.size() > 0) {
            displayQuestion(0);
        }

        initializeGameComponents();
        startTimer();
    }

    private void initializeGameComponents() {
        try {
            kbcframe();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        quitbutton();
        createToggleButton();
    }

    private void updateTimerDisplay() {
        timerLabel.setIcon(new ImageIcon(createTimerImage(countdownTime)));
    }

    private Image createTimerImage(int timeLeft) {
        int diameter = 300;
        BufferedImage img = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the circle
        g.setColor(Color.WHITE);
        g.fillOval(0, 0, diameter, diameter);

        // Draw the timer background
        int strokeWidth = 20;
        g.setStroke(new BasicStroke(strokeWidth));
        g.setColor(Color.GRAY);
        g.drawOval(strokeWidth / 2, strokeWidth / 2, diameter - strokeWidth, diameter - strokeWidth);

        // Draw the countdown arc
        int angle = (int) (360 * (timeLeft / 30.0));
        g.setColor(timeLeft <= 15 ? Color.RED : Color.GREEN);
        g.drawArc(strokeWidth / 2, strokeWidth / 2, diameter - strokeWidth, diameter - strokeWidth, 90, -angle);

        // Draw the time text
        g.setColor(g.getColor());  // Use the same color as the arc
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String timeText = timeLeft + "s";
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(timeText);
        int textHeight = fm.getAscent();
        g.drawString(timeText, diameter / 2 - textWidth / 2, diameter / 2 + textHeight / 2);

        g.dispose();
        return img;
    }

    private void startTimer() {
        countdownTimer = new Timer(true);
        countdownTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                countdownTime--;
                updateTimerDisplay();
                if (countdownTime <= 0) {
                    countdownTimer.cancel();
                    JOptionPane.showMessageDialog(null, "Time's up! The game is over.");
                    endGame();
                }
            }
        }, 0, 1000);
        
    }

    private void resetTimer() {
        if (countdownTimer != null) {
            countdownTimer.cancel();  // Cancel any existing timer
        }
        countdownTime = 30; // Reset to 30 seconds
        updateTimerDisplay();
    }

    private void endGame() {
        // Game ending logic here
        dispose();
        Ending endingScreen = new Ending();
        endingScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endingScreen.setResizable(false);
        endingScreen.setSize(1000, 720);
        endingScreen.setVisible(true);
    }

    private void displayQuestion(int index) {
        Question q = questions.get(index);
        questionLabel.setText(q.getQuestion());
        answerLabels[0].setText(q.getOptionA());
        answerLabels[1].setText(q.getOptionB());
        answerLabels[2].setText(q.getOptionC());
        answerLabels[3].setText(q.getOptionD());
    }

    private void kbcframe() throws Exception {
        setTitle("Kaun Banega Crorepati");
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);

        // Padav
        int siz = 20;
        for (int i = 0; i <= 14; i++) {
            String temp = new String();
            int a = questionNumbers[i].length();
            int b = amounts[i].length();
            for (int j = 0; j <= 10; j++) {
                temp = temp.concat(" ");
            }
            if (a == 1) {
                temp = temp.concat("  ");
            }
            int spaces = 22 - b;
            temp = temp.concat(questionNumbers[i]);
            for (int j = 1; j <= spaces; j++) {
                temp = temp.concat("  ");
            }
            temp = temp.concat(amounts[i]);
            JLabel p = new JLabel(temp);
            p.setBounds(782, 70 + i * siz, 220, 20);

            JLabel mark = new JLabel();
            mark.setBounds(845, 48 + i * siz, 60, 64);
            mark.setIcon(new ImageIcon("img_files/diamond.png"));
            mark.setVisible(false);
            add(mark);
            if (i % 5 == 0) {
                p.setForeground(Color.WHITE);
            } else {
                p.setForeground(Color.ORANGE);
            }
            add(p);
        }

        // Question
        JLabel q1 = new JLabel();
        q1.setBounds(140, 394, 871, 94);
        add(q1);

        JLabel q2 = new JLabel();
        q2.setBounds(140, 405, 871, 47);
        add(q2);

        JLabel q3 = new JLabel();
        q3.setBounds(140, 394 + 37, 871, 47);
        add(q3);

        o1 = new JLabel();
        JLabel o1 = new JLabel();
        o1.setBounds(100, 510, 436, 47);
        add(o1);
        o2 = new JLabel();
        JLabel o2 = new JLabel();
        o2.setBounds(540, 510, 436, 47);
        add(o2);
        o3 = new JLabel();
        JLabel o3 = new JLabel();
        o3.setBounds(100, 580, 436, 47);
        add(o3);
        o4 = new JLabel();
        JLabel o4 = new JLabel();
        o4.setBounds(540, 580, 436, 47);
        add(o4);

        // Ques bar
        JLabel ques = new JLabel();
        ques.setBounds(60, 394, 871, 94);
        add(ques);
        ques.setIcon(new ImageIcon("img_files/box.png"));

        // Options
        int x = 60;
        int y = 510;

 for (int i = 1; i <= 4; i++) {
            if (i % 2 == 0) {
                x = 500;
            } else {
                x = 60;
            }
            if (i == 3) {
                y += 70;
            }
            JLabel op = new JLabel();
            op.setBounds(x, y, 436, 47);
            add(op);
            op.setIcon(new ImageIcon("img_files/transbox1.png"));
            mouseaction(op, i);
        }

        // Lifelines panel
        JLabel life = new JLabel("LIFELINES");
        life.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
        life.setBounds(782, 0, 220, 50);
        add(life);
        life.setHorizontalAlignment(JLabel.CENTER);
        life.setOpaque(true);
        life.setBackground(Color.BLACK);
        life.setForeground(Color.WHITE);
        life.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                if (flag != 1) {
                    life.setForeground(Color.ORANGE);
                    sound("beep.wav");
                }
            }

            public void mouseExited(MouseEvent e) {
                if (flag != 1) {
                    life.setForeground(Color.WHITE);
                }
            }

            public void mouseClicked(MouseEvent e) {
                if (flag != 1) {
                    life.setVisible(false);
                }
            }
        });

        // Lines for interface
        int x1 = 0;
        int y1 = 532;
        int z1 = 63;

        for (int i = 1; i <= 6; i++) {
            if (i % 3 == 1) {
                x1 = 0;
                z1 = 63;
            }
            if (i % 3 == 2) {
                x1 = 493;
                z1 = 10;
            }
            if (i % 3 == 0) {
                x1 = 932;
                z1 = 63;
            }
            if (i <= 3) {
                y1 = 532;
            } else if (i <= 6) {
                y1 = 602;
            } else if (i <= 8) {
                y1 = 602;
            }
            JLabel l = new JLabel();
            l.setBounds(x1, y1, z1, 2);
            add(l);
            l.setOpaque(true);
            l.setBackground(Color.WHITE);
        }

        JLabel l5 = new JLabel();
        l5.setBounds(0, 440, 66, 2);
        add(l5);
        l5.setOpaque(true);
        l5.setBackground(Color.WHITE);

        JLabel l6 = new JLabel();
        l6.setBounds(926, 440, 65, 2);
        add(l6);
        l6.setOpaque(true);
        l6.setBackground(Color.WHITE);

        JLabel l9 = new JLabel("");
        l9.setBounds(780, 0, 2, 395);
        add(l9);
        l9.setOpaque(true);
        l9.setBackground(Color.WHITE);

        JLabel l10 = new JLabel("");
        l10.setBounds(780, 50, 290, 2);
        add(l10);
        l10.setOpaque(true);
        l10.setBackground(Color.WHITE);

        JLabel lf2 = new JLabel("");
        lf2.setBounds(820, 10, 30, 30);
        add(lf2);
        lf2.setIcon(new ImageIcon("img_files/501.png"));
        lifeaction(lf2, 1);

        JLabel lf3 = new JLabel("  ");
        lf3.setBounds(910, 10, 100, 30);
        add(lf3);
        lf3.setIcon(new ImageIcon("img_files/skip1.png"));
        lifeaction(lf3, 2);
    }

    private void mouseaction(JLabel op, int i) {
        op.addMouseListener(new MouseAdapter() {
    
            public void mouseEntered(MouseEvent e) {
                if (flag != 1) {
                    op.setIcon(new ImageIcon("img_files/orangebox1.png"));
                    sound("beep.wav");
                }
            }
    
            public void mouseExited(MouseEvent e) {
                if (flag != 1) {
                    op.setIcon(new ImageIcon("img_files/transbox1.png"));
                }
            }
    
            public void mouseClicked(MouseEvent e) {
                if (flag != 1) {
                    sound("OptionLock.wav");
                    checkAnswer(i); // Call the checkAnswer method here
                    flag = 1;
                }
            }
        });
    }

    private int[] lif = new int[3]; // Declare and initialize the lif array

    private int used = 0; // Declare the used variable
    
    private void lifeaction(JLabel op, int i) {
            op.addMouseListener(new MouseAdapter() {
    
                public void mouseEntered(MouseEvent e) {
                    if (flag != 1) {
                        if (i == 1 && lif[i] != 1) {
                            op.setIcon(new ImageIcon("img_files/50.png"));
                        } else if (i == 2 && lif[i] != 1) {
                            op.setIcon(new ImageIcon("img_files/skip.png"));
                        }
                        sound("beep.wav");
                    }
                }
    
                public void mouseExited(MouseEvent e) {
                    if (flag != 1) {
                        if (i == 1 && lif[i] != 1) {
                            op.setIcon(new ImageIcon("img_files/501.png"));
                        } else if (i == 2 && lif[i] != 1) {
                            op.setIcon(new ImageIcon("img_files/skip1.png"));
                        }
                    }
                }
    
                public void mouseClicked(MouseEvent e) {
        if (flag != 1) {
            int ko = 0;
            int j = 0;
            int temp = 0;
            try {
                Question q = questions.get(currentQuestionIndex);
                temp = q.getCorrectAnswer();
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
            if (i == 1) {
                used = 1;
                if (lif[i] == 1) {
                    return;
                }
                lif[i] = 1;
                op.setIcon(new ImageIcon("img_files/cross.png"));
                while (j < 2) {
                    ko++;
                    if (temp != ko) {
                        j++;
                        if (ko == 1) {
                            o1.setText("");
                        }
                        if (ko == 2) {
                            o2.setText("");
                        }
                        if (ko == 3) {
                            o3.setText("");
                        }
                    }
                }
            }
            if (i == 2) {
                if (lif[i] == 1) {
                    return;
                }
                op.setIcon(new ImageIcon("img_files/skipcross.png"));
                lif[i] = 1;
                n_question--;
                try {
                   
                } catch (Exception e1) {
                }
            }
        }
    }
            });
        }
        private void checkAnswer(int optionNumber) {
            Question q = questions.get(currentQuestionIndex);
            if (optionNumber == q.getCorrectAnswer()) {
                // Correct answer
                JOptionPane.showMessageDialog(null, "Correct answer!");
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion(currentQuestionIndex);
                    resetTimer();
                    updateUI(); // Update the UI to show the next question
                    updateMoney(); // Update the money
                    updateQuestionNumber(); // Update the question number
                } else {
                    endGame(true);
                }
            } else {
                // Incorrect answer
                JOptionPane.showMessageDialog(null, "Incorrect answer! Game over.");
                endGame(false);
            }
        }
        
        private void updateUI() {
            // Update the UI to show the next question
            flag = 0;
            o1.setText("");
            o2.setText("");
            o3.setText("");
            o4.setText("");
            lif[1] = 0;
            lif[2] = 0;
            used = 0;
        }
        
        private void updateMoney() {
            // Update the money
            int moneyIndex = 15 - currentQuestionIndex;
            if (moneyIndex >= 0 && moneyIndex < amounts.length) {
                JLabel mark = (JLabel) getComponentAt(845, 48 + moneyIndex * 20);
                mark.setVisible(true);
            }
        }
        
        private void updateQuestionNumber() {
            // Update the question number
            int questionIndex = 15 - currentQuestionIndex;
            if (questionIndex >= 0 && questionIndex < questionNumbers.length) {
                JLabel questionLabel = (JLabel) getComponentAt(782, 70 + questionIndex * 20);
                questionLabel.setForeground(Color.WHITE);
            }
        }
    boolean isMuted = false;
    private void sound(String filename) {
        if (isMuted) {
            return;
        }
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

    private void createToggleButton() {
        JToggleButton toggleButton = new JToggleButton("🔇");
        int buttonSize = 30;
        toggleButton.setBounds(100, 30, buttonSize, buttonSize);
        toggleButton.setBounds(660, 10, 105, 40);
        Font font = new Font("SansSerif", Font.PLAIN, 30);
        toggleButton.setForeground(Color.WHITE);
        toggleButton.setFont(font);
        toggleButton.setBorderPainted(true);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setOpaque(false);
        add(toggleButton);

        toggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (ev.getStateChange() == ItemEvent.SELECTED) {
                    toggleButton.setText("🔊");
                    isMuted = true;
                } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
                    toggleButton.setText("🔇");
                    isMuted = false;
                }
            }
        });
    }

    private void quitbutton() {
        JLabel w = new JLabel();
        w.setBounds(30, 30, 116, 32);
        add(w);
        w.setIcon(new ImageIcon("img_files/quit.png"));
        w.setBackground(Color.BLACK);
        w.setOpaque(true);
        w.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (flag != 1) {
                    w.setIcon(new ImageIcon("img_files/quit1.png"));
                    sound("beep.wav");
                }
            }
    
            public void mouseExited(MouseEvent e) {
                if (flag != 1) {
                    w.setIcon(new ImageIcon("img_files/quit.png"));
                }
            }
    
            public void mouseClicked(MouseEvent e) {
                if (flag != 1) {
                    Object[] options = {"Yes", "No"};
                    int n = JOptionPane.showOptionDialog(null, "Want to Quit and walk out with the Money?", "Want to Quit?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == 0) {
                        // User chose to quit
                        if (countdownTimer != null) {
                            countdownTimer.cancel();
                        }
                        dispose();
                        Ending ob = new Ending();
                        ob.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        ob.setResizable(false);
                        ob.setSize(1000, 720);
                        
                        // Calculate the amount won
                        String amountWon = getAmountWon();
                        
                        // Create a label with the amount won and set its color to white
                        JLabel amountLabel = new JLabel("Rs. " + amountWon);
                        amountLabel.setForeground(Color.WHITE);
                        
                        // Pass the correct information to the Ending screen
                        ob.win(currentQuestionIndex, 2, new String[]{amountWon}, amountLabel);
                        
                        ob.setVisible(true);
                    }
                }
            }
        });
    }
    
    private String getAmountWon() {
        if (currentQuestionIndex > 0) {
            return amounts[15 - currentQuestionIndex];
        } else {
            return "0"; // If no questions answered, the amount won is 0
        }
    }

    private void endGame(boolean won) {
        if (countdownTimer != null) {
            countdownTimer.cancel();
        }
        dispose();
        
        Ending endingScreen = new Ending();
        String amountWon = won ? amounts[15 - questions.size()] : getLastSafeAmount();
        JLabel amountLabel = new JLabel("Rs. " + amountWon);
    amountLabel.setForeground(Color.WHITE);
        endingScreen.win(currentQuestionIndex, won ? 1 : 0, new String[]{amountWon} , amountLabel);
        endingScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endingScreen.setResizable(false);
        endingScreen.setSize(1000, 720);
        endingScreen.setVisible(true);
    }

    private String getLastSafeAmount() {
        if (currentQuestionIndex >= 10) {
            return amounts[5]; // 3,20,000
        } else if (currentQuestionIndex >= 5) {
            return amounts[10]; // 10,000
        } else {
            return "0"; // No safe amount reached
        }
    }
}