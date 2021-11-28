package pubsub;

public abstract class EventListener implements IListener {

    public abstract void recvMsg(EventInformation event);
}
