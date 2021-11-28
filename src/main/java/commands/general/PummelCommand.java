package commands.general;

import com.github.stackovernorth.jda.commandhandler.listener.CommandListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import utility.ClassHelpers;
import utility.DatabaseHelper;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class PummelCommand implements CommandListener {

    @Override
    public void onCommand(Member member, TextChannel textChannel, Message message, String[] strings) {
        if(strings.length != 0)
        {
            if(member.isOwner()) {
                String winner = strings[1];
                if (strings[0].equals("winner")) {
                    try {
                        DatabaseHelper.updatePummelLeaderboards(winner);
                        message.addReaction("U+1F3C6").queue(); //Trophy Emote
                    } catch (SQLException e) {
                        message.addReaction(ClassHelpers.CROSSMARK_EMOTE).queue(); //Trophy Emote
                    }
                }
            }
            return;
        }

        Map<String, Integer> sortedlist = null;
        try {
            sortedlist = DatabaseHelper.getPummelLeaderboards()
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer> comparingByValue().reversed())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (sortedlist != null) {
            EmbedBuilder embed = new EmbedBuilder();
            StringBuilder stringBuilder = new StringBuilder("");
            for (Map.Entry<String, Integer> en : sortedlist.entrySet()) {
                String user = en.getKey();
                String score = String.valueOf(en.getValue());
                String line = String.format("%s: %s%n", user, score);
                stringBuilder.append(line);
            }
            embed.setTitle("Pummel Party Leaderboards")
                            .setDescription(stringBuilder.toString())
                                    .setColor(Color.MAGENTA);
            textChannel.sendMessage(embed.build()).queue();
        } else {
            textChannel.sendMessage("Couldnt get Leaderboard").queue();
        }
    }
}

