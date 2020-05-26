package il.ac.shenkar.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionProxy extends Thread implements StringConsumer,
		StringProducer
{
	
	@Override
	public void run()
	{
		if (input == null)		//check if we already created the input stream
		{
			try
			{
				input = new DataInputStream(socket.getInputStream());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		while (true)
		{
			try
			{
				if ((buff = input.readUTF()) != null)
				{
					consumer.consume(buff);
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private String buff;
	private String serverName = "127.0.0.1" ;
	private int portNumber = 1200;
	private Socket socket;
	private DataOutputStream output = null;
	private DataInputStream input = null;
	private StringConsumer consumer = null;
	

	public ConnectionProxy(String serverName, int portNumber)
	{
		super();
		setServerName(serverName);
		setPortNumber(portNumber);
		
		try
		{
			socket = new Socket(getServerName(), getPortNumber());
			
			
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	
	public ConnectionProxy()
	{
		try
		{
			socket = new Socket(getServerName(), getPortNumber());
			
			
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public ConnectionProxy(Socket socket)
	{
		try
		{
			setSocket(socket);
			setPortNumber(socket.getPort());
			InetAddress num = socket.getLocalAddress();
			setServerName(num.toString());
		}catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
	}


	public Socket getSocket()
	{
		return socket;
	}

	public void setSocket(Socket socket) throws UnknownHostException
	{
		if (socket !=null)
		{
			this.socket = socket;
		}
		else throw new UnknownHostException("recived NULL Socket");
	}

	public String getServerName()
	{
		return serverName;
	}

	public void setServerName(String serverName)
	{
		if (serverName != null)
		this.serverName = serverName;
		else throw new NullPointerException("serverName is NULL!");
	}

	public int getPortNumber()
	{
		return portNumber;
	}

	public void setPortNumber(int portNumber)
	{
		if (portNumber>1024)
		this.portNumber = portNumber;
		else throw new IllegalArgumentException("Port number is illegal");
	}

	@Override
	public void addConsumer(StringConsumer sc)
	{
		if (sc != null && consumer == null)
		{
			consumer = sc;
		}
	}

	@Override
	public void removeConsumer(StringConsumer sc)
	{
		if (consumer == sc)
		{
			consumer = null;	
		}
	}

	@Override
	public void consume(String str)
	{
		if (output == null)
		{
			try
			{
				output = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		// input and output streams are good
		
		try
		{
			output.writeUTF(str);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		// after we received the str from the GUI we need to sent it to the server, that he would be able to notify all
	}
}
