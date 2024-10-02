package kbc;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
// import sun.audio.*;
public class Ending extends JFrame {
	void win(int n, int i, String[] amount, JLabel amountLabel) {
					setTitle("Kaun Banega Crorepati");
					setLayout(null);
					getContentPane().setBackground(Color.black);
					setSize(1000, 720);
					setLocationRelativeTo(null);
					setVisible(true);
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					setResizable(false);
					
					JLabel ty = new JLabel("Thank You for Playing!");
					ty.setBounds(0, 60, 1000, 80);
					ty.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 35));
					ty.setHorizontalAlignment(JLabel.CENTER);
					ty.setForeground(Color.ORANGE);
					add(ty);
					
					JLabel tx = new JLabel("You Have Won");
					tx.setBounds(0, 170, 1000, 80);
					tx.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 35));
					tx.setHorizontalAlignment(JLabel.CENTER);
					tx.setForeground(Color.WHITE);
					add(tx);
					
					// Use the amountLabel passed from the interrface class
					amountLabel.setBounds(0, 280, 1000, 80);
					amountLabel.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 45));
					amountLabel.setHorizontalAlignment(JLabel.CENTER);
					add(amountLabel);
					
					sound("Thanks.wav");
					button();
	}

	void button(){
		
		JLabel w = new JLabel();
		w.setBounds(524,500,116,32);
		add(w);
		w.setIcon(new ImageIcon("img_files/back.png"));
		w.setBackground(Color.BLACK);
		w.setOpaque(true);
		mouseaction(w,1);
		
		JLabel s = new JLabel();
		s.setBounds(360,500,116,32);
		add(s);
		s.setIcon(new ImageIcon("img_files/new.png"));
		s.setBackground(Color.BLACK);
		s.setOpaque(true);
		mouseaction(s,2);
		
	}
	
	void mouseaction(JLabel w,int i){
		
		w.addMouseListener(new MouseAdapter() {
		    
			public void mouseEntered(MouseEvent e) {
		    	
				if(i==1){
					w.setIcon(new ImageIcon("img_files/back1.png"));
				}
				else{
					w.setIcon(new ImageIcon("img_files/new1.png"));
				}
		    	sound("beep.wav");
		    	
		    }
	
		    public void mouseExited(MouseEvent e) {
		    	
		    	if(i==1){
					w.setIcon(new ImageIcon("img_files/back.png"));
				}
				else{
					w.setIcon(new ImageIcon("img_files/new.png"));
				}
		    	
		    } 
			
		    public void mouseClicked(MouseEvent e) {
					
					dispose();
					if(i==1){
						start ob1=new start();
						ob1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						ob1.setResizable(false);
						ob1.setSize(1000,720);
						ob1.setVisible(true);
					}
					else{
						interrface ob1;
						try {
							ob1 = new interrface();
							ob1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							ob1.setResizable(false);
							ob1.setSize(1000,720);
							ob1.setVisible(true);
						} catch (Exception e1) {
						}
	
					}
		    	
		    }     
		            
		});
		
	}

	void sound(String filename){
		try{
			filename="sounds/"+filename;
			File soundFile = new File(filename);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		}
		catch(Exception e1){
		}
	}

	
}






