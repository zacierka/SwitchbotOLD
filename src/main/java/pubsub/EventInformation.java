package pubsub;

public class EventInformation {

    public String channel;
    public String value;

    public EventInformation(String channel, String value)
    {
        this.channel = channel;
        this.value = value;
    }
}
