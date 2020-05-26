package il.ac.shenkar.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NickWindow extends Thread implements ActionListener
{
	private JFrame nickFrame;
	private JPanel nickPanel;
	private JTextField nickfield;
	private JButton nickButton;
	private String NickName = "Default";
	
	public NickWindow()
	{
		nickFrame = new JFrame("Enter your Nick name");
		nickPanel = new JPanel();
		nickfield = new JTextField(10);
		nickButton = new JButton("OK");
		
		nickFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing (WindowEvent e)
			{
				System.exit(0);
			}
		});
		
		nickFrame.setVisible(true);
		nickPanel.add(nickfield);
		nickPanel.add(nickButton);
		nickFrame.add(nickPanel);
		nickFrame.setSize(300,300);
		
		nickfield.addActionListener(this);
		nickButton.addActionListener(this);
	}

	@Override
	public void run()
	{
		System.out.println("before the While loop");
		//while (NickName.equals("Default"))
		{
			System.out.println("nickname = Default");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		NickName= nickfield.getText();
		nickFrame.setVisible(false);
	}

	public String getNickName()
	{
		return NickName;
	}

	public void setNickName(String nickName)
	{
		NickName = nickName;
	}

}