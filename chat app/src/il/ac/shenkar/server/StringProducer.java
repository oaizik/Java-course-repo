package il.ac.shenkar.server;


public interface StringProducer
{
	 public void addConsumer(StringConsumer sc);
	 public void removeConsumer(StringConsumer sc);

}
