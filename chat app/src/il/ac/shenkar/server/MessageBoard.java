package il.ac.shenkar.server;

import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessageBoard implements StringConsumer, StringProducer
{
	private JTextArea board;
	private JScrollPane scroll;
	private JFrame frame;
	private LinkedList<StringConsumer> consumersList;
	

	public MessageBoard()
	{
		super();
		consumersList = new LinkedList<StringConsumer>();
		board = new JTextArea();
		scroll = new JScrollPane(board);
		
		frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(700,500);
		frame.add(scroll);
		board.setLineWrap(true);
		board.setEditable(false);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
	}

	@Override
	public void addConsumer(StringConsumer sc)
	{
		consumersList.add(sc);
	}

	@Override
	public void removeConsumer(StringConsumer sc)
	{
        consumersList.remove(sc);
	}

	@Override
	public void consume(String str)
	{
        /* new message received from the Client descriptor*/
        System.out.println(this.getClass().getSimpleName() +" Consume");
        /*add the massage to the board*/
        board.append(str+ "\n");

        /* send the message to all of my consumers*/
        if (consumersList != null)
        {
            for (StringConsumer ob: consumersList)
            {
                ob.consume(str);        /* The consumers are the connection Proxy (mean the clients)*/
            }
        }

	}

}
