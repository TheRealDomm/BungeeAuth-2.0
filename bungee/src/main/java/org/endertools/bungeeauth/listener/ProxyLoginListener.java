package org.endertools.bungeeauth.listener;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.endertools.bungeeauth.AuthenticatorPlugin;
import org.endertools.bungeeauth.config.MessageConfiguration;
import org.endertools.bungeeauth.config.PluginConfiguration;
import org.endertools.bungeeauth.util.AuthUserInfo;
import org.endertools.bungeeauth.util.ProxyCheck;
import org.endertools.bungeeauth.util.ProxyCheckLog;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
@RequiredArgsConstructor
public class ProxyLoginListener implements Listener {

    private final AuthenticatorPlugin plugin;

    @EventHandler
    public void onLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (player.hasPermission("bungeeauth.command.auth")) {
            if (!plugin.getAuthRepository().isRegistered(player.getUniqueId())) {
                plugin.getAuthUtil().addAuth(player.getUniqueId());
                MessageConfiguration.sendMessage(player, "message.registration-needed");
            }
            else {
                AuthUserInfo info = plugin.getAuthRepository().getUser(player.getUniqueId());
                if (info.getLastLogin() + PluginConfiguration.SETTING_SESSION_TIMEOUT <= System.currentTimeMillis()) {
                    performProxyCheck(player);
                }
                else if (info.getLastIp().equals(player.getAddress().getAddress().getHostAddress()) &&
                        info.getLastLogin() < System.currentTimeMillis() + PluginConfiguration.SETTING_SESSION_TIMEOUT) {
                    MessageConfiguration.sendMessage(player, "message.auto-login-success", player.getName());
                }
                else {
                    performProxyCheck(player);
                }
            }
        }
    }

    private void performProxyCheck(ProxiedPlayer player) {
        if (!plugin.getCheckRepository().isRequestLimit()) {
            ProxyCheckLog log = new ProxyCheck(PluginConfiguration.SETTING_PROXY_API_KEY).createLog(player);
            plugin.getLogRepository().addLog(log);
            plugin.getCheckRepository().addRequest();
        }
        plugin.getAuthUtil().addAuth(player.getUniqueId());
        MessageConfiguration.sendMessage(player, "message.login");
    }

}
