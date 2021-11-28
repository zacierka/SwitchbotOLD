package commands.minecraft;

import com.github.stackovernorth.jda.commandhandler.listener.CommandListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import utility.ClassHelpers;
import utility.DatabaseHelper;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class OnlineCommand implements CommandListener {

    @Override
    public void onCommand(Member member, TextChannel textChannel, Message message, String[] strings) {
        List<String> players = null;
        try {
            players = DatabaseHelper.getOnlinePlayers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        EmbedBuilder embed = null;
        if(players != null)
        {
            embed = new EmbedBuilder()
                    .setTitle("**Players Online: " + players.size() + "**")
                    .addField("Players", cleanList(players), false)
                    .setFooter(ClassHelpers.getProperty("serverversion"));
        } else
        {
            embed = new EmbedBuilder()
                    .setTitle("No Players Online")
                    .setColor(Color.GRAY);
        }
        textChannel.sendMessage(embed.build()).queue();
    }

    private String cleanList(List<String> players)
    {
        StringBuilder strbld = new StringBuilder("");

        for (int i = 0; i < players.size(); i++)
        {
            if(i < players.size() - 1)
            {
                strbld.append(players.get(i) + "\n");
            } else
            {
                strbld.append(players.get(i));
            }
        }

        return strbld.toString();
    }

}
