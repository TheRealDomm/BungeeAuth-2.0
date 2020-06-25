package org.endertools.bungeeauth.config;

import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
public class MessageConfiguration {

    public static HashMap<String, String> MESSAGES = new HashMap<>();
    public static HashMap<String, List<String>> LIST_MESSAGES = new HashMap<>();
    public static String PREFIX;

    @SneakyThrows
    public MessageConfiguration(Plugin plugin) {
        MESSAGES.clear();
        LIST_MESSAGES.clear();
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream("messages.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        PREFIX = configuration.getString("prefix");
        LIST_MESSAGES.put("message.help.admin", configuration.getStringList("message.help.admin"));
        LIST_MESSAGES.put("message.help.user", configuration.getStringList("message.help.user"));
        LIST_MESSAGES.put("message.code", configuration.getStringList("message.code"));
        configuration.getSection("message").getKeys().forEach(key -> {
            if (!key.equals("help") || !key.equals("code")) {
                MESSAGES.put("message." + key, configuration.getString("message." + key));
            }
        });
    }

    public static List<String> getMessageList(String key, Object... replace) {
        List<String> messages = LIST_MESSAGES.getOrDefault(key, new ArrayList<>());;
        for (int i = 0; i < messages.size(); i++) {
            String message = messages.get(i);
            message = message.replaceAll("%prefix%", PREFIX);
            message = String.format(message, replace);
            messages.set(i, message);
        }
        return messages;
    }

    public static String getMessage(String key, Object... replace) {
        String message = MESSAGES.getOrDefault(key, key).replaceAll("%prefix%", PREFIX).replaceAll("&", "§");
        return String.format(message, replace);
    }

    public static void sendMessage(ProxiedPlayer player, String message, Object... replace) {
        player.sendMessage(TextComponent.fromLegacyText(getMessage(message, replace)));
    }

    public static void sendMessages(ProxiedPlayer player, String message, Object... replace) {
        String toSend = "";
        for (String string : getMessageList(message, replace)) {
            toSend += string.replaceAll("&", "§") + "\n";
        }
        toSend = toSend.substring(0, toSend.length()-2);
        player.sendMessage(TextComponent.fromLegacyText(toSend));
    }

}
