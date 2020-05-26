package il.ac.shenkar.client;



import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.UnknownHostException;
import java.util.LinkedList;
import javax.swing.*;

public class ClientGUI implements StringConsumer, StringProducer, ActionListener
{
	// members
	private String clientNickName = "Default";
	

	private JFrame frame;
	private JPanel topPanel, bottomPanel;
	private JScrollPane scroll;
	private JButton connect, send;
	private JTextField massageTextField;
	private JTextArea mainTextArea;
	private JLabel serverIP, portNumber;
	private StringConsumer consumer = null;
	
	//c'tor
	
	public ClientGUI()
	{
		frame = new JFrame("Chat Room");
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		mainTextArea = new JTextArea();
		scroll = new JScrollPane(mainTextArea);
		connect = new JButton("Connect");
		send = new JButton("sent");
		massageTextField = new JTextField(40);
		serverIP = new JLabel("Server IP: ");
		portNumber = new JLabel("Port Number: ");		
		
	
	}
	
	public void go()
	{
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing (WindowEvent e)
			{
				System.exit(0);
			}
		});
		
		connect.addActionListener(this);
		send.addActionListener(this);
		massageTextField.addActionListener(this);
		
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setSize(700,500);
		
		// set top panel
		frame.add(BorderLayout.NORTH, topPanel);
		topPanel.setLayout(new GridLayout(1,3));
		topPanel.add(connect);
		topPanel.add(serverIP);
		topPanel.add(portNumber);
		// set bottom panel
		frame.add(BorderLayout.SOUTH, bottomPanel);
		bottomPanel.add(massageTextField);
		bottomPanel.add(send);
		
		frame.add(BorderLayout.CENTER, scroll);
		mainTextArea.setLineWrap(true);
		mainTextArea.setEditable(false);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
	}
	public String getClientNickName()
	{
		return clientNickName;
	}

	public void setClientNickName(String clientNickName)
	{
		this.clientNickName = clientNickName;
	}


	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == connect)
		{
			ConnectionProxy proxy  = new ConnectionProxy();
			proxy.addConsumer(this);
			this.addConsumer(proxy);
			consumer.consume(new String (this.getClientNickName()));
			this.serverIP.setText(this.serverIP.getText()+proxy.getServerName());
			this.portNumber.setText(this.portNumber.getText()+ proxy.getPortNumber());
			proxy.start();
			
			// add the connection proxy to the string consumer connection
			System.out.println("CONNECT Button Presed");
		}
		else if (e.getSource() == send ||e.getSource() == massageTextField )
		{
			// check if the user in connected
			if (consumer == null)
			{
				mainTextArea.append("you shall connect first"+"\n");
				massageTextField.setText("");
				return;
			}
			else
			{
				String newMassage = massageTextField.getText();
				massageTextField.setText("");
				System.out.println(newMassage);
				System.out.println("SEND Button Presed");
				consumer.consume(newMassage);
			}
			
			// write code that will run when press the SEND button
		}
	
	}
	
	

	@Override
	public void addConsumer(StringConsumer sc)
	{
		if (sc != null)
		{
			this.consumer = sc;
		}

	}

	@Override
	public void removeConsumer(StringConsumer sc)
	{
		
		// TODO Auto-generated method stub

	}

	@Override
	public void consume(String str)
	{
		if (str != null)
		{
			mainTextArea.append("\n");
			mainTextArea.append(str);
		}
	}

	/**
	 * @param args
	 */
	
	public static void main(String[] args)
	{
         if (args.length < 1)
         {
             System.out.println("Not enough arguments... Please enter your nick name");
             System.exit(1);
         }
        final String userName = args[0];
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                ClientGUI demo = new ClientGUI();
                demo.setClientNickName(userName);
                demo.go();            }
        });
 	}



}
