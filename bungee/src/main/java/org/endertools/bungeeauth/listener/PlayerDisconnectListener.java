package org.endertools.bungeeauth.listener;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.endertools.bungeeauth.AuthenticatorPlugin;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
@RequiredArgsConstructor
public class PlayerDisconnectListener implements Listener {

    private final AuthenticatorPlugin plugin;

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        if (plugin.getAuthUtil().isAuth(event.getPlayer().getUniqueId())) {
            plugin.getAuthUtil().removeAuth(event.getPlayer().getUniqueId());
        }
    }

}
