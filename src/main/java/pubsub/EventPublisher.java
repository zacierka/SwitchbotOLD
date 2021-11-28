package pubsub;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

public class EventPublisher {
    private final String HOST = "localhost";
    private RedisClient client;
    private StatefulRedisPubSubConnection<String, String> con;
    private RedisPubSubCommands<String, String> sync;

    public EventPublisher()
    {
        this.client = RedisClient.create("redis://" + PubSubUtil.getPassword() + "@" + HOST + ":6379/0");
        this.con = client.connectPubSub();

        this.sync = con.sync();
    }

    public void sendMsg(EventInformation event)
    {
        if(event.channel != null || event.value != null)
        {
            sync.publish(event.channel, event.value);
        }
    }
}
