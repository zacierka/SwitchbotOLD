package commands.minecraft;

import com.github.stackovernorth.jda.commandhandler.listener.CommandListener;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import pubsub.EventInformation;
import pubsub.EventListener;
import pubsub.EventPublisher;
import pubsub.EventSubscriber;
import utility.TopicConstants;

public class ExecuteCommand extends EventListener implements CommandListener  {
    private EventPublisher eventPublisher;
    private TextChannel channel;
    @Override
    public void onCommand(Member member, TextChannel textChannel, Message message, String[] strings) {
        EventSubscriber eventSubscriber = new EventSubscriber(TopicConstants.EXECUTERERESPONSE, this);
        this.eventPublisher = new EventPublisher();
    }

    @Override
    public void recvMsg(EventInformation event) {

    }
}
