package pubsub;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

public class EventPublisher {
    private final String HOST = "localhost";
    private static StatefulRedisPubSubConnection<String, String> CONNECTION;
    private final RedisPubSubCommands<String, String> sync;

    public EventPublisher()
    {
        getCONNECTION();
        this.sync = CONNECTION.sync();
    }

    public void sendMsg(EventInformation event)
    {
        if(event.channel != null || event.value != null)
        {
            sync.publish(event.channel, event.value);
        }
    }

    private static void getCONNECTION()
    {
        if(CONNECTION == null)
        {
            RedisClient client = RedisClient.create("redis://" + PubSubUtil.getPassword() + "@localhost:6379/0");
            CONNECTION = client.connectPubSub();
            System.out.println("Publisher Connected to Redis");
        }
    }
}
