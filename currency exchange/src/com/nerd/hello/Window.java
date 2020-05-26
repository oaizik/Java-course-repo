package com.nerd.hello;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.xml.sax.SAXException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.Vector;

import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

/**
 * The Window.class represent the GUI of the project.
 * this class have a Thread that always keep it element 
 * up-to-date as can.
 * @author Yaron Daya
 *
 */
public class Window extends JFrame implements ActionListener, FocusListener ,Runnable{

	Parse parse = null;
	JTextArea textArea = new JTextArea();
	JTextArea CoinUpdateValueList = new JTextArea();
	private JPanel panel=null;
	private JTextField amount=null;
	private JTextField from=null;
	private JTextField to=null;
	private JTextField score = null;
	private JTextField search = null;
	private JTextPane txtpnChooseFromThe=null;
	private JTable table=null;
	private JPanel contentPane=null;
	private JButton update=null;
	private JButton sum=null;
	private JButton butsearch=null;
	private JButton logButton=null;
	private JTable tabele;
	private JScrollPane Pane;
	private DefaultTableModel modle3;
	private JPanel table_panle;
	double EnterSum;
	
	
	String ChangeFrom;
	String ChangeTo;
	String tempJTextFieldText=null;

	DateFormat dateFormat =null;//= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = null; //new Date();
	boolean isSearch =false;

    
	public Window() {
		Log.logIt("Window constructor.");
		parse = new Parse();
		Thread t1 = new Thread(this);
		Thread t2 = new Thread(parse);
		t1.start();
		t2.start();
		
		//get the current time and date only for the constructor..
		//after it will get the current time from the parseXml.
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(690,590);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230,230,250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		JLabel lblEnterAmountOf = new JLabel("Enter amount of money to exchange:");
		lblEnterAmountOf.setForeground(new Color(10,0,128));
		
		amount = new JTextField();//151, 126, 86, 20
		amount.setForeground(new Color(255, 51, 0));
		amount.setColumns(10);
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(new Color(230,230,250));
		inputPanel.setLayout(new GridLayout(6,2));
		inputPanel.add(lblEnterAmountOf);
		inputPanel.add(amount);
		
		
		
		
		JLabel lblFromCountryCoin = new JLabel("Change from Country coin:");
		lblFromCountryCoin.setForeground(new Color(0,0,128));
		lblFromCountryCoin.setBounds(0, 120, 159, 32);
		inputPanel.add(lblFromCountryCoin);
		
		from = new JTextField();
		from.setForeground(new Color(0,0,128));
		from.setBounds(151, 169, 86, 20);
		inputPanel.add(from);
		from.setColumns(10);
		
		JLabel lblToCountryCoin = new JLabel("To country Coin:");
		lblToCountryCoin.setForeground(new Color(0,0,128));
		lblToCountryCoin.setBounds(10, 163, 114, 32);
		inputPanel.add(lblToCountryCoin);
		
		to = new JTextField();
		to.setForeground(new Color(0,0,128));
		to.setBounds(196, 72, 86, 20);
		inputPanel.add(to);
		to.setColumns(10);
		
				
		JLabel lblNewLabel = new JLabel("Country Coin's List:");
		lblNewLabel.setForeground(new Color(0,0,128));
		lblNewLabel.setBounds(498, 4, 187, 32);
		inputPanel.add(lblNewLabel);
		
		sum = new JButton("Calculate");
		sum.setForeground(new Color(0,0,128));
		sum.setBackground(new Color(230,230,250));
		sum.setBounds(445, 206, 149, 23);
		inputPanel.add(sum);
		//contentPane.add(sum);
		
		modle3 = new DefaultTableModel();
		
		tabele = new JTable(modle3);
		Pane = new JScrollPane(tabele);
		Pane.setBounds(228, 5, 304, 304);
		
		tabele.setPreferredScrollableViewportSize(new Dimension(300,300));
		tabele.setFillsViewportHeight(true);
		
		table_panle = new JPanel();
		table_panle.setBackground(new Color(230,230,250));
				table_panle.setLayout(null);
		
				logButton = new JButton("Log");
				table_panle.add(logButton);
				logButton.setBackground(new Color(204, 204, 255));
				logButton.setForeground(new Color(30, 144, 255));
				logButton.addActionListener(this) ;
				logButton.setBounds(6, 249, 80, 29);
		table_panle.add(Pane);

		score = new JTextField();
		score.setBounds(200, 150, 200, 10);
		CoinUpdateValueList.setBounds(357, 43, 110, 291);
		contentPane.add(table_panle,BorderLayout.CENTER);
		modle3.addColumn("CODE");
		modle3.addColumn("Country Name");//כל שורה שלמה זה ווקטור
		modle3.addColumn("RATE");
		
		
		updateLists();				//update each of the NodeList
		amount.setText("amount");
		from.setText("from");
		to.setText("to");
		score.setText("blahh");

		paintTable();				//update the Table

		JLabel lblUpdateCoinValue = new JLabel("Update Coin Value:");
		lblUpdateCoinValue.setForeground(new Color(0,0,128));
		lblUpdateCoinValue.setBounds(347, 9, 104, 23);
		inputPanel.add(lblUpdateCoinValue);
		
		update = new JButton("Get Update Values");
		update.setForeground(new Color(0,0,128));
		update.setBounds(332, 345, 149, 40);
		inputPanel.add(update);
		
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new GridLayout(2,1));
		 txtpnChooseFromThe = new JTextPane();
		txtpnChooseFromThe.setEditable(false);
		txtpnChooseFromThe.setText("Last update was: "+dateFormat.format(date));
		txtpnChooseFromThe.setBounds(10, 4, 2915, 57);
		botPanel.add(txtpnChooseFromThe);
		
		
		JPanel searchpanel=new JPanel();
		searchpanel.setLayout(new GridLayout(1,2));
		search = new JTextField ("search");
		search.setBounds(10, 4, 295, 57);
		searchpanel.add(search);
		botPanel.add(searchpanel);
		
		butsearch = new JButton("search");
		butsearch.setBackground(new Color(204, 204, 255));
		butsearch.setForeground(new Color(30, 144, 255));
		butsearch.addActionListener(this) ;
		butsearch.setBounds(445, 206, 149, 23);
		searchpanel.add(butsearch);

		score = new JTextField();
		inputPanel.add(score);
		score.setColumns(10);
		contentPane.add(inputPanel, BorderLayout.NORTH);
		contentPane.add(botPanel, BorderLayout.SOUTH);
		
		
		//action listener for the components
				search.addFocusListener(this);
				update.addActionListener(this);
				 sum.addActionListener(this);	
				 to.addFocusListener(this);
				 from.addFocusListener(this);
				 amount.addFocusListener(this);
				 logButton.addActionListener(this);
			}
	

			
			/**
			 * Puts values inside the table.
			 * this function gets calld several time becuase it
			 * seppose to return the latest version of currency
			 * rate			
			 */
			private void paintTable(){
				Log.logIt("Put's values inside the table.");
				modle3.setRowCount(0);
				modle3.addRow(new Object[]{"ILS","Shekel",1});
				for(int i=0;i<Currency.getLength();i++){
					modle3.addRow(new Object[]{	Currency.getLast(Currency.getCountryCode(), i),
							Currency.getLast(Currency.getCountryList(),i),
							Currency.getLast(Currency.getRateList(),i)});
					
					}

			}
			//--------------------------------------------------------------


	
			
	
			
			/*control all the buttons*/
			 public void actionPerformed(ActionEvent event){
				 Object src = event.getSource();
				 if(src==update){
					 Log.logIt("update was pressed.");
				 updateLists();
				 }
				 else if(src==logButton){	//btnLog
					 Log.showGui();
				 }
				 else if(src==sum){
					 to.setText(to.getText().toUpperCase());
					 from.setText(from.getText().toUpperCase()); 
				Currency.setTextBoxs(from.getText(), to.getText(), amount.getText());
				 Log.logIt("sum has been pressed/");
				score.setText(Currency.calculate());
				 }
				 else if(src==butsearch){
						Log.logIt("search button has been pressed.");
						if(search.getText().equals("")) {isSearch = false;
						paintTable();
						}
						else{
							isSearch = true;
							searchTableContents(search.getText().toUpperCase());
						}
			}
			 }
			 
			 
			 
			 
			 
			 
			 
			 
			 
				/*this func will search the values u are looking for and 
				 * display it on the table.
				 * if you want to see the full table just searc for BLANK search*/
				private void searchTableContents(String searchString) {  
					Log.logIt("searching for similir values as: "+searchString);
					  	modle3.setRowCount(0);					    //To empty the table before search
					  	String str=null;
					  	String str2 = null;
					  for(int i=0;i<Currency.getLength();i++){
						  str = Currency.getLast(Currency.getCountryList(), i);//country.item(i).getLastChild().getNodeValue();
						  str2 = Currency.getLast(Currency.getCountryCode(), i);//code.item(i).getLastChild().getNodeValue();
						  if((str.toUpperCase().equals(searchString))||(str2.toUpperCase().equals(searchString))){
							  modle3.addRow(new Object[]{Currency.getFirst(Currency.getCountryCode(), i),//code.item(i).getFirstChild().getNodeValue(),
									  Currency.getFirst(Currency.getCountryList(),i),//country.item(i).getFirstChild().getNodeValue(),
										Currency.getFirst(Currency.getCountryCode(), i)});//rate.item(i).getFirstChild().getNodeValue()});
						  }  
					  }
					}


				
				
				/*this func is update all the lists and its will
				 * put the values at the table.
				 * this very importent that this func will be synchronized
				 * beacause it could show wrong values.
				 * it can show the right dollar against the wrong EUR*/
			private synchronized void updateLists(){
				Log.logIt("updating lists from Currency.class");
					paintTable();
				}
			 
			
			
			 /*Handle the focus situation when the user
			  * pick one TextField*/
			@Override
			public void focusGained(FocusEvent e) {
					JTextField tt= (JTextField) e.getSource();
					tempJTextFieldText = tt.getText();
					tt.setText(null);
			}
			@Override
			public void focusLost(FocusEvent e) {
				JTextField tt= (JTextField) e.getSource();
				if(tt.getText().equals(null)){
					tt.setText(tempJTextFieldText);	
				}
			}
			
			
			
			

			/*The Thread in syncronized with the Parse.class Thread
			 * that way it will get the update valued after the Parse.class
			 * is reading them.*/
			@Override
			public void run() {
				while(true){
						try {
							Log.logIt("Window.class Thread try to sleep.");
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
						if(isSearch==false){
							Log.logIt("updating lists...");
						updateLists();
						txtpnChooseFromThe.setText("Last update was: "+parse.getLastUpdate());
						}
				}		
			}
			}

