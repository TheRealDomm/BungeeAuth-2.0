package org.endertools.bungeeauth.config;

import lombok.SneakyThrows;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.endertools.database.utility.AccessPoint;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
public class PluginConfiguration {

    public static AccessPoint MY_SQL_DATA;
    public static long SETTING_SESSION_TIMEOUT;
    public static String SETTING_PROXY_API_KEY;
    public static String SETTING_HASH_KEY;
    public static long SETTING_MAX_PROXY_API_REQUEST;
    public static long SETTING_IDLE_TIMEOUT;
    public static int SETTING_MAX_ATTEMPS;
    public static List<String> SETTING_COMMAND_ON_MAX_ATTEMPS;

    @SneakyThrows
    public PluginConfiguration(Plugin plugin) {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        MY_SQL_DATA = new AccessPoint(
                configuration.getString("mysql.host"),
                configuration.getString("mysql.database"),
                configuration.getString("mysql.username"),
                configuration.getString("mysql.password"),
                configuration.getInt("mysql.port")
        );
        SETTING_SESSION_TIMEOUT = configuration.getInt("settings.session-timeout-hours") * 3600000L;
        SETTING_PROXY_API_KEY = configuration.getString("settings.proxy-check-api-key");
        SETTING_HASH_KEY = configuration.getString("settings.hashing-key");
        SETTING_MAX_PROXY_API_REQUEST = configuration.getLong("settings.proxy-check-request-limit");
        SETTING_IDLE_TIMEOUT = configuration.getInt("settings.idle-time-verify-minutes") * 60000L;
        SETTING_MAX_ATTEMPS = configuration.getInt("settings.max-failed-attemps");
        SETTING_COMMAND_ON_MAX_ATTEMPS = configuration.getStringList("settings.commands-on-max-failed");
        new MessageConfiguration(plugin);
    }

}
