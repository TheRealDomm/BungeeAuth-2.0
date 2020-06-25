package org.endertools.bungeeauth;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Plugin;
import org.endertools.bungeeauth.command.AuthBaseCommand;
import org.endertools.bungeeauth.command.SubCommandPool;
import org.endertools.bungeeauth.command.sub.*;
import org.endertools.bungeeauth.config.MessageConfiguration;
import org.endertools.bungeeauth.config.PluginConfiguration;
import org.endertools.bungeeauth.listener.*;
import org.endertools.bungeeauth.mysql.AuthRepository;
import org.endertools.bungeeauth.mysql.LogRepository;
import org.endertools.bungeeauth.mysql.ProxyCheckRepository;
import org.endertools.bungeeauth.util.AuthUtil;
import org.endertools.bungeeauth.util.GoogleAuthUtil;
import org.endertools.database.DatabaseManager;

import java.util.logging.Logger;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
@Getter
public class AuthenticatorPlugin {

    private static AuthenticatorPlugin instance;
    private AuthRepository authRepository;
    private LogRepository logRepository;
    private ProxyCheckRepository checkRepository;
    private AuthUtil authUtil;
    private GoogleAuthUtil googleAuthUtil;
    private SubCommandPool subCommandPool;
    private DatabaseManager databaseManager;
    @Setter
    private Plugin plugin;
    @Setter
    private Logger logger;


    public void onEnable() {
        instance = this;
        getLogger().info("\u001B[33m==========================================\u001B[0m");
        getLogger().info("\u001B[33m  Booting BungeeAuth v2.0 by TheRealDomm  \u001B[0m");
        getLogger().info("\u001B[33m==========================================\u001B[0m");
        this.databaseManager = new DatabaseManager(PluginConfiguration.MY_SQL_DATA, getLogger());
        this.databaseManager.connect();
        this.authRepository = new AuthRepository(databaseManager);
        this.logRepository = new LogRepository(databaseManager);
        this.checkRepository = new ProxyCheckRepository(databaseManager);
        this.authUtil = new AuthUtil();
        this.googleAuthUtil = new GoogleAuthUtil();
        this.subCommandPool = new SubCommandPool();
        setupCommands();
        setupListeners();
    }

    public void onDisable() {
        this.databaseManager.disconnect();
    }

    private void setupCommands() {
        plugin.getProxy().getPluginManager().registerCommand(plugin, new AuthBaseCommand(this));
        subCommandPool.put("code", new CodeSubCommand(""));
        subCommandPool.put("help", new HelpSubCommand(""));
        subCommandPool.put("info", new InfoSubCommand("bungeeauth.command.info"));
        subCommandPool.put("register", new RegisterSubCommand(""));
        subCommandPool.put("reload", new ReloadSubCommand("bungeeauth.command.reload"));
    }

    private void setupListeners() {
        plugin.getProxy().getPluginManager().registerListener(plugin, new PlayerChatListener(this));
        plugin.getProxy().getPluginManager().registerListener(plugin, new PlayerDisconnectListener(this));
        plugin.getProxy().getPluginManager().registerListener(plugin, new PluginChannelMessageListener(this));
        plugin.getProxy().getPluginManager().registerListener(plugin, new ProxyLoginListener(this));
        plugin.getProxy().getPluginManager().registerListener(plugin, new ServerSwitchListener(this));
    }



    public void reload() {
        this.databaseManager.disconnect();
        new PluginConfiguration(plugin);
        new MessageConfiguration(plugin);
        this.databaseManager = new DatabaseManager(PluginConfiguration.MY_SQL_DATA, getLogger());
        this.databaseManager.connect();
        this.authRepository = new AuthRepository(databaseManager);
        this.logRepository = new LogRepository(databaseManager);
        this.checkRepository = new ProxyCheckRepository(databaseManager);
    }

    public static AuthenticatorPlugin getInstance() {
        return instance;
    }

}
