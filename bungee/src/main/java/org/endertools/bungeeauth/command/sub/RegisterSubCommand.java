package org.endertools.bungeeauth.command.sub;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.endertools.bungeeauth.AuthenticatorPlugin;
import org.endertools.bungeeauth.command.SubCommand;
import org.endertools.bungeeauth.config.MessageConfiguration;
import org.endertools.bungeeauth.util.AuthUserInfo;
import org.endertools.bungeeauth.util.GoogleAuthUtil;
import org.endertools.bungeeauth.util.HashUtil;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
public class RegisterSubCommand extends SubCommand {

    public RegisterSubCommand(String permission) {
        super(permission);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        super.execute(commandSender, args);
        if (!(commandSender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (AuthenticatorPlugin.getInstance().getAuthRepository().isRegistered(player.getUniqueId())) {
            MessageConfiguration.sendMessage(player, "message.already-registered");
            return;
        }
        GoogleAuthUtil googleAuthUtil = new GoogleAuthUtil();
        HashUtil hashUtil = new HashUtil();
        String key = googleAuthUtil.createKey();
        String hashedKey = hashUtil.serialize(key);
        AuthUserInfo info = new AuthUserInfo(player.getUniqueId(), hashedKey);
        info.setName(player.getName());
        AuthenticatorPlugin.getInstance().getAuthRepository().addUser(info);
        MessageConfiguration.sendMessages(player, "message.code", key);
    }

}
