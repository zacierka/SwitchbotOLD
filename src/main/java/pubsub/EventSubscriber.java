package pubsub;

import io.lettuce.core.RedisClient;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

public class EventSubscriber {
    private RedisClient client;
    private static StatefulRedisPubSubConnection<String, String> CONNECTION;

    public EventSubscriber(String channel, IListener listen) {
        getCONNECTION();

        RedisPubSubListener<String, String> listener = new RedisPubSubAdapter<String, String>() {

            @Override
            public void message(String channel, String message) {
                listen.recvMsg(new EventInformation(channel, message));
            }
        };

        CONNECTION.addListener(listener);
        RedisPubSubCommands<String, String> sync = CONNECTION.sync();
        sync.subscribe(channel);
    }

    private static void getCONNECTION()
    {
        if(CONNECTION == null)
        {
            RedisClient client = RedisClient.create("redis://" + PubSubUtil.getPassword() + "@localhost:6379/0");
            CONNECTION = client.connectPubSub();
            System.out.println("Subscriber Connected to Redis");
        }
    }

}