package org.endertools.bungeeauth.config;

import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
public class DependencyConfiguration {

    private final String JSON = "https://repo1.maven.org/maven2/com/googlecode/json-simple/json-simple/1.1.1/" +
            "json-simple-1.1.1.jar";
    private final String DATABASE = "https://dl.endertools.org/database-manager-1.0-SNAPSHOT.jar";
    private final String GOOGLE_AUTH = "https://repo1.maven.org/maven2/com/warrenstrange/googleauth/1.4.0/googleauth-1.4.0.jar";
    private final String CODEC = "https://repo1.maven.org/maven2/commons-codec/commons-codec/1.14/commons-codec-1.14.jar";
    private final String HTTP = "https://repo1.maven.org/maven2/org/apache/httpcomponents/httpclient/4.5.9/httpclient-4.5.9.jar";

    private final File libraryFolder;
    private final Plugin plugin;

    public DependencyConfiguration(Plugin plugin) {
        this.plugin = plugin;
        libraryFolder = new File(plugin.getDataFolder() + "/libs/");
        if (!libraryFolder.exists()) {
            libraryFolder.mkdir();
        }
        downloadDepends(libraryFolder, JSON, DATABASE, GOOGLE_AUTH, CODEC, HTTP);
    }

    public void injectFiles(File folder) {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.getAbsolutePath().endsWith(".jar")) {
                try {
                    plugin.getLogger().info("Trying to inject library '" + file.getName() + "'...");
                    URL url = file.toURI().toURL();
                    Class[] params = new Class[]{URL.class};
                    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                    Class<?> urlClassLoader = URLClassLoader.class;
                    try {
                        Method method = urlClassLoader.getDeclaredMethod("addURL", params);
                        method.setAccessible(true);
                        method.invoke(classLoader, url);
                        plugin.getLogger().info("Successfully injected library '" + file.getName() + "'!");
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        plugin.getLogger().severe("Could not inject library '" + file.getName() + "'!");
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    plugin.getLogger().severe("Could not inject library '" + file.getName() + "'!");
                    e.printStackTrace();
                }
            }
        }
        plugin.getLogger().info("Dependency configuration was completed!");
        new PluginConfiguration(plugin);
    }

    public void downloadDepends(File folder, String... urls) {
        for (String url : urls) {
            plugin.getLogger().info("Pulling library from '" + url + "'...");
            try {
                URL uri = new URL(url);
                String[] strings = url.split("/");
                String libName = strings[strings.length-1];
                File libFile = new File(folder, libName);
                if (libFile.exists() && System.currentTimeMillis() - libFile.lastModified() < TimeUnit.MINUTES.toMillis(10)) {
                    plugin.getLogger().info("Skipping library '" + libName + "' its not older than 10 mintes!");
                    continue;
                }
                else {
                    Files.copy(uri.openStream(), libFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    plugin.getLogger().info("Successfully pulled library '" + libName + "' from '" + url + "'!");
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Could not pull library from '" + url + "'...");
                e.printStackTrace();
            }
        }
        plugin.getLogger().info("Pulling of librarys completed!");
        injectFiles(libraryFolder);
    }

}
