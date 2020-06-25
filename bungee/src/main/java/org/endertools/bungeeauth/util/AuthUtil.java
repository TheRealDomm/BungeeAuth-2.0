package org.endertools.bungeeauth.util;

import lombok.NoArgsConstructor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.endertools.bungeeauth.AuthenticatorPlugin;
import org.endertools.bungeeauth.config.PluginConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
@NoArgsConstructor
public class AuthUtil {

    private List<UUID> awaitAuth = new ArrayList<>();

    public boolean isAuth(UUID uuid) {
        return awaitAuth.contains(uuid);
    }

    public void addAuth(UUID uuid) {
        if (awaitAuth.contains(uuid)) return;
        awaitAuth.add(uuid);
        AuthenticatorPlugin.getInstance().getPlugin().getProxy().getScheduler().schedule(
                AuthenticatorPlugin.getInstance().getPlugin(), () -> {
            if (isAuth(uuid)) {
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
                if (player != null) {
                    player.disconnect(TextComponent.fromLegacyText("You got kicked from the server."));
                }
            }
        }, PluginConfiguration.SETTING_IDLE_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void removeAuth(UUID uuid) {
        awaitAuth.remove(uuid);
    }

}
