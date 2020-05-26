package com.nerd.hello;

import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

public class Main {


	public static void main(String[] args) {

		Log l = new Log();					//Create the Log file for the entire Application.
		Parse parse = new Parse();
		Currency currency = new Currency();		
		Window frame = new Window();		//intialized the User Gui
		frame.setVisible(true);		
			
		
		
	}

}
