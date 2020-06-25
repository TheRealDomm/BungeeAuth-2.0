package org.endertools.bungeeauth.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.endertools.bungeeauth.AuthenticatorPlugin;
import org.endertools.bungeeauth.config.MessageConfiguration;

import java.util.Arrays;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
public class AuthBaseCommand extends Command {

    private final AuthenticatorPlugin plugin;

    public AuthBaseCommand(AuthenticatorPlugin plugin) {
        super("auth", "bungeeauth.command.auth", "bauth", "bungeeauth");
        this.plugin = plugin;
    }


    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (strings.length == 0) {
            plugin.getSubCommandPool().get("help").execute(commandSender, strings);
        }
        else {
            String subCommand = strings[0];
            int authCode;
            try {
                authCode = Integer.parseInt(subCommand);
            } catch (NumberFormatException e) {
                if (plugin.getSubCommandPool().get(subCommand) != null) {
                    plugin.getSubCommandPool().get(subCommand).execute(commandSender, strings);
                    return;
                }
                plugin.getSubCommandPool().get("help").execute(commandSender, strings);
                return;
            }
            if (authCode >= 1000000) {
                MessageConfiguration.sendMessages(player, "message.failed-code-too-long", authCode);
                return;
            }
            plugin.getSubCommandPool().get("code").execute(commandSender, new String[]{String.valueOf(authCode)});
        }
    }

}
