package org.endertools.bungeeauth.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.endertools.bungeeauth.AuthenticatorPlugin;
import org.endertools.bungeeauth.config.MessageConfiguration;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
@RequiredArgsConstructor
public class ServerSwitchListener implements Listener {

    private final AuthenticatorPlugin plugin;

    @EventHandler
    public void onServerSwitch(ServerConnectEvent event) {
        if (!plugin.getAuthUtil().isAuth(event.getPlayer().getUniqueId())) return;
        if (event.getReason().equals(ServerConnectEvent.Reason.JOIN_PROXY)) return;
        event.setCancelled(true);
        MessageConfiguration.sendMessage(event.getPlayer(), "message.login");
    }

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent event) {
        if (!plugin.getAuthUtil().isAuth(event.getPlayer().getUniqueId())) return;
        freezePlayer(event.getPlayer());
    }

    private void freezePlayer(ProxiedPlayer player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("freeze");
        output.writeUTF(player.getUniqueId().toString());
        player.getServer().getInfo().sendData("auth", output.toByteArray());
        plugin.getLogger().info("Freezing player '" + player.getUniqueId().toString() + "'...");
    }

}
