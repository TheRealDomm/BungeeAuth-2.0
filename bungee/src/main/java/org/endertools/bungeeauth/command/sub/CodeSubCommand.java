package org.endertools.bungeeauth.command.sub;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.endertools.bungeeauth.AuthenticatorPlugin;
import org.endertools.bungeeauth.command.SubCommand;
import org.endertools.bungeeauth.config.MessageConfiguration;
import org.endertools.bungeeauth.config.PluginConfiguration;
import org.endertools.bungeeauth.util.AuthUserInfo;
import org.endertools.bungeeauth.util.GoogleAuthUtil;
import org.endertools.bungeeauth.util.HashUtil;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
public class CodeSubCommand extends SubCommand {

    public CodeSubCommand(String permission) {
        super(permission);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        super.execute(commandSender, args);
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (!AuthenticatorPlugin.getInstance().getAuthRepository().isRegistered(player.getUniqueId())) {
            MessageConfiguration.sendMessage(player, "message.registration-needed");
            return;
        }
        AuthUserInfo info = AuthenticatorPlugin.getInstance().getAuthRepository().getUser(player.getUniqueId());
        if (info.getLastLogin() + PluginConfiguration.SETTING_SESSION_TIMEOUT > System.currentTimeMillis()) {
            MessageConfiguration.sendMessage(player, "message.already-loggedin");
            return;
        }
        if (info.getLastIp().equals(player.getAddress().getAddress().getHostAddress())) {
            MessageConfiguration.sendMessage(player, "message.already-loggedin");
            return;
        }
        HashUtil hashUtil = new HashUtil();
        GoogleAuthUtil googleAuthUtil = new GoogleAuthUtil();
        int code = Integer.parseInt(args[0]);
        String key = hashUtil.deserialize(info.getAuthKey());
        if (googleAuthUtil.isValid(key, code)) {
            MessageConfiguration.sendMessage(player, "message.success", player.getName());
            AuthenticatorPlugin.getInstance().getAuthUtil().removeAuth(player.getUniqueId());
            info.setName(player.getName());
            info.setLogins(info.getLogins()+1);
            info.setLastLogin(System.currentTimeMillis());
            info.setLastIp(player.getAddress().getAddress().getHostAddress());
            AuthenticatorPlugin.getInstance().getAuthRepository().updateUser(info);
            unFreezePlayer(player);
            return;
        }
        info.setFailedLogins(info.getFailedLogins()+1);
        AuthenticatorPlugin.getInstance().getAuthRepository().updateUser(info);
        MessageConfiguration.sendMessage(player, "message.failed", code);
    }

    private void unFreezePlayer(ProxiedPlayer player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("unfreeze");
        output.writeUTF(player.getUniqueId().toString());
        player.getServer().getInfo().sendData("auth", output.toByteArray());
        AuthenticatorPlugin.getInstance().getLogger().info("Unfreezing player '" + player.getUniqueId().toString() + "'...");
    }

}
