package pubsub;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

public class EventSubscriber {
    private RedisClient client;
    private StatefulRedisPubSubConnection<String, String> con;
    private RedisPubSubListener<String, String> listener;

    public EventSubscriber(String channel, IListener listen) {

        String HOST = "localhost";
        this.client = RedisClient.create("redis://" + PubSubUtil.getPassword() + "@" + HOST + ":6379/0");
        this.con = client.connectPubSub();
        this.listener = new RedisPubSubAdapter<String, String>() {

            @Override
            public void message(String channel, String message) {
                listen.recvMsg(new EventInformation(channel, message));
            }
        };

        con.addListener(listener);
        RedisPubSubCommands<String, String> sync = con.sync();
        sync.subscribe(channel);
    }
}
