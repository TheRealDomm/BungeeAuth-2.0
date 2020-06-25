package org.endertools.bungeeauth.command;

import net.md_5.bungee.api.CommandSender;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
public interface ISubCommand {

    void execute(CommandSender commandSender, String[] args);

    String getPermission();

    void setPermission(String permission);

    boolean hasPermission(CommandSender sender);

}
