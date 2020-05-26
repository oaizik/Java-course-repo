
package com.nerd.hello;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventException;
import org.xml.sax.SAXException;
/**
 * Parse class suppose to read from the server of The Bank
 * Of Israel and Parsing all the information to other  
 * Currency class
 * @author Yaron Daya
 *
 */
public class Parse implements ParseXml, Runnable{
	byte[] vector;
	private Currency currency= null;
	private NodeList rateList, countryList,countryCode, countryUnit;
	DateFormat dateFormat =null;
	Date date = null; 
	String currentTime = null;
	

	public Parse() {
		currency = new Currency();
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date =new Date();
		setCurrentTime(dateFormat.format(date));
		try {
			readXml();
		} catch (IOException e) {
			Log.logIt("Ubable to read the XML");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			Log.logIt("Ubable to read the XML");
			e.printStackTrace();
		}
		
	}
	
	//write to log file
	//read the XML from the server.
	public void readXml()throws IOException, ParserConfigurationException{
		try {
			URL url = new URL("http://boi.org.il/currency.xml");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			url.openConnection();
			con.connect();
			InputStream in = con.getInputStream();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			try {
				builder = factory.newDocumentBuilder();
				Document doc = builder.parse(in);
				//countryCode = doc.getElementsByTagName("CURRENCYCODE");
				/*
				 rateList = doc.getElementsByTagName("RATE");
				 countryList = doc.getElementsByTagName("NAME");
				 countryUnit = doc.getElementsByTagName("UNIT");
				 */
				
				Currency.setCountryCode(doc.getElementsByTagName("CURRENCYCODE"));
				Currency.setRateList(doc.getElementsByTagName("RATE"));
				Currency.setCountryList(doc.getElementsByTagName("NAME"));
				Currency.setCountryUnit(doc.getElementsByTagName("UNIT"));

				 

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			in.close();
			con.disconnect();
		} catch (MalformedURLException e) {
			Log.logIt("problem with parsing the XML.");
			e.printStackTrace();
	
		} catch (SAXException e) {
			Log.logIt("problem with parsing the XML.");
			e.printStackTrace();
		}
		
	}

		/*
		 * return all the NodeList
		 */


	
	//give code for log file
	//update the list when requested..
	@Override
	public synchronized void update() {
		
			try {
				readXml();
			} catch (IOException e) {
				Log.logIt(("failed by update"));
				e.printStackTrace();
			} catch (ParserConfigurationException e) {				
				Log.logIt("failed by update");
				e.printStackTrace();
			}
		
		
	}

	@Override
	public void run() {
		while(true){
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			Log.logIt("Parse.Thread cannot sleep.");
			e.printStackTrace();
		}
		update();
		date =new Date();
		setCurrentTime(dateFormat.format(date));

		}
	}
	private void setCurrentTime(String df){
		this.currentTime= df;
	}

	@Override
	public String getLastUpdate() {
		return currentTime;
	}





	
}
