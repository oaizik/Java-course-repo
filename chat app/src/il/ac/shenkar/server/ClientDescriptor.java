package il.ac.shenkar.server;

public class ClientDescriptor implements StringConsumer, StringProducer
{

    private StringConsumer consumer;
    private String clientNickName;

    @Override
    public void addConsumer(StringConsumer sc)
    {
        if (sc != null)
        {
            consumer = sc;
        }
    }

    @Override
    public void removeConsumer(StringConsumer sc)
    {
       if (sc != null)
       {
           MessageBoard mb = (MessageBoard) consumer;
           mb.removeConsumer(sc);
           this.consumer = null;
       }
    }

    @Override
    public void consume(String str)
    {
        /* get new message from the connection proxy */
        System.out.println(this.getClass().getSimpleName() + " Consume");
        if (clientNickName == null)            /* mean the user pressed connect the String sent is the user nickName*/
        {
            clientNickName = new String(str);
            consumer.consume(str + " Joined the chat");
        }
        else                                    /* We already have the client nick name */
        {
            consumer.consume(new String("<" + clientNickName + ">" + str));   /* passing the message to the message board*/
        }

    }

    public StringConsumer getConsumer()
    {
        return this.consumer;
    }

}
