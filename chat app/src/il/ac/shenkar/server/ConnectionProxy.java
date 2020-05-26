package il.ac.shenkar.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionProxy extends Thread implements StringConsumer, StringProducer
{
    private String buff;
    private String serverName = "127.0.0.1";
    private int portNumber = 1300;
    private Socket socket;
    private DataOutputStream output = null;
    private DataInputStream dataInputStream = null;
    private StringConsumer consumer = null;
    private boolean wasClosed = false;

    /* this two first ctor are never being used in the server side, we received them from the Ex definition*/
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
        } catch (IOException e)
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
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Create new Connection Proxy class with an open socket to a specific client
     * @param socket  open connection to a specific client.
     */
    public ConnectionProxy(Socket socket)
    {
        try
        {
            setSocket(socket);
            setPortNumber(socket.getPort());
            InetAddress num = socket.getLocalAddress();
            setServerName(num.toString());
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        } catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        /*
        * This code will run in a different Thread
        * that is responsible to listen to new messages from the client
        * */

        if (dataInputStream == null)       //check if we already created the dataInputStream stream
        {
            try
            {
                /* Create new Data input stream from the client input stream */
                dataInputStream = new DataInputStream(socket.getInputStream());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        /* wait for new messages to arrive from the client */
        while (socket.isConnected())
        {
            try
            {
                if ((buff = dataInputStream.readUTF()) != null)
                {
                    /* New message has arrived from the client and the content of it is inside buff*/
                    consumer.consume(buff);     /* pass the message to the ClientDescriptor (consumer = ClientDescriptor) */
                }
            } catch (IOException e)
            {
                // we received IO Exception - need to remove the
                System.out.println("Connection was closed");
                removeConsumer(consumer);
                closeStreams();
                wasClosed = true;
                break;              /*Exit the while loop*/
            }
        }
    }

    private void closeStreams()
    {
        if (dataInputStream != null)
        {
            try
            {
                dataInputStream.close();
            } catch (IOException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        if (output != null)
        {
            try
            {
                output.close();
            } catch (IOException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        if (socket != null)
        {
            try
            {
                socket.close();
            } catch (IOException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    public Socket getSocket()
    {
        return socket;
    }

    public void setSocket(Socket socket) throws UnknownHostException
    {
        if (socket != null)
        {
            this.socket = socket;
        } else throw new UnknownHostException("recived NULL Socket");
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
        if (portNumber > 1024)
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
        if (sc != null)
        {
            ClientDescriptor clientDescriptor = (ClientDescriptor) sc;
            clientDescriptor.removeConsumer(this);              /* remove the connection proxy from the message board*/
            this.consumer = null;
        }
    }

    @Override
    public void consume(String str)
    {
        /* New message received from the message board --> pass it to the client*/
        System.out.println(this.getClass().getSimpleName() +" Consume");
        if (!wasClosed)
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
            // if we are here the output and output streams are good

            try
            {
                output.writeUTF(str);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Does not write to the connection, it was closed ");
        }

        // after we received the str from the GUI we need to sent it to the server, that he would be able to notify all
    }
}
