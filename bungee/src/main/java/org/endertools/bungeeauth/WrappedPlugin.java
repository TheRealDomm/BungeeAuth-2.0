package org.endertools.bungeeauth;

import lombok.SneakyThrows;
import net.md_5.bungee.api.plugin.Plugin;
import org.endertools.bungeeauth.config.DependencyConfiguration;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
public class WrappedPlugin extends Plugin {

    private AuthenticatorPlugin authenticatorPlugin;

    @Override
    public void onLoad() {

    }

    @SneakyThrows
    @Override
    public void onEnable() {
        if (getDataFolder() == null || !getDataFolder().exists()) {
            if (!getDataFolder().mkdir()) {
                getLogger().severe("Could not create data folder!");
            }
        }
        new DependencyConfiguration(this);
        authenticatorPlugin = new AuthenticatorPlugin();
        authenticatorPlugin.setPlugin(this);
        authenticatorPlugin.setLogger(this.getLogger());
        authenticatorPlugin.onEnable();
    }

    @Override
    public void onDisable() {
        authenticatorPlugin.onDisable();
    }

}
