package org.endertools.bungeeauth.command.sub;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.endertools.bungeeauth.AuthenticatorPlugin;
import org.endertools.bungeeauth.command.SubCommand;
import org.endertools.bungeeauth.config.MessageConfiguration;
import org.endertools.bungeeauth.util.ProxyCheckLog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
public class InfoSubCommand extends SubCommand {

    public InfoSubCommand(String permission) {
        super(permission);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        super.execute(commandSender, args);
        if (!(commandSender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (args.length == 1) return;
        List<BaseComponent[]> components = new ArrayList<>();
        ProxyCheckLog log = AuthenticatorPlugin.getInstance().getLogRepository().getLog(args[1]);
        if (log == null) return;
        String prefix = MessageConfiguration.PREFIX.replaceAll("&", "§");
        components.add(TextComponent.fromLegacyText(prefix + " §eUUID: §7" + log.getUuid().toString()));
        components.add(TextComponent.fromLegacyText(prefix + " §eName: §7" + log.getName()));
        components.add(TextComponent.fromLegacyText(prefix + " §eASN: §7" + log.getAsn()));
        components.add(TextComponent.fromLegacyText(prefix + " §eProvider: §7" + log.getProvider()));
        components.add(TextComponent.fromLegacyText(prefix + " §eContinent: §7" + log.getContinent()));
        components.add(TextComponent.fromLegacyText(prefix + " §eCountry: §7" + log.getCountry()));
        components.add(TextComponent.fromLegacyText(prefix + " §eISO-Code: §7" + log.getIsoCode()));
        components.add(TextComponent.fromLegacyText(prefix + " §eRegion: §7" + log.getRegion()));
        components.add(TextComponent.fromLegacyText(prefix + " §eCity: §7" + log.getCity()));
        components.add(TextComponent.fromLegacyText(prefix + " §eProxy: §7" + (log.isProxy() ? "yes" : "no")));
        components.add(TextComponent.fromLegacyText(prefix + " §eStatus: §7" + log.getStatus()));
        for (BaseComponent[] component : components) {
            player.sendMessage(component);
        }
    }

}
