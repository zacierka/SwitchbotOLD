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

public class ShutdownCommand extends EventListener implements CommandListener {

    private final EventPublisher eventPublisher;
    private JDA jda = null;
    private String author;

    public ShutdownCommand(JDA jda) {
        this.jda = jda;
        EventSubscriber eventSubscriber = new EventSubscriber(TopicConstants.SHUTDOWNRESPONSE, this);
        this.eventPublisher = new EventPublisher();
    }

    @Override
    public void onCommand(Member member, TextChannel textChannel, Message message, String[] strings) {
        this.author = member.getEffectiveName();
        eventPublisher.sendMsg(new EventInformation(TopicConstants.SHUTDOWNREQUEST, "request"));
    }

    @Override
    public void recvMsg(EventInformation event) {
        String status = event.value;
        Color stat = Color.GRAY;
        if(status.equals("ERROR")) {
            stat = Color.RED;
        } else if(status.equals("SUCCESS")) {
            stat = Color.GREEN;
        }

        Objects.requireNonNull(Objects.requireNonNull(
                jda.getGuildById(ClassHelpers.getProperty("guildid")))
                        .getTextChannelById(ClassHelpers.getProperty("minecraftchannel")))
                .sendMessage(new EmbedBuilder()
                    .setTitle("Shutdown Request")
                    .setAuthor(author)
                    .setColor(stat)
                    .setDescription("Server Status: " + (status.equals("SUCCESS") ? "OFFLINE" : "ERROR"))
                    .build())
                .queue();

    }
}
