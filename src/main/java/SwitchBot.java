import com.github.stackovernorth.jda.commandhandler.api.command.CommandBuilder;
import com.github.stackovernorth.jda.commandhandler.api.handler.CommandHandler;
import com.github.stackovernorth.jda.commandhandler.api.handler.CommandHandlerBuilder;
import commands.general.PummelCommand;
import commands.minecraft.OnlineCommand;
import commands.minecraft.RestartCommand;
import commands.minecraft.ShutdownCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import utility.ClassHelpers;

import javax.security.auth.login.LoginException;

public class SwitchBot {

    public static void main(String[] args) throws LoginException
    {
        JDA jda = JDABuilder.createDefault(ClassHelpers.getProperty("token"))
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.playing(ClassHelpers.getProperty("initialgame")))
                .build();



        CommandHandler commandHandler = new CommandHandlerBuilder(jda)
                .setPrefix(ClassHelpers.getProperty("prefix"))
                .build();

        commandHandler.addCommand(new CommandBuilder("online",
                new OnlineCommand())
                .setAlias("o")
                .setDescription("View players in the Minecraft Server")
                .allowBotReply(false)
                .addAllowedChannel(793278072064835603L)
                .build());

        commandHandler.addCommand(new CommandBuilder("shutdown",
                new ShutdownCommand(jda))
                .setDescription("Shutdown the server (Save and Shutdown)")
                .allowBotReply(false)
                .addAllowedChannel(793278072064835603L)
                .addPermission(Permission.ADMINISTRATOR)
                .build());

        commandHandler.addCommand(new CommandBuilder("restart",
                new RestartCommand(jda))
                .setDescription("Restart the Server (Save and Restart)")
                .allowBotReply(false)
                .addAllowedChannel(793278072064835603L)
                .addPermission(Permission.ADMINISTRATOR)
                .build());

        commandHandler.addCommand(new CommandBuilder("pummel",
                new PummelCommand())
                .setDescription("View Pummel Leaderboards")
                .allowBotReply(false)
                .build());
    }
}
