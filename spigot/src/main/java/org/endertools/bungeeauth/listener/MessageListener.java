package org.endertools.bungeeauth.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.endertools.bungeeauth.AuthenticatorPlugin;

import java.util.UUID;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
public class MessageListener implements PluginMessageListener {

    private final AuthenticatorPlugin plugin;

    public MessageListener(AuthenticatorPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin,
                "auth", this::onPluginMessageReceived);
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin,
                "auth", this::onPluginMessageReceived);
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        plugin.getLogger().info("Receiving on channel " + s);
        if (!s.equals("auth")) return;
        ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
        String subChannel = input.readUTF();
        if (subChannel.equals("freeze")) {
            UUID uuid = UUID.fromString(input.readUTF());
            if (plugin.getBlockedPlayer().contains(uuid)) return;
            plugin.getBlockedPlayer().add(uuid);
            plugin.getLogger().info("Freezed player '" + uuid.toString() + "'");
        }
        if (subChannel.equals("unfreeze")) {
            UUID uuid = UUID.fromString(input.readUTF());
            if (!plugin.getBlockedPlayer().contains(uuid)) return;
            plugin.getBlockedPlayer().remove(uuid);
            plugin.getLogger().info("Unfreezed player '" + uuid.toString() + "'");
        }
    }

}
