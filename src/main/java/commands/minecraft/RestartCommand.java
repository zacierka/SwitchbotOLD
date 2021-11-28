package commands.minecraft;

import com.github.stackovernorth.jda.commandhandler.listener.CommandListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import pubsub.EventInformation;
import pubsub.EventListener;
import pubsub.EventPublisher;
import pubsub.EventSubscriber;
import utility.ClassHelpers;
import utility.TopicConstants;

import java.awt.*;
import java.util.Objects;

public class RestartCommand extends EventListener implements CommandListener {
    private JDA jda = null;
    private final EventPublisher eventPublisher;
    private String msgID = "";

    public RestartCommand(JDA jda) {
        this.jda = jda;
        EventSubscriber eventSubscriber = new EventSubscriber(TopicConstants.RESTARTRESPONSE, this);
        this.eventPublisher = new EventPublisher();
    }

    @Override
    public void onCommand(Member member, TextChannel textChannel, Message message, String[] strings) {
        eventPublisher.sendMsg(new EventInformation(TopicConstants.RESTARTREQUEST, "request"));
        message.addReaction("U+1F4AD").queue();
        this.msgID = message.getId();
    }

    @Override
    public void recvMsg(EventInformation event) {
        if (!msgID.isEmpty())
        {
            String reaction = event.value.equals("SUCCESS") ? "U+2705" : "U+274C";
            Objects.requireNonNull(Objects.requireNonNull(
                            jda.getGuildById(ClassHelpers.getProperty("guildid")))
                    .getTextChannelById(ClassHelpers.getProperty("minecraftchannel")))
                    .retrieveMessageById(msgID).queue(m -> {
                        m.clearReactions().queue(r -> {
                            m.addReaction(reaction).queue();
                        });

                        this.msgID = "";
            });
        } else {
            Objects.requireNonNull(Objects.requireNonNull(
                            jda.getGuildById(ClassHelpers.getProperty("guildid")))
                    .getTextChannelById(ClassHelpers.getProperty("minecraftchannel")))
                    .sendMessage(new EmbedBuilder()
                            .setTitle("Server Restarted")
                            .setColor(Color.GREEN)
                            .build())
                    .queue();
        }
    }
}
