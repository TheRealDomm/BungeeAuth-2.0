package org.endertools.bungeeauth.listener;

import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.endertools.bungeeauth.AuthenticatorPlugin;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
public class PluginChannelMessageListener implements Listener {

    private final AuthenticatorPlugin plugin;

    public PluginChannelMessageListener(AuthenticatorPlugin plugin) {
        this.plugin = plugin;
        plugin.getPlugin().getProxy().registerChannel("auth");
    }

    @EventHandler
    public void onChannelMessageReceive(PluginMessageEvent event) {
        if (event.getTag().equals("unblock_auth_player")) {
            //unhandled!
        }
        if (event.getTag().equals("block_auth_player")) {
            //unhandled!
        }
    }

}
