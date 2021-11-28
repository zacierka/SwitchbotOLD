package pubsub;

public interface IListener {

    void recvMsg(EventInformation event);
}
