package org.endertools.bungeeauth.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
public abstract class SubCommand implements ISubCommand {

    private String permission;

    public SubCommand(String permission) {
        this.permission = permission;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!hasPermission(commandSender)) {
            commandSender.sendMessage(TextComponent.fromLegacyText("§cYou do not have permission to execute this command!"));
            return;
        }
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        if (permission == null || permission.equals("")) return true;
        return sender.hasPermission(getPermission());
    }

}
