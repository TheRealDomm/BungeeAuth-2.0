package org.endertools.bungeeauth.command.sub;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.endertools.bungeeauth.AuthenticatorPlugin;
import org.endertools.bungeeauth.command.SubCommand;
import org.endertools.bungeeauth.config.MessageConfiguration;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
public class ReloadSubCommand extends SubCommand {

    public ReloadSubCommand(String permission) {
        super(permission);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        super.execute(commandSender, args);
        if (!(commandSender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        AuthenticatorPlugin.getInstance().reload();
        MessageConfiguration.sendMessage(player, "message.reload-success");
    }

}
