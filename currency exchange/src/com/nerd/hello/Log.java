package com.nerd.hello;

import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * This Log class keeps all the imformation that happend in this project.
 * if you add something new and want to log it to check result
 * you can use the static function LogIt and it will keep the info in 
 * a document that calld Log.txt.
 * 
 * @author Yaron Daya
 *
 */
public class Log {
	private static JFrame frame = null;
	private static JTextArea text = null;
	private static File file = null;
	private static Date date = null;
	private static boolean flag = false;
	
	/*
	 * ******************Constructor******************
	 */
	public Log(){
		file = new File("Log.txt");
		date = new Date();
		file.writeToFile(date +" - log file has created.\n");
		text = new JTextArea();
	}
	/**************************************************/
	
	
	/*
	 * Static function that able to write to a log file
	 * and write to a JTextField and the user can always
	 * see what's happening real time.
	 */
	public static void logIt(String str){
		date = new Date();
		file.writeToFile(date +" - "+ str+"\n");
		text.setText(text.getText()+"\n"+date+" - " +str);

	}
	
	/*
	 * showGui function is function that connect to the 
	 * User Interface and connects to a Button.
	 * If it get pressed the Gui appear.
	 */
	public static void showGui(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(600, 600, 600, 600);
		frame.add(text);
		frame.setVisible(true);
		flag = true;
	}

}
