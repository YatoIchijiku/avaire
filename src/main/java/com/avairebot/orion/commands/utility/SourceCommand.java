package com.avairebot.orion.commands.utility;

import com.avairebot.orion.Orion;
import com.avairebot.orion.commands.CommandContainer;
import com.avairebot.orion.commands.CommandHandler;
import com.avairebot.orion.contracts.commands.AbstractCommand;
import com.avairebot.orion.factories.MessageFactory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Collections;
import java.util.List;

public class SourceCommand extends AbstractCommand {

    private final String rootUrl = "https://github.com/AvaIre/Orion";
    private final String commandUrl = "https://github.com/AvaIre/Orion/tree/master/src/main/java/com/avairebot/orion/commands/%s/%s.java";

    public SourceCommand(Orion orion) {
        super(orion);
    }

    @Override
    public String getName() {
        return "Source Command";
    }

    @Override
    public String getDescription() {
        return "Gives you the source code for the Bot, or the code for a given command.";
    }

    @Override
    public List<String> getTriggers() {
        return Collections.singletonList("source");
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        if (args.length == 0) {
            MessageFactory.makeInfo(event.getMessage(), "Orion source code:\n\n%s", rootUrl).queue();
            return;
        }

        CommandContainer command = CommandHandler.getCommand(args[0]);
        if (command == null) {
            MessageFactory.makeInfo(event.getMessage(), "Invalid command given, here is the full source code instead.\n\n%s", rootUrl).queue();
            return;
        }

        String[] split = command.getCommand().getClass().toString().split("\\.");
        String category = split[split.length - 2];
        String name = split[split.length - 1];

        MessageFactory.makeInfo(event.getMessage(), "AvaIre source code for the **%s** command:\n\n" + commandUrl,
                command.getCommand().getName(), category, name
        ).queue();
    }
}
