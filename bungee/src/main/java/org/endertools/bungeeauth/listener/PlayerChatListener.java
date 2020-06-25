package org.endertools.bungeeauth.listener;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.endertools.bungeeauth.AuthenticatorPlugin;
import org.endertools.bungeeauth.config.MessageConfiguration;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
@RequiredArgsConstructor
public class PlayerChatListener implements Listener {

    private final AuthenticatorPlugin plugin;

    @EventHandler
    public void onChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (plugin.getAuthUtil().isAuth(player.getUniqueId())) {
            if (event.isProxyCommand()) {
                if (event.getMessage().toLowerCase().startsWith("/bauth") ||
                        event.getMessage().toLowerCase().startsWith("/auth") ||
                        event.getMessage().toLowerCase().startsWith("/bungeeauth")) return;
            }
            event.setCancelled(true);
            MessageConfiguration.sendMessage(player, "message.login");
            return;
        }
    }

}
