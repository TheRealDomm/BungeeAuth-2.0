package org.endertools.bungeeauth.command.sub;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.endertools.bungeeauth.command.SubCommand;
import org.endertools.bungeeauth.config.MessageConfiguration;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
public class HelpSubCommand extends SubCommand {

    public HelpSubCommand(String permission) {
        super(permission);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        super.execute(commandSender, args);
        if (!(commandSender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (commandSender.hasPermission("bungeeauth.help.admin")) {
            MessageConfiguration.sendMessages(player, "message.help.admin");
        }
        MessageConfiguration.sendMessages(player, "message.help.user");
    }

}
