package org.endertools.bungeeauth;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.endertools.bungeeauth.listener.MessageListener;
import org.endertools.bungeeauth.listener.PlayerBlockListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
@Getter
public class AuthenticatorPlugin extends JavaPlugin {

    private List<UUID> blockedPlayer = new ArrayList<>();

    @Override
    public void onEnable() {
        new MessageListener(this);
        getServer().getPluginManager().registerEvents(new PlayerBlockListener(this), this);
    }

}
