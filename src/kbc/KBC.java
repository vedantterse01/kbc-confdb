package kbc;

import java.io.*;
import javax.swing.*;
import javax.sound.sampled.*;

public class KBC {

	public static void main(String[] args) {
		try{
			FileInputStream in=new FileInputStream(new File("sounds/KBC_Theme.wav"));
			AudioInputStream audio=AudioSystem.getAudioInputStream(in);
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		}
		catch(Exception e1){
		}
		start obj=new start();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.setResizable(false);
		obj.setSize(1000,720);
		obj.setVisible(true);
		
	}
}
